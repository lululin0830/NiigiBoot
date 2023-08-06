package tw.idv.tibame.products.service;

import java.util.List;

import tw.idv.tibame.products.DTO.ProductSpecManageDTO;
import tw.idv.tibame.products.entity.ProductSpec;



public interface ProductSpecService {
	
	//insert判斷
	
	//以selectByProductId商品編號查全部規格(不包含強制下架)再算出上架中的規格，然後加工成xxx:xxx xxx:xxx
	
	public List<ProductSpecManageDTO> getAllProductManage(String registerSupplier)throws Exception;

	public List<ProductSpecManageDTO> getSoldProductManage(String registerSupplier)throws Exception;
	
	public List<ProductSpecManageDTO> getStatusProductManage(String shelvesStatus,String registerSupplier)throws Exception;

}
