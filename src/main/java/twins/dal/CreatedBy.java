package twins.dal;

public class CreatedBy {
	  private UserId userId ;

	  public CreatedBy() {
	  	super();
	  }


	  @Override
	  public String toString() {
	  	return "CreatedBy [userId=" + userId + "]";
	  }


	  public CreatedBy(UserId userId) {
	  	super();
	  	this.userId = userId;
	  }

	  public UserId getUserId() {
	  	return userId;
	  }

	  public void setUserId(UserId userId) {
	  	this.userId = userId;
	  }
	    
}
