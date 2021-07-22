package twins.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twins.boundaries.NewUserDetailsBoundary;
import twins.boundaries.OperationBoundary;
import twins.boundaries.UserBoundary;
import twins.dal.UserEntity;
import twins.dal.UserHandler;
import twins.dal.UserId;
import twins.dal.UserRole;

@Service
public class UsersServiceImplementation implements ExtendedUserService{

	private UserHandler userHandler;
	private String dummy;
	
	@Autowired
	public UsersServiceImplementation(UserHandler uh) {
		super();
		this.userHandler = uh;
	}
	
	// have spring initialize the dummy value using property: spring.application.name 
	// or generate default value if property does not exist: "dummyValue"
	@Value("${spring.application.name:dummyValue}")
	public void setDummy(String dummy) {
			this.dummy = dummy;
	}
	
	
	
	@Override
	@Transactional(readOnly = false) 
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {
		
		UserEntity updatedEntity = null;
		try
		{
			UserEntity extractedUser = extractSpecificUser(userSpace, userEmail);
			updatedEntity = convertToEntity(update);
			updatedEntity.setId(extractedUser.getId());
			userHandler.save(updatedEntity);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		return convertToBoundary(updatedEntity);
	}
	
	private final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public UserBoundary createUser(NewUserDetailsBoundary newUser) {
		UserEntity userEntity = new UserEntity();
		System.out.println(newUser.toString());
		try
		{
			areUserDetailsValid(newUser);
			userEntity.setAvatar(newUser.getAvatar());
			userEntity.setUserName(newUser.getUserName());
			userEntity.setRole(newUser.getRole());
			userEntity.setId(newUser.getEmail() + "_" + this.dummy);
			userHandler.save(userEntity);
		}
        catch(Exception e)
		{
        	e.printStackTrace();
		}
		return convertToBoundary(userEntity);
	}

	public boolean areUserDetailsValid(NewUserDetailsBoundary newUser) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(newUser.getEmail());
        if(!matcher.find())
        {
        	throw new RuntimeException("Invalid email inserted");
        }
        if(newUser.getUserName() == null || newUser.getAvatar() == null || newUser.getAvatar().equals(""))
        {
        	throw new RuntimeException("Invalid User name/avatar inserted.");
        }
//        if(newUser.getUserName() == null)
//        	throw new RuntimeException("USER NAME NULL.");
//        if(newUser.getAvatar() == null)
//        	throw new RuntimeException("AVATAR NULL");
//    	if(newUser.getAvatar().equals(""))
//			throw new RuntimeException("USER NAME EMPTY");
        return true;
        
	}
	
	@Override
	public UserBoundary login(String userSpace, String userEmail) {
		UserEntity userEntity = extractSpecificUser(userSpace, userEmail);
		return convertToBoundary(userEntity);
	}
	
	@Override
	@Transactional (readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail,int size,int page) {
		
		UserEntity userEntity = extractSpecificUser(adminSpace,adminEmail);
		
		if(!userEntity.getRole().toString().equals("ADMIN"))
		{
			throw new RuntimeException("Extracting users is only allowded for admin users.");
		}
		
		Iterable<UserEntity> allEntities = this.userHandler
				.findAll(PageRequest.of(page,size, Direction.DESC,"id")).getContent();
		 
		List<UserBoundary> rv = new ArrayList<>(); 
		for (UserEntity entity : allEntities) {
			UserBoundary boundary = this.convertToBoundary(entity);
			rv.add(boundary);
		}
		
		return rv;
	}

	@Override
	@Transactional (readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		
		UserEntity userEntity = extractSpecificUser(adminSpace,adminEmail);
		
		if(!userEntity.getRole().toString().equals("ADMIN"))
		{
			throw new RuntimeException("Extracting users is only allowded for admin users.");
		}
		
		Iterable<UserEntity> allEntities = this.userHandler
				.findAll();
		List<UserBoundary> rv = new ArrayList<>(); 
		for (UserEntity entity : allEntities) {
			UserBoundary boundary = this.convertToBoundary(entity);
			rv.add(boundary);
		}
		return rv;
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		UserEntity userEntity = extractSpecificUser(adminSpace,adminEmail);
		
		if(!userEntity.getRole().toString().equals("ADMIN"))
		{
			throw new RuntimeException("Extracting users is only allowded for admin users.");
		}
		userHandler.deleteAll();
	}
	
	
	public UserRole getUserRole(String userSpace,String userEmail) {
		
		UserEntity userEntity = extractSpecificUser(userSpace,userEmail);
		return userEntity.getRole();
	}
	
	
	public UserEntity convertToEntity( UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		entity.setUserName(boundary.getUserName());
		entity.setAvatar(boundary.getAvatar());
		entity.setRole(boundary.getRole());
		return entity;	
	}
	
	public UserBoundary convertToBoundary(UserEntity entity) {
		
		UserBoundary boundary = new UserBoundary();
		String[] id = entity.getId().split("_");
		
		boundary.setUserId(new UserId(id[0],id[1]));
		boundary.setRole(entity.getRole());
		boundary.setUserName(entity.getUserName());
		boundary.setAvatar(entity.getAvatar());
		
		return boundary;	
	}


	@Override
	public UserEntity extractSpecificUser(String userSpace, String userEmail) {
		Optional<UserEntity> optEntity = userHandler.findById(userEmail+"_"+userSpace); //get user from users DB
		
		if(!optEntity.isPresent())
		{
			throw new RuntimeException("Invalid operation for this user .");
		}

		return optEntity.get();
	}

	@Transactional(readOnly = false) 
	public void changeUserRole(String space,String email,UserRole role) {
		UserEntity userEntity = extractSpecificUser(space, email);
		userEntity.setRole(role);
		userHandler.save(userEntity);
	}

}
