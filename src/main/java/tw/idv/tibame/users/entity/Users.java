package tw.idv.tibame.users.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.idv.tibame.core.entity.Core;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends Core{

	/**
	 * 平台使用者
	 */
	private static final long serialVersionUID = -6071858145966342981L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String userName;
	private String userAcct;
	private String password;
	@Column(insertable = false)
	private String financialAuthority;
	@Column(insertable = false)
	private String customerServiceAuthority;
	@Column(insertable = false)
	private String marketingAuthority;
	@Column(insertable = false)
	private String hrAuthority;
	
	public Users(String userAcct,String password) {
		this.userAcct = userAcct;
		this.password = password;
	}
}
