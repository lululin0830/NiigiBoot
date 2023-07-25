package tw.idv.tibame.shoppingcart.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import tw.idv.tibame.shoppingcart.dao.ShoppingCartDAO;

@Repository
public class ShoppingCartDAOImpl implements ShoppingCartDAO {

	private final RedisTemplate<String, Object> redisTemplate;

	@Autowired
	public ShoppingCartDAOImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public boolean hasExistsCart(String memberId) {

		return redisTemplate.boundListOps(memberId) != null;
	}

	@Override
	public boolean insert(String memberId, int productSpecId) {
		return false;
	}

	@Override
	public boolean update(String memberId, int productSpecId) {
		return false;
	}

	@Override
	public List<Integer> getCartList(String memberId) {
		return redisTemplate.boundListOps(memberId).range(0, -1).stream()
				.map(obj -> (Integer) obj)
				.collect(Collectors.toList());
	}

	@Override
	public boolean delete(String memberId, int productSpecId) {
		return false;
	}

	@Override
	public boolean delete(String memberId) {
		return false;
	}

	@Override
	public int getCount(String memberId) {
		return 0;
	}

}
