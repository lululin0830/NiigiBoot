package tw.idv.tibame.events.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import tw.idv.tibame.events.dao.EventSingleThresholdDAOImpl;
import tw.idv.tibame.events.entity.EventSingleThreshold;
import tw.idv.tibame.events.service.EventSingleThresholdService;

@Service
@Transactional
public class EventSingleThresholdServiceImpl implements EventSingleThresholdService{

	private static volatile int orderCounter = 1;
	private static final Object counterLock = new Object();
	
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
	
	@Override
	public String getBySearchSupplier(JsonObject searchCondition) {
		
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

		
		String RegisterSupplier = searchCondition.get("RegisterSupplier").getAsString();
		
		String result = null;
		
		try {
			result = dao.getAllBySearchSupplier(searchcase, SearchSelect, startDate, closeDate, RegisterSupplier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 取得自動編號
		public String generateOrderId() throws Exception {

			String lastId = dao.selectLastOrder();
			LocalDate lastDate = null;
			if (lastId != null) {

				lastDate = LocalDate.parse(lastId.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"));
			}

			LocalDate currentDate = LocalDate.now();
			String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

			String eventId = formattedDate + String.format("%08d", 1);

			synchronized (counterLock) {

				if (dao.selectById(eventId) == null || orderCounter >= 999999999) {
					orderCounter = 1;
				} else if (lastId != null && dao.selectById(eventId) != null && lastDate.isEqual(currentDate)) {
					orderCounter = Integer.parseInt(lastId.substring(8)) + 1;
				}
				eventId = formattedDate + String.format("%08d", orderCounter);
				orderCounter++;
			}
			return eventId;
		}
	
	@Override
	public String addEvent(EventSingleThreshold newEvent) throws Exception{
		dao.insert(newEvent);
		return "新增成功";
	}
}
