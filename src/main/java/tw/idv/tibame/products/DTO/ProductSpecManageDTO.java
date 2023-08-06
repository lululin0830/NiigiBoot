package tw.idv.tibame.products.DTO;

public class ProductSpecManageDTO {

	private String productSpecId;
	private Integer productId;
	private String productName;
	private Integer productPrice;
	private String specType1;
	private String specInfo1;
	private String specType2;
	private String specInfo2;
	private byte[] specPicture;
	private String shelvesStatus;
	private Integer specStock;
	private Integer soldStock;

	public ProductSpecManageDTO() {
		// 空的預設建構函式
	}

	public ProductSpecManageDTO(String productSpecId, Integer productId, String productName, Integer productPrice,
			String specType1, String specInfo1, String specType2, String specInfo2, byte[] specPicture,
			String shelvesStatus, Integer specStock, Integer soldStock) {
		this.productSpecId = productSpecId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.specType1 = specType1;
		this.specInfo1 = specInfo1;
		this.specType2 = specType2;
		this.specInfo2 = specInfo2;
		this.specPicture = specPicture;
		this.shelvesStatus = shelvesStatus;
		this.specStock = specStock;
		this.soldStock = soldStock;
	}

	// Getters and Setters (根據需要生成所有屬性的Getter和Setter方法)

	public String getProductSpecId() {
		return productSpecId;
	}

	public void setProductSpecId(String productSpecId) {
		this.productSpecId = productSpecId;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSpecType1() {
		return specType1;
	}

	public void setSpecType1(String specType1) {
		this.specType1 = specType1;
	}

	public String getSpecInfo1() {
		return specInfo1;
	}

	public void setSpecInfo1(String specInfo1) {
		this.specInfo1 = specInfo1;
	}

	public String getSpecType2() {
		return specType2;
	}

	public void setSpecType2(String specType2) {
		this.specType2 = specType2;
	}

	public String getSpecInfo2() {
		return specInfo2;
	}

	public void setSpecInfo2(String specInfo2) {
		this.specInfo2 = specInfo2;
	}

	public byte[] getSpecPicture() {
		return specPicture;
	}

	public void setSpecPicture(byte[] specPicture) {
		this.specPicture = specPicture;
	}

	public String getShelvesStatus() {
		return shelvesStatus;
	}

	public void setShelvesStatus(String shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}

	public Integer getSpecStock() {
		return specStock;
	}

	public void setSpecStock(Integer specStock) {
		this.specStock = specStock;
	}

	public Integer getSoldStock() {
		return soldStock;
	}

	public void setSoldStock(Integer soldStock) {
		this.soldStock = soldStock;
	}

}
