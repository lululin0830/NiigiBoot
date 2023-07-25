package tw.idv.tibame.core.service;

public interface CoreService<T> {
	/**
	 * 取得Header上方的資訊
	 */
	String initForHeader(T userInfo);
}
