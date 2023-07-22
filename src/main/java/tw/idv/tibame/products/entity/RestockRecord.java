package tw.idv.tibame.products.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestockRecord {

	@Id
	private String restockId;
	private String productId;
	private String productSpecId;
	private String restockMemberId;
	private Integer beforeRestockStock;
	private Integer restockQuantity;
	private Integer afterRestockStock;
	private Date restockDate;
}
