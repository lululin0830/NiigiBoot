package tw.idv.tibame.products.service;

import java.util.List;

import tw.idv.tibame.products.DTO.ProductSpecManageDTO;
import tw.idv.tibame.products.entity.ProductSpec;



public interface ProductSpecService {
	
	//insert判斷
	

	//以下是分頁用
	public List<ProductSpecManageDTO> getAllProductManage(String registerSupplier)throws Exception;
	
	public Integer getAllProductTotal(String registerSupplier)throws Exception;

	public List<ProductSpecManageDTO> getSoldProductManage(String registerSupplier)throws Exception;
	
	public Integer getSoldProductTotal(String registerSupplier)throws Exception;
	
	public List<ProductSpecManageDTO> getStatusProductManage(String shelvesStatus,String registerSupplier)throws Exception;
	
	public Integer getStatusProductTotal(String shelvesStatus,String registerSupplier)throws Exception;

	//以下是分頁搜尋用
	public List<ProductSpecManageDTO> getAllProductManage(String optionName,String searchText,String registerSupplier)throws Exception;
	
	public Integer getAllProductTotal(String optionName,String searchText,String registerSupplier)throws Exception;
	
	public List<ProductSpecManageDTO> getSoldProductManage(String optionName,String searchText,String registerSupplier)throws Exception;
	
	public Integer getSoldProductTotal(String optionName,String searchText,String registerSupplier)throws Exception;
	
	public List<ProductSpecManageDTO> getStatusProductManage(String optionName,String searchText,String shelvesStatus,String registerSupplier)throws Exception;
	
	public Integer getStatusProductTotal(String optionName,String searchText,String shelvesStatus,String registerSupplier)throws Exception;
	
	
	//確認目前商品規格內有多少上架商品
	public Integer getUpStatusCount(Integer productId) throws Exception;
	
	
	//針對上下架按鈕
	public Boolean updateStatusButton(String[] productSpecIds, String shelvesMemberId, String shelvesStatus)throws Exception;
	
	
	
	
}
