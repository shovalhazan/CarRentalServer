package twins.logic;

import java.util.List;

import twins.boundaries.UserBoundary;
import twins.dal.UserEntity;

public interface ExtendedUserService extends UsersService {
	public UserEntity extractSpecificUser(String userSpace, String userEmail);
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail,int size,int page);
}
