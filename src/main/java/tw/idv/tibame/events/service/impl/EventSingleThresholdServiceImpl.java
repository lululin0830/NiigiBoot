package tw.idv.tibame.events.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import tw.idv.tibame.events.dao.EventSingleThresholdDAOImpl;
import tw.idv.tibame.events.service.EventSingleThresholdService;

@Service
@Transactional
public class EventSingleThresholdServiceImpl implements EventSingleThresholdService{

	@Autowired
	EventSingleThresholdDAOImpl dao;
	
	@Override
	public String getAllInit() {
		
		String getAllInit = null;
		
		try {
			getAllInit = dao.getAllInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getAllInit;
	}
	
	@Override
	public String getBySearch(JsonObject searchCondition) {
		
		String searchcase = searchCondition.get("searchcase").getAsString();

		String SearchSelect = searchCondition.get("searchway").getAsString();

		String startDateString = searchCondition.get("StartDate").getAsString();

		Timestamp startDate, closeDate;

		if (startDateString.length() > 0) {
			startDateString += " 00:00:00";
			startDate = Timestamp.valueOf(startDateString);
		} else {
			startDate = Timestamp.valueOf("1970-01-01 00:00:00");
		}

		String closeDateString = searchCondition.get("EndDate").getAsString();

		if (closeDateString.length() > 0) {
			closeDateString += " 00:00:00";
			closeDate = Timestamp.valueOf(closeDateString);
		} else {
			closeDate = Timestamp.valueOf("2050-01-01 00:00:00");
		}

		
		String result = null;
		
		try {
			result = dao.getAllBySearch(searchcase, SearchSelect, startDate, closeDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
