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
public class SortWeight {

	@Id
	private Timestamp weightsUpdateTime;
	private Integer dealerUserId;
	private String dataPeriod;
	private String weights1;
	@Column(insertable = false)
	private Double weights1Ratio;
	private String weights2;
	@Column(insertable = false)
	private Double weights2Ratio;
	private String weights3;
	@Column(insertable = false)
	private Double weights3Ratio;
	private String weights4;
	@Column(insertable = false)
	private Double weights4Ratio;
	private String weights5;
	@Column(insertable = false)
	private Double weights5Ratio;
	private String weights6;
	@Column(insertable = false)
	private Double weights6Ratio;
}
