package tw.idv.tibame.users.entity;

public class Users implements java.io.Serializable{

	/**
	 * 平台使用者
	 */
	private static final long serialVersionUID = -6071858145966342981L;
	
	private Integer userId;
	private String userName;
	private String userAcct;
	private String password;
	private String financialAuthority;
	private String customerServiceAuthority;
	private String marketingAuthority;
	private String hrAuthority;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAcct() {
		return userAcct;
	}
	public void setUserAcct(String userAcct) {
		this.userAcct = userAcct;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFinancialAuthority() {
		return financialAuthority;
	}
	public void setFinancialAuthority(String financialAuthority) {
		this.financialAuthority = financialAuthority;
	}
	public String getCustomerServiceAuthority() {
		return customerServiceAuthority;
	}
	public void setCustomerServiceAuthority(String customerServiceAuthority) {
		this.customerServiceAuthority = customerServiceAuthority;
	}
	public String getMarketingAuthority() {
		return marketingAuthority;
	}
	public void setMarketingAuthority(String marketingAuthority) {
		this.marketingAuthority = marketingAuthority;
	}
	public String getHrAuthority() {
		return hrAuthority;
	}
	public void setHrAuthority(String hrAuthority) {
		this.hrAuthority = hrAuthority;
	}
}
