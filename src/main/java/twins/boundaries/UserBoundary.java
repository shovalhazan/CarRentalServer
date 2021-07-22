package twins.boundaries;

import twins.dal.UserId;
import twins.dal.UserRole;

public class UserBoundary {
	private UserId userId;
	private UserRole role;
	private String userName;
	private String avatar;

	public UserBoundary() {}
	
	public UserBoundary(UserId userId, UserRole role, String username, String avatar) {
		this.userId = userId;
		this.role = role;
		this.userName = username;
		this.avatar = avatar;
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}