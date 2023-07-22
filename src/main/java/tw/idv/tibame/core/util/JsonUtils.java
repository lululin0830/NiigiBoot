package tw.idv.tibame.core.util;

import com.google.gson.Gson;

/**
 * JsonUtils Json & 物件之間互相轉換的工具類別
 * 
 * @author Lulu Lin
 * @version 1.0
 */
public class JsonUtils {
	private static final Gson gson = new Gson();

	// 物件轉Json
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	// Json轉物件
	public static <T> T fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
}
