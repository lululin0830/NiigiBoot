package tw.idv.tibame.products.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductSpec {

	@Id
	private String productSpecId;
	private Integer productId;
	private String specType1;
	private String specInfo1;
	private String specType2;
	private String specInfo2;
	private byte[] specPicture;
	@Column(insertable = false)
	private String shelvesStatus;
	private Integer initialStock;
	private Integer specStock;

	@ManyToOne
	@JoinColumn(name = "productId", insertable = false, updatable = false)
	private Product product;
}
