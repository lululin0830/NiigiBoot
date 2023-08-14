package tw.idv.tibame.products.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tw.idv.tibame.products.DTO.ProductSpecManageDTO;
import tw.idv.tibame.products.service.ProductSpecService;

@RestController
public class ProductSpecController {

	@Autowired
	private ProductSpecService productSpecService;

	///////////////// 以下是分頁用
	@PostMapping("/AllProductSpecManage")
	public List<ProductSpecManageDTO> getAllProductSpecManage(@RequestBody Map<String, String> requestData)
			throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getAllProductManage(registerSupplier);
	}

	@PostMapping("/AllProductSpecTotal")
	public Integer getAllProductSpecTotal(@RequestBody Map<String, String> requestData) throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getAllProductTotal(registerSupplier);
	}

	@PostMapping("/SoldProductSpecManage")
	public List<ProductSpecManageDTO> getSoldProductSpecManage(@RequestBody Map<String, String> requestData)
			throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getSoldProductManage(registerSupplier);
	}

	@PostMapping("/SoldProductSpecTotal")
	public Integer getSoldProductSpecTotal(@RequestBody Map<String, String> requestData) throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getSoldProductTotal(registerSupplier);
	}

	@PostMapping("/UpStatusProductSpecManage")
	public List<ProductSpecManageDTO> getUpStatusProductSpecManage(@RequestBody Map<String, String> requestData)
			throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getStatusProductManage("0", registerSupplier);
	}

	@PostMapping("/UpStatusProductSpecTotal")
	public Integer getUpStatusProductSpecTotal(@RequestBody Map<String, String> requestData) throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getStatusProductTotal("0", registerSupplier);
	}

	@PostMapping("/DownStatusProductSpecManage")
	public List<ProductSpecManageDTO> getDownStatusProductSpecManage(@RequestBody Map<String, String> requestData)
			throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getStatusProductManage("1", registerSupplier);
	}

	@PostMapping("/DownStatusProductSpecTotal")
	public Integer getDownStatusProductSpecTotal(@RequestBody Map<String, String> requestData) throws Exception {
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getStatusProductTotal("1", registerSupplier);
	}

	///////////////// 以下是分頁搜尋用
	@PostMapping("/AllSearch")
	public List<ProductSpecManageDTO> getAllSearch(@RequestBody Map<String, String> requestData) throws Exception {
		String optionName = requestData.get("optionName");
		String searchText = requestData.get("searchText");
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getAllProductManage(optionName, searchText, registerSupplier);
	}

	@PostMapping("/AllSearchTotal")
	public Integer getAllSearchTotal(@RequestBody Map<String, String> requestData) throws Exception {
		String optionName = requestData.get("optionName");
		String searchText = requestData.get("searchText");
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getAllProductTotal(optionName, searchText, registerSupplier);
	}

	@PostMapping("/SoldSearch")
	public List<ProductSpecManageDTO> getSoldSearch(@RequestBody Map<String, String> requestData) throws Exception {
		String optionName = requestData.get("optionName");
		String searchText = requestData.get("searchText");
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getSoldProductManage(optionName, searchText, registerSupplier);
	}

	@PostMapping("/SoldSearchTotal")
	public Integer getSoldSearchTotal(@RequestBody Map<String, String> requestData) throws Exception {
		String optionName = requestData.get("optionName");
		String searchText = requestData.get("searchText");
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getSoldProductTotal(optionName, searchText, registerSupplier);
	}

	@PostMapping("/UpStatusSearch")
	public List<ProductSpecManageDTO> getUpStatusSearch(@RequestBody Map<String, String> requestData) throws Exception {
		String optionName = requestData.get("optionName");
		String searchText = requestData.get("searchText");
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getStatusProductManage(optionName, searchText, "0", registerSupplier);
	}

	@PostMapping("/UpStatusSearchTotal")
	public Integer getUpStatusSearchTotal(@RequestBody Map<String, String> requestData) throws Exception {
		String optionName = requestData.get("optionName");
		String searchText = requestData.get("searchText");
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getStatusProductTotal(optionName, searchText, "0", registerSupplier);
	}

	@PostMapping("/DownStatusSearch")
	public List<ProductSpecManageDTO> getDownStatusSearch(@RequestBody Map<String, String> requestData)
			throws Exception {
		String optionName = requestData.get("optionName");
		String searchText = requestData.get("searchText");
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getStatusProductManage(optionName, searchText, "1", registerSupplier);
	}

	@PostMapping("/DownStatusSearchTotal")
	public Integer getDownStatusSearchTotal(@RequestBody Map<String, String> requestData) throws Exception {
		String optionName = requestData.get("optionName");
		String searchText = requestData.get("searchText");
		String registerSupplier = requestData.get("registerSupplier");
		return productSpecService.getStatusProductTotal(optionName, searchText, "1", registerSupplier);
	}

	/// 以下是上下架按鈕用
	@PostMapping("/StatusButton")
	public Boolean updateProductSpecStatus(@RequestBody Map<String, Object> requestData) throws Exception {
		List<Long> productSpecIdsList = (List<Long>) requestData.get("productSpecIds");
		String[] productSpecIds = new String[productSpecIdsList.size()];
		// 將 ArrayList 中的元素逐個裝入 String[] 陣列中
		for (int i = 0; i < productSpecIdsList.size(); i++) {
			productSpecIds[i] = productSpecIdsList.get(i).toString();
		}
		String shelvesMemberId = (String) requestData.get("shelvesMemberId");
		String shelvesStatus = (String) requestData.get("shelvesStatus");
		return productSpecService.updateStatusButton(productSpecIds, shelvesMemberId, shelvesStatus);
	}

	// 以下是新增規格用
	@PostMapping("/insertSpecProduct")
	public List<String> insertSpecProductText(@RequestBody List<Map<String, String>> dataToSend) throws Exception {
		List<String> poductSpecIdList = new ArrayList<>();
		for (Map<String, String> item : dataToSend) {
			String productId = item.get("productId");
			String specType1 = item.get("specType1");
			String specInfo1 = item.get("specInfo1");
			String specType2 = item.get("specType2");
			String specInfo2 = item.get("specInfo2");
			String initialStock = item.get("initialStock");
			String poductSpecId = productSpecService.insertSpecProductText(productId, specType1, specInfo1, specType2,
					specInfo2, initialStock);
			poductSpecIdList.add(poductSpecId);
		}
		return poductSpecIdList;
	}

	// 上傳規格圖片用
	@PostMapping("/uploadSpecPicture")
	public ResponseEntity<String> uploadSpecPicture(@RequestParam("productSpecIds") String[] productSpecIds,
			@RequestParam MultipartFile[] images) throws Exception {

		for (int i = 0; i < images.length; i++) {
			MultipartFile image = images[i];
			String productSpecId = productSpecIds[i];
			// System.out.println(productSpecIds[i]);
			// System.out.println(image);
			productSpecService.saveSpecPicture(productSpecId, image);
		}

		return ResponseEntity.ok("Uploaded successfully");

	}
	
	// 修改規格資料(不含圖)
	@PostMapping("/updateSpecText")
	public Boolean updateProductSpecText(@RequestBody Map<String, String> requestData) throws Exception {
		String productSpecId = requestData.get("productSpecId");
		String specType1 = requestData.get("specType1");
		String specInfo1 = requestData.get("specInfo1");
		String specType2 = requestData.get("specType2");
		String specInfo2 = requestData.get("specInfo2");
		return productSpecService.updateProductSpec(productSpecId, specType1, specInfo1, specType2, specInfo2);
	}

}
