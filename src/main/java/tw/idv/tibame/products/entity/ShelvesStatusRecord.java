package tw.idv.tibame.products.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
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
	private Integer productId;
	private String productSpecId;
	private String shelvesMemberId;
	private String statusModify;
	@Column(insertable = false)
	private Timestamp statusModifyTime;
}
