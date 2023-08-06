package tw.idv.tibame.products.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class RestockRecord {

	@Id
	private String restockId;
	private Integer productId;
	private String productSpecId;
	private String restockMemberId;
	private Integer beforeRestockStock;
	private Integer restockQuantity;
	private Integer afterRestockStock;
	@Column(insertable = false)
	private Date restockDate;
}
