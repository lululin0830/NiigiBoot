package tw.idv.tibame.products.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.idv.tibame.products.DTO.ProductSpecManageDTO;
import tw.idv.tibame.products.dao.ProductSpecDAO;
import tw.idv.tibame.products.entity.ProductSpec;
import tw.idv.tibame.products.service.ProductSpecService;

@Service
@Transactional
public class ProductSpecServiceImpl implements ProductSpecService {
	
	
	@Autowired
    private ProductSpecDAO productSpecDAO;
	
	@Override
	public List<ProductSpecManageDTO> getAllProductManage(String registerSupplier)throws Exception{
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
	public List<ProductSpecManageDTO> getSoldProductManage(String registerSupplier)throws Exception{
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
	public List<ProductSpecManageDTO> getStatusProductManage(String shelvesStatus,String registerSupplier)throws Exception{
		List<ProductSpecManageDTO> combinedData = new ArrayList<>();
		
		List<ProductSpec> inactiveSpecs = productSpecDAO.findActiveSpecificationsBySupplierId(shelvesStatus,registerSupplier);
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
	

	
	
}