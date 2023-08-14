package tw.idv.tibame.products.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import tw.idv.tibame.products.DTO.ProductSpecManageDTO;
import tw.idv.tibame.products.dao.ProductDAO;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.dao.ProductSpecRepository;
import tw.idv.tibame.products.entity.ProductSpec;
import tw.idv.tibame.products.service.ProductSpecService;
import tw.idv.tibame.products.service.ShelvesStatusRecordService;

@Service
@Transactional
public class ProductSpecServiceImpl implements ProductSpecService {

	@Autowired
	private ProductSpecDAO productSpecDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ShelvesStatusRecordService shelvesStatusRecordService;

	private final ProductSpecRepository productSpecRepository;

	@Autowired
	public ProductSpecServiceImpl(ProductSpecRepository productSpecRepository) {
		this.productSpecRepository = productSpecRepository;
	}

	//////// 以下是分頁用
	@Override
	public List<ProductSpecManageDTO> getAllProductManage(String registerSupplier) throws Exception {
		List<ProductSpecManageDTO> combinedData = new ArrayList<>();

		List<ProductSpec> inactiveSpecs = productSpecDAO.findInactiveSpecificationsBySupplierId(registerSupplier);
		for (ProductSpec spec : inactiveSpecs) {
			ProductSpecManageDTO specManage = new ProductSpecManageDTO();
			specManage.setProductSpecId(spec.getProductSpecId());
			specManage.setProductId(spec.getProductId());
			specManage.setProductName(spec.getProduct().getProductName());
			specManage.setProductPrice(spec.getProduct().getProductPrice());
			specManage.setSpecType1(spec.getSpecType1());
			specManage.setSpecInfo1(spec.getSpecInfo1());
			specManage.setSpecType2(spec.getSpecType2());
			specManage.setSpecInfo2(spec.getSpecInfo2());
			specManage.setSpecPicture(spec.getSpecPicture());
			specManage.setShelvesStatus(spec.getShelvesStatus());
			specManage.setSpecStock(spec.getSpecStock());

			Integer soldQuantity = productSpecDAO.getSoldQuantityBySpecIdAndStatus(spec.getProductSpecId());
			specManage.setSoldStock(soldQuantity);

			combinedData.add(specManage);
		}
		return combinedData;
	}

	@Override
	public Integer getAllProductTotal(String registerSupplier) throws Exception {
		return productSpecDAO.getTotalCountOfActiveProductsBySupplierId(registerSupplier);
	}

	@Override
	public List<ProductSpecManageDTO> getSoldProductManage(String registerSupplier) throws Exception {
		List<ProductSpecManageDTO> combinedData = new ArrayList<>();

		List<ProductSpec> inactiveSpecs = productSpecDAO.getAllSpecsForSoldOutProductsBySupplierId(registerSupplier);
		for (ProductSpec spec : inactiveSpecs) {
			ProductSpecManageDTO specManage = new ProductSpecManageDTO();
			specManage.setProductSpecId(spec.getProductSpecId());
			specManage.setProductId(spec.getProductId());
			specManage.setProductName(spec.getProduct().getProductName());
			specManage.setProductPrice(spec.getProduct().getProductPrice());
			specManage.setSpecType1(spec.getSpecType1());
			specManage.setSpecInfo1(spec.getSpecInfo1());
			specManage.setSpecType2(spec.getSpecType2());
			specManage.setSpecInfo2(spec.getSpecInfo2());
			specManage.setSpecPicture(spec.getSpecPicture());
			specManage.setShelvesStatus(spec.getShelvesStatus());
			specManage.setSpecStock(spec.getSpecStock());

			Integer soldQuantity = productSpecDAO.getSoldQuantityBySpecIdAndStatus(spec.getProductSpecId());
			specManage.setSoldStock(soldQuantity);

			combinedData.add(specManage);
		}
		return combinedData;
	}

	@Override
	public Integer getSoldProductTotal(String registerSupplier) throws Exception {
		return productSpecDAO.getCountForSoldOutProductsBySupplierId(registerSupplier);
	}

	@Override
	public List<ProductSpecManageDTO> getStatusProductManage(String shelvesStatus, String registerSupplier)
			throws Exception {
		List<ProductSpecManageDTO> combinedData = new ArrayList<>();

		List<ProductSpec> inactiveSpecs = productSpecDAO.findActiveSpecificationsBySupplierId(shelvesStatus,
				registerSupplier);
		for (ProductSpec spec : inactiveSpecs) {
			ProductSpecManageDTO specManage = new ProductSpecManageDTO();
			specManage.setProductSpecId(spec.getProductSpecId());
			specManage.setProductId(spec.getProductId());
			specManage.setProductName(spec.getProduct().getProductName());
			specManage.setProductPrice(spec.getProduct().getProductPrice());
			specManage.setSpecType1(spec.getSpecType1());
			specManage.setSpecInfo1(spec.getSpecInfo1());
			specManage.setSpecType2(spec.getSpecType2());
			specManage.setSpecInfo2(spec.getSpecInfo2());
			specManage.setSpecPicture(spec.getSpecPicture());
			specManage.setShelvesStatus(spec.getShelvesStatus());
			specManage.setSpecStock(spec.getSpecStock());

			Integer soldQuantity = productSpecDAO.getSoldQuantityBySpecIdAndStatus(spec.getProductSpecId());
			specManage.setSoldStock(soldQuantity);

			combinedData.add(specManage);
		}
		return combinedData;
	}

	@Override
	public Integer getStatusProductTotal(String shelvesStatus, String registerSupplier) throws Exception {
		return productSpecDAO.getTotalCountOfProductsByStatusAndSupplierId(shelvesStatus, registerSupplier);
	}

	//////// 以下是分頁搜尋用
	@Override
	public List<ProductSpecManageDTO> getAllProductManage(String optionName, String searchText, String registerSupplier)
			throws Exception {
		List<ProductSpecManageDTO> combinedData = new ArrayList<>();

		List<ProductSpec> inactiveSpecs = productSpecDAO.findInactiveSpecificationsBySupplierId(optionName, searchText,
				registerSupplier);
		for (ProductSpec spec : inactiveSpecs) {
			ProductSpecManageDTO specManage = new ProductSpecManageDTO();
			specManage.setProductSpecId(spec.getProductSpecId());
			specManage.setProductId(spec.getProductId());
			specManage.setProductName(spec.getProduct().getProductName());
			specManage.setProductPrice(spec.getProduct().getProductPrice());
			specManage.setSpecType1(spec.getSpecType1());
			specManage.setSpecInfo1(spec.getSpecInfo1());
			specManage.setSpecType2(spec.getSpecType2());
			specManage.setSpecInfo2(spec.getSpecInfo2());
			specManage.setSpecPicture(spec.getSpecPicture());
			specManage.setShelvesStatus(spec.getShelvesStatus());
			specManage.setSpecStock(spec.getSpecStock());

			Integer soldQuantity = productSpecDAO.getSoldQuantityBySpecIdAndStatus(spec.getProductSpecId());
			specManage.setSoldStock(soldQuantity);

			combinedData.add(specManage);
		}
		return combinedData;
	}

	@Override
	public Integer getAllProductTotal(String optionName, String searchText, String registerSupplier) throws Exception {
		return productSpecDAO.getTotalCountOfActiveProductsBySupplierId(optionName, searchText, registerSupplier);
	}

	@Override
	public List<ProductSpecManageDTO> getSoldProductManage(String optionName, String searchText,
			String registerSupplier) throws Exception {
		List<ProductSpecManageDTO> combinedData = new ArrayList<>();

		List<ProductSpec> inactiveSpecs = productSpecDAO.getAllSpecsForSoldOutProductsBySupplierId(optionName,
				searchText, registerSupplier);
		for (ProductSpec spec : inactiveSpecs) {
			ProductSpecManageDTO specManage = new ProductSpecManageDTO();
			specManage.setProductSpecId(spec.getProductSpecId());
			specManage.setProductId(spec.getProductId());
			specManage.setProductName(spec.getProduct().getProductName());
			specManage.setProductPrice(spec.getProduct().getProductPrice());
			specManage.setSpecType1(spec.getSpecType1());
			specManage.setSpecInfo1(spec.getSpecInfo1());
			specManage.setSpecType2(spec.getSpecType2());
			specManage.setSpecInfo2(spec.getSpecInfo2());
			specManage.setSpecPicture(spec.getSpecPicture());
			specManage.setShelvesStatus(spec.getShelvesStatus());
			specManage.setSpecStock(spec.getSpecStock());

			Integer soldQuantity = productSpecDAO.getSoldQuantityBySpecIdAndStatus(spec.getProductSpecId());
			specManage.setSoldStock(soldQuantity);

			combinedData.add(specManage);
		}
		return combinedData;
	}

	@Override
	public Integer getSoldProductTotal(String optionName, String searchText, String registerSupplier) throws Exception {
		return productSpecDAO.getCountForSoldOutProductsBySupplierId(optionName, searchText, registerSupplier);
	}

	@Override
	public List<ProductSpecManageDTO> getStatusProductManage(String optionName, String searchText, String shelvesStatus,
			String registerSupplier) throws Exception {
		List<ProductSpecManageDTO> combinedData = new ArrayList<>();

		List<ProductSpec> inactiveSpecs = productSpecDAO.findActiveSpecificationsBySupplierId(optionName, searchText,
				shelvesStatus, registerSupplier);
		for (ProductSpec spec : inactiveSpecs) {
			ProductSpecManageDTO specManage = new ProductSpecManageDTO();
			specManage.setProductSpecId(spec.getProductSpecId());
			specManage.setProductId(spec.getProductId());
			specManage.setProductName(spec.getProduct().getProductName());
			specManage.setProductPrice(spec.getProduct().getProductPrice());
			specManage.setSpecType1(spec.getSpecType1());
			specManage.setSpecInfo1(spec.getSpecInfo1());
			specManage.setSpecType2(spec.getSpecType2());
			specManage.setSpecInfo2(spec.getSpecInfo2());
			specManage.setSpecPicture(spec.getSpecPicture());
			specManage.setShelvesStatus(spec.getShelvesStatus());
			specManage.setSpecStock(spec.getSpecStock());

			Integer soldQuantity = productSpecDAO.getSoldQuantityBySpecIdAndStatus(spec.getProductSpecId());
			specManage.setSoldStock(soldQuantity);

			combinedData.add(specManage);
		}
		return combinedData;
	}

	@Override
	public Integer getStatusProductTotal(String optionName, String searchText, String shelvesStatus,
			String registerSupplier) throws Exception {
		return productSpecDAO.getTotalCountOfProductsByStatusAndSupplierId(optionName, searchText, shelvesStatus,
				registerSupplier);
	}

	// 確認目前商品規格內有多少上架商品
	@Override
	public Integer getUpStatusCount(Integer productId) throws Exception {
		List<ProductSpec> productSpec = productSpecDAO.selectByProductId(productId);
		int c = 0;
		for (ProductSpec p : productSpec) {
			if (p.getShelvesStatus() == "0")
				c++;
		}
		return c;
	}

	// 針對上下架按鈕
	@Override
	public Boolean updateStatusButton(String[] productSpecIds, String shelvesMemberId, String shelvesStatus)
			throws Exception {
		Boolean b = productSpecDAO.updateStatus(productSpecIds, shelvesStatus);
		Boolean c;
		if (b) {
			c = shelvesStatusRecordService.insertShelvesStatusRecord(productSpecIds, shelvesMemberId, shelvesStatus);
		} else {
			return c = false;
		}
		if (c) {
			for (int i = 0; i < productSpecIds.length; i++) {
				Integer productId = Integer.parseInt(productSpecIds[i].substring(0, 8));
				int a = getUpStatusCount(productId);
				if (shelvesStatus == "0") {
					if (a == 1)
						productDAO.updateStatus(productId, shelvesStatus);
				} else {
					if (a == 0)
						productDAO.updateStatus(productId, "1");
				}
			}
		}
		return true;
	}

	// 以下是獲取pk號碼使用
	@Override
	public String concatPKID(String productId) throws Exception {
		Integer a = Integer.parseInt(productId);
		Integer b = productSpecDAO.selectByProductSpecId(a);
		String formattedText = String.format("%03d", b + 1);
		String poductSpecId = a + formattedText;
		return poductSpecId;
	}

	// 新增規格(不含圖片)
	@Override
	public String insertSpecProductText(String productId, String specType1, String specInfo1, String specType2,
			String specInfo2, String initialStock) throws Exception {
		ProductSpec productSpec = new ProductSpec();
		productSpec.setProductSpecId(concatPKID(productId));
		productSpec.setProductId(Integer.parseInt(productId));
		productSpec.setSpecType1(specType1);
		productSpec.setSpecInfo1(specInfo1);
		productSpec.setSpecType2(specType2);
		productSpec.setSpecInfo2(specInfo2);
		productSpec.setShelvesStatus("0");
		productSpec.setInitialStock(Integer.parseInt(initialStock));
		productSpec.setSpecStock(Integer.parseInt(initialStock));
		productSpecDAO.insert(productSpec);
		return productSpec.getProductSpecId();
	}

	// 上傳規格圖片
	@Override
	public void saveSpecPicture(String productSpecId, MultipartFile image) throws IOException {
		ProductSpec productSpec = productSpecRepository.findById(productSpecId).orElse(null);
		if (productSpec == null) {
			throw new IllegalArgumentException("ProductSpec with ID " + productSpecId + " not found.");
		}

		byte[] imageData = image.getBytes();
		productSpec.setSpecPicture(imageData);

		productSpecRepository.save(productSpec);
	}

	// 修改規格資料(不含圖)
	@Override
	public Boolean updateProductSpec(String productSpecId, String specType1, String specInfo1, String specType2,
			String specInfo2) throws Exception {
		ProductSpec productSpec = new ProductSpec();
		productSpec.setProductSpecId(productSpecId);
		productSpec.setSpecType1(specType1);
		productSpec.setSpecInfo1(specInfo1);
		productSpec.setSpecType2(specType2);
		productSpec.setSpecInfo2(specInfo2);
		return productSpecDAO.update(productSpec);
	}

}