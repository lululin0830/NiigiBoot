package tw.idv.tibame.products.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tw.idv.tibame.products.dto1.ProductSpecManageDTO;
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
	
	// 以下是獲取pk號碼使用
	public String concatPKID(String productId) throws Exception;
	
	//新增規格(不含圖片)
	public String insertSpecProductText(String productId, String specType1, String specInfo1, String specType2,
			String specInfo2, String initialStock) throws Exception;
	
	//上傳規格圖片
	public void saveSpecPicture(String productSpecId, MultipartFile image) throws IOException;
	
	// 修改規格資料(不含圖)
	public Boolean updateProductSpec(String productSpecId, String specType1, String specInfo1, String specType2,
				String specInfo2) throws Exception;
	
	
	//以productid查看所有規格
	public List<ProductSpec> selectByProductId(Integer productId) throws Exception; 
	
}
