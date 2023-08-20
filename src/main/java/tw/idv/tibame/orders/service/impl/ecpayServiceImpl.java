package tw.idv.tibame.orders.service.impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.transaction.Transactional;
import tw.idv.tibame.orders.dao.MainOrderDAO;
import tw.idv.tibame.orders.service.EcpayService;
@Service
@Transactional
public class ecpayServiceImpl implements EcpayService {

	@Autowired
	MainOrderDAO mainOrderDAO;
	
	@Override
	public String ecpayform(String orderId) {
		
		AllInOne all = new AllInOne("");
		
		LocalDateTime paytime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String ecpayTime = paytime.format(dateTimeFormatter);
		String totalAmount = String.valueOf(mainOrderDAO.patmentAmount(orderId));
		List<String> productName = mainOrderDAO.paymentItemList(orderId);
		
		//配合綠界
		Optional<String> reduce = productName.stream().reduce((String acc, String curr) -> { 
			return acc + "#" + curr; 
			});
		String itemName = reduce.get();
		
//		String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
		
		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo("NGI"+orderId);
		obj.setMerchantTradeDate(ecpayTime);
		obj.setTotalAmount(totalAmount);
		obj.setTradeDesc("test Description");
		obj.setItemName(itemName);
		// 交易結果回傳網址，只接受 https 開頭的網站，可以使用 ngrok
		obj.setReturnURL("http://niigi.shop/OrderPay/paymentRuturn");
		obj.setNeedExtraPaidInfo("Y");
		// 商店轉跳網址 (Optional)
		obj.setClientBackURL("http://niigi.shop/frontend/check_order.html");
		String form = all.aioCheckOut(obj, null);

		return form;
		
	}

}
