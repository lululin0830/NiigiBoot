package tw.idv.tibame.shoppingcart.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import tw.idv.tibame.shoppingcart.service.ShoppingCartService;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Override
	public String init(String memberId) {
		return null;
	}

	@Override
	public String addToCart(JsonObject data) {
		return null;
	}

	@Override
	public boolean removeFromCart(JsonObject data) {
		return false;
	}
	
	
	
}
