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
public class SortWeight {

	@Id
	private Timestamp weightsUpdateTime;
	private String dealerUserId;
	private String dataPeriod;
	private String weights1;
	private Double weights1Ratio;
	private String weights2;
	private Double weights2Ratio;
	private String weights3;
	private Double weights3Ratio;
	private String weights4;
	private Double weights4Ratio;
	private String weights5;
	private Double weights5Ratio;
	private String weights6;
	private Double weights6Ratio;
}
