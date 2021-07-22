package twins.dal;

public class InvokedBy {
	
	private UserId userId;
	
	public InvokedBy(UserId userId) {
		this.userId = userId;
	}
	
	public InvokedBy() {
	
	}
	
	public UserId getUserId() {
		return userId;
	}
	
	public void setUserId(UserId userId) {
		this.userId = userId;
	}

}
