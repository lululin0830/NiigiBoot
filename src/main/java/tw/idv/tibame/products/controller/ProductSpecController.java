package tw.idv.tibame.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.idv.tibame.products.DTO.ProductSpecManageDTO;
import tw.idv.tibame.products.service.ProductSpecService;

@RestController
public class ProductSpecController {
	
	@Autowired
	private ProductSpecService productSpecService;
	
	@GetMapping("/AllProductSpecManage")
	public List<ProductSpecManageDTO> getAllProductSpecManage() throws Exception {
		return productSpecService.getAllProductManage("S000000001");
	}
	
	
	@GetMapping("/SoldProductSpecManage")
	public List<ProductSpecManageDTO> getSoldProductSpecManage() throws Exception {
		return productSpecService.getSoldProductManage("S000000001");
	}
	
	@GetMapping("/UpStatusProductSpecManage")
	public List<ProductSpecManageDTO> getUpStatusProductSpecManage() throws Exception {
		return productSpecService.getStatusProductManage("0","S000000001");
	}
	
	@GetMapping("/DownStatusProductSpecManage")
	public List<ProductSpecManageDTO> getDownStatusProductSpecManage() throws Exception {
		return productSpecService.getStatusProductManage("1","S000000001");
	}
	

}
