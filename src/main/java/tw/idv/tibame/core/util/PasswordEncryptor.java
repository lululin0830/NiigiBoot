package tw.idv.tibame.core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

	private static final String FIXED_SALT = "Pz+5Pdg0tDwirGiFsGTzRQ=="; // 固定的 salt

	// 將密碼使用SHA-256加密，並回傳加密後的16進制字串
	public static String encrypt(String password) throws NoSuchAlgorithmException {

		// 創建SHA-256加密的MessageDigest物件
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		// 將固定的 salt 和密碼組合在一起
		String saltedPassword = FIXED_SALT + password;

		// 將 saltedPassword 進行加密
		byte[] encodedHash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

		// 將加密後的位元組陣列轉換為16進位表示形式
		StringBuilder hexString = new StringBuilder();
		for (byte b : encodedHash) {
			String hex = Integer.toHexString(0xff & b);
			// 確保每個16進位數字都是兩位數
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
