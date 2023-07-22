package tw.idv.tibame.products.entity;

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
public class FrontendPage {
	@Id
	private String pageId;
	private String pageName;
	private String pageCategoryId;
}
