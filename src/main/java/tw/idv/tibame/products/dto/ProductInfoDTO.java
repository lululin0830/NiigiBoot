package tw.idv.tibame.products.dto;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProductInfoDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	private String productName;
    private Integer productPrice;
    private byte[] picture1;
    private String shopName;
    
    
    // 空構造函數
    public ProductInfoDTO() {
    }

    // 帶參數的構造函數
    public ProductInfoDTO(String productName, int productPrice, byte[] picture1, String shopName) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.picture1 = picture1;
        this.shopName = shopName;
    }

}
