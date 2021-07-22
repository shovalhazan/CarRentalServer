package twins.dal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="USER_TABLE")
public class UserEntity {
	private String id; //ID CONTAINS EMAIL_SPACE OF USER (WITH NO SPACE BETWEEN THEM!!!!!)
	private UserRole role;
	private String userName;
	private String avatar;

	public UserEntity() {
	}
	
	public UserEntity(String id, UserRole role, String username, String avatar) {
		this.id = id;
		this.role = role;
		this.userName = username;
		this.avatar = avatar;
	}

	@Id
	@Column(name="ID")
	public String getId() {
		return id;
	}

	public void setId(String userId) {
		this.id = userId;
	}
	
	@Column(name="NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	@Column(name="ROLE")
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Column(name="AVATAR")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}