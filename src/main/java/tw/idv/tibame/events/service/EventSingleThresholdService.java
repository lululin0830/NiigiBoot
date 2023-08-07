package tw.idv.tibame.events.service;

import com.google.gson.JsonObject;

public interface EventSingleThresholdService {

	public String getAllInit();
	public String getBySearch(JsonObject searchCondition);
}
