package tw.idv.tibame.core.util;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.gson.io.GsonSerializer;

/**
 * JWTutil 用於生成、驗證JWT的工具類別
 * 
 * @author Lulu Lin
 * @version 1.0
 */

public class JwtUtil implements Serializable {

	private static Gson gson = new Gson();

	private static final long serialVersionUID = -7699280065575291990L;

	private static final String SECRET_KEY = JwtConfig.getJwtSecretKey();
//	private static final long EXPIRATION_TIME = 3600000; // Token的生存時間(先設1hr)
	private static final long EXPIRATION_TIME = 86400000; // Token的生存時間(先設1hr)
	// 生成JWT Token
	public static String generateJwtToken(String userId, String userName) {
		
		// 計算JWT的過期時間，現在時間+EXPIRATION_TIME後的時間
		Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

		// 將userId,userName組合成一個JSON格式的字串，作為JWT的Subject
		Map<String, Object> subjectMap = new HashMap<>();
		subjectMap.put("userId", userId);
		subjectMap.put("userName", userName);
		String subject = gson.toJson(subjectMap);

		// 使用SecretKeySpec來產生用於JWT簽名的Key
		Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

		// 使用Jwts.builder()生成JWT Token，signWith方法設置簽名密鑰，compact方法取出最終的JWT字串
		String jwtToken = Jwts.builder().setSubject(subject).setExpiration(expirationDate).signWith(key)
				.serializeToJsonWith(new GsonSerializer<>(gson)).compact();

		return jwtToken;

	}

	public static boolean validateJwtToken(String jwtToken) {
		try {
			// 使用SecretKeySpec來產生用於驗證JWT簽名的Key
			Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

			// 透過剛剛產生的key來解析JWT Token，並回傳聲明
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);

			// 獲取JWT Token中的過期時間(Claim: exp)
			Date expirationDate = claimsJws.getBody().getExpiration();

			// 檢查Token是否已過期
			if (expirationDate != null && expirationDate.before(new Date())) {
				return false;
			}

			// 如果以上的解析和驗證都正常沒丟出例外，表示JWT Token是有效的
			return true;
		} catch (ExpiredJwtException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static Map<String, String> validateJwtTokenAndSendInfo(String bearerToken) {
		
		final String jwtToken = bearerToken.replace("Bearer ", "");
		Map<String, String> response = new HashMap<>();
		
		try {
			// 使用SecretKeySpec來產生用於驗證JWT簽名的Key
			Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

			// 透過剛剛產生的key來解析JWT Token，並回傳聲明
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);

			// 獲取JWT Token中的過期時間(Claim: exp)
			Date expirationDate = claimsJws.getBody().getExpiration();

			// 檢查Token是否已過期
			if (expirationDate != null && expirationDate.before(new Date())) {
				response.put("error", "Token expired");
				return response;
			}

			// 解析JWT Token中的使用者資訊
			
			String subject = claimsJws.getBody().getSubject();
			Map<String, String> subjectMap = gson.fromJson(subject, new TypeToken<Map<String, String>>() {}.getType());
			String userId = subjectMap.get("userId");
			String username = subjectMap.get("userName");

			response.put("userId", userId);
			response.put("username", username);

			return response;
			
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			response.put("error", "Token expired");
			return response;
			
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", "Invalid token");
			return response;
		}
	}

}
