package twins.dal;

public class UserId {
	private String email;
	private String space;
	
	public UserId(String email, String space) {
		super();
		this.email = email;
		this.space = space;
	}
	
	public UserId(String email) {
		super();
		this.email = email;
	}

	public UserId() {
		super();
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSpace() {
		return space;
	}
	
	public void setSpace(String space) {
		this.space = space;
	}
	
	@Override
	public String toString() {
		return "UserId [email=" + email + ", space=" + space + "]";
	}
	
}
