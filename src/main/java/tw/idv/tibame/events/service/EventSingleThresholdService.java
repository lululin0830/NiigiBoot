package tw.idv.tibame.events.service;

import com.google.gson.JsonObject;

import tw.idv.tibame.events.entity.EventSingleThreshold;

public interface EventSingleThresholdService {

	public String getAllInit();
	public String getBySearch(JsonObject searchCondition);
	public String getBySearchSupplier(JsonObject searchCondition);
	
	String addEvent (EventSingleThreshold addEvent) throws Exception;
//	public boolean addEvent(JsonObject addEvent) throws Exception;
}
