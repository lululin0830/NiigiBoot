package tw.idv.tibame.shoppingcart.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import tw.idv.tibame.shoppingcart.dao.ShoppingCartDAO;

@Repository
public class ShoppingCartDAOImpl implements ShoppingCartDAO {

	private final RedisTemplate<String, String> redisTemplate;

	@Autowired
	public ShoppingCartDAOImpl(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public boolean hasExistsCart(String memberId) {

		return redisTemplate.hasKey(memberId);
	}

	@Override
	public boolean insert(String memberId, String[] productSpecId) {
		
		Arrays.sort(productSpecId);
		
		return redisTemplate.opsForList().leftPushAll(memberId, productSpecId) > 0 ? true : false;
	}

	@Override
	public boolean update(String memberId, String[] productSpecId) {
		return redisTemplate.boundListOps(memberId).rightPushAll(productSpecId) > 0 ? true : false;
	}

	@Override
	public List<String> getCartList(String memberId) {
		return redisTemplate.boundListOps(memberId).range(0, -1);
	}

	@Override
	public boolean delete(String memberId, String productSpecId) {
		return redisTemplate.boundListOps(memberId).remove(1, productSpecId) > 0 ? true : false;
	}

	@Override
	public boolean delete(String memberId) {
		return redisTemplate.delete(memberId);
	}

	@Override
	public Long getCount(String memberId) {
		return redisTemplate.boundListOps(memberId).size();
	}

	@Override
	public void sort(String memberId) {
		BoundListOperations<String, String> list = redisTemplate.boundListOps(memberId);
		String[] array = new String[Math.toIntExact(list.size())];

		for (int i = 0; i < array.length; i++) {
			array[i] = list.leftPop();
		}
		Arrays.sort(array);
		list.leftPushAll(array);
	}

}
