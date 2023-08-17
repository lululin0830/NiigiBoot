package tw.idv.tibame.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.core.LoginRequired;
import tw.idv.tibame.orders.service.EcpayService;

@RestController
@RequestMapping("/OrderPay")
@CrossOrigin(origins = "*")
public class Ecpay {

	@Autowired
	EcpayService ecpayService;
	
	@LoginRequired
	@PostMapping
	public String ecpayCheck(@RequestBody String orderId) {

		String ecpCheck = ecpayService.ecpayform(orderId);
		return ecpCheck;
	}
	
	//收綠界回傳
	@PostMapping(path ="/paymentRuturn" , consumes = "application/x-www-form-urlencoded")
	public int paymentReturn(
            @RequestParam(name = "CustomField1", required = false) String customField1,
            @RequestParam(name = "CustomField2", required = false) String customField2,
            @RequestParam(name = "CustomField3", required = false) String customField3,
            @RequestParam(name = "CustomField4", required = false) String customField4,
            @RequestParam(name = "MerchantID") String merchantID,
            @RequestParam(name = "MerchantTradeNo") String merchantTradeNo,
            @RequestParam(name = "PaymentDate") String paymentDate,
            @RequestParam(name = "PaymentType") String paymentType,
            @RequestParam(name = "PaymentTypeChargeFee") String paymentTypeChargeFee,
            @RequestParam(name = "RtnCode") String rtnCode,
            @RequestParam(name = "RtnMsg") String rtnMsg,
            @RequestParam(name = "SimulatePaid") String simulatePaid,
            @RequestParam(name = "StoreID", required = false) String storeID,
            @RequestParam(name = "TradeAmt") String tradeAmt,
            @RequestParam(name = "TradeDate") String tradeDate,
            @RequestParam(name = "TradeNo") String tradeNo,
            @RequestParam(name = "CheckMacValue") String checkMacValue) {

		if(rtnCode.equals("1")) {
			
			String ordreId = merchantTradeNo.replace("NGI", "");
			ecpayService.ecpayform(ordreId);
		}
		
		return 1;
		
	}
}