package tw.idv.tibame.products.entity;

import java.sql.Timestamp;

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
public class ShelvesStatusRecord {

	@Id
	private String shelvesStatusId;
	private String productId;
	private String productSpecId;
	private String shelvesMemberId;
	private String statusModify;
	private Timestamp statusModifyTime;
}
