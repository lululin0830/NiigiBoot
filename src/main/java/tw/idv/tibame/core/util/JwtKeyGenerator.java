package tw.idv.tibame.core.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class JwtKeyGenerator {

    // 產生JWT專用的密鑰
    public static String generateJwtSecretKey() {
        try {
            // 使用HMAC SHA-256算法生成256位的密鑰
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");

            // 使用SecureRandom作為隨機數生成器，提高密鑰的隨機性和安全性
            SecureRandom secureRandom = new SecureRandom();
            keyGen.init(256, secureRandom);

            SecretKey secretKey = keyGen.generateKey();
            // 將密鑰轉換成Base64編碼的字符串形式，便於存儲和傳輸
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // 生成JWT專用的密鑰並打印
        String jwtSecretKey = generateJwtSecretKey();
        System.out.println("JWT Secret Key: " + jwtSecretKey);
    }
}

