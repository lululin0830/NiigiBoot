package tw.idv.tibame.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;

import jakarta.servlet.ServletContext;

/**
 * 共用的工具類別-
 * Json & 物件之間互相轉換
 * getBean
 * 
 * @author Lulu Lin
 * @version 1.1
 */
public class CommonUtils {
	private static final Gson gson = new Gson();

	// 物件轉Json
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	// Json轉物件
	public static <T> T fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

	// 取得Bean元件
	public static <T> T getBean(ServletContext sc, Class<T> clazz) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		return context.getBean(clazz);
	}
}
