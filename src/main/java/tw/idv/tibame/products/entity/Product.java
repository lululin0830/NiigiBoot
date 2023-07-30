package tw.idv.tibame.products.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tw.idv.tibame.suppliers.entity.Suppliers;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	private String registerSupplier;
	private String categorieId;
	private String productName;
	private Integer productPrice;
	private String productInfo;
	private byte[] picture1;
	private byte[] picture2;
	private byte[] picture3;
	private byte[] picture4;
	private byte[] picture5;
	@Column(insertable = false)
	private String productStatus;
	@Column(insertable = false)
	private Double avgRating;
	@Column(insertable = false)
	private Date firstOnShelvesDate;
	
	@ManyToOne
	@JoinColumn(name = "registerSupplier", insertable = false, updatable = false)
	private Suppliers suppliers;
	
	
}
