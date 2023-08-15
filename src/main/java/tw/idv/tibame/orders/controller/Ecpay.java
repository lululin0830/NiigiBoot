package tw.idv.tibame.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.orders.service.EcpayService;

@RestController
@RequestMapping("/OrderPay")
@CrossOrigin(origins = "*")
public class Ecpay {

	@Autowired
	EcpayService ecpayService;

	@PostMapping
	public String ecpayCheck(@RequestBody String orderId) {

		String ecpCheck = ecpayService.ecpayform(orderId);
		return ecpCheck;
	}

//	@PostMapping(path = "/paymentRuturn", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public void paymentReturn(Foo foo) {
//		System.out.println(foo);
//	}
//}
//
//class Foo {
//	private Integer MerchantID;
//	private String RtnMsg;
//
//	public Integer getMerchantID() {
//		return MerchantID;
//	}
//
//	public void setMerchantID(Integer merchantID) {
//		MerchantID = merchantID;
//	}
//
//	public String getRtnMsg() {
//		return RtnMsg;
//	}
//
//	public void setRtnMsg(String rtnMsg) {
//		RtnMsg = rtnMsg;
//	}
	
	
	@PostMapping("/paymentRuturn")
	public int paymentReturn(@RequestParam(name = "RtnCode")  String rtnCode) {
		System.out.println("HI");
		
		return 1;
		
	}
}