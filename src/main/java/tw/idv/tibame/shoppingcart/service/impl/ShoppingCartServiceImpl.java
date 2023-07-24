package tw.idv.tibame.shoppingcart.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.idv.tibame.shoppingcart.service.ShoppingCartService;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Override
	public String init(String memberId) {
		return null;
	}
	
	
	
}
