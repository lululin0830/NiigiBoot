package tw.idv.tibame.users.bean.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReq {
	private  String userId;
	 String password;
	 String financialAuthority;
	 String customerServiceAuthority;
	 String marketingAuthority;
	 String hrAuthority;
}
