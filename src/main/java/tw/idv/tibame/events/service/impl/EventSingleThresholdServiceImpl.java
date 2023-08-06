package tw.idv.tibame.events.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
