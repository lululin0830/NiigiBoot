package tw.idv.tibame.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JwtConfig {

	private static final String PROPERTIES_FILE = "application.properties";
	private static final String JWT_SECRET_KEY_PROPERTY = "jwt.secret.key";

	private static String jwtSecretKey;

	static {
		try (InputStream inputStream = JwtConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			jwtSecretKey = properties.getProperty(JWT_SECRET_KEY_PROPERTY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 取得JWT Secret Key
	public static String getJwtSecretKey() {
		return jwtSecretKey;
	}
}
