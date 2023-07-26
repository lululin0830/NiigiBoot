package tw.idv.tibame.products.dao;

import java.sql.Timestamp;

import tw.idv.tibame.core.dao.CoreDAO;
import tw.idv.tibame.products.entity.SortWeight;

public interface SortWeightDAO extends CoreDAO<SortWeight,Timestamp> {
	
	//找出最新的異動
	public SortWeight findLatestDate();

}
