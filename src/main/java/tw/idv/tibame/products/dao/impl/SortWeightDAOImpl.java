package tw.idv.tibame.products.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import tw.idv.tibame.products.dao.SortWeightDAO;
import tw.idv.tibame.products.entity.SortWeight;

@Repository
public class SortWeightDAOImpl implements SortWeightDAO {
	
	@PersistenceContext
	private Session session;
	
	
	@Override
	public SortWeight findLatestDate() {
		String hql = "SELECT * FROM SortWeight order by weightsUpdateTime DESC limit 1;";
	    NativeQuery<SortWeight> nativequery = session.createNativeQuery(hql, SortWeight.class);
	    return nativequery.getSingleResult();
	}
	
	@Override
	public Boolean insert(SortWeight entity)throws Exception{
		session.persist(entity);
		return true;
	}
	
	@Override
	public SortWeight selectById(Timestamp id)throws Exception{
		return null;
	}
	
	@Override
	public List<SortWeight> getAll() throws Exception{
		return null;
	}
	

}
