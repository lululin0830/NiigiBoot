package tw.idv.tibame.products.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import tw.idv.tibame.products.dao.ShelvesStatusRecordDAO;
import tw.idv.tibame.products.entity.ShelvesStatusRecord;

@Repository
public class ShelvesStatusRecordDAOImpl implements ShelvesStatusRecordDAO {
	
	@PersistenceContext
	private Session session;

	@Override
	public Boolean insert(ShelvesStatusRecord entity) throws Exception {
		session.persist(entity);
		return true;
	}

	@Override
	public ShelvesStatusRecord selectById(String id) throws Exception {
		return session.get(ShelvesStatusRecord.class, id);
	}

	@Override
	public List<ShelvesStatusRecord> getAll() throws Exception {
		return null;
	}
	
	//用於計算pk單號(新增時用的)
	@Override
	public Integer selectByShelvesStatusDate(String restockDate)throws Exception {
		String sql = "select count(*) from ShelvesStatusRecord where statusModifyTime like '%" + restockDate + "%';";
		NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
		return nativeQuery.uniqueResult();
	}
	

}
