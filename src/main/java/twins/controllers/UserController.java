package twins.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.boundaries.NewUserDetailsBoundary;
import twins.boundaries.UserBoundary;
import twins.logic.ExtendedUserService;

@RestController
public class UserController {

	private ExtendedUserService usersService;
	
	@Autowired
	public void setUsersService(ExtendedUserService usersService) {
		this.usersService = usersService;
	}
	
		@CrossOrigin(origins = "http://localhost:3000")
		@RequestMapping(
				path = "/twins/users",
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE,
				consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary createNewUser(
				@RequestBody NewUserDetailsBoundary newUserDetails ) {
			
			return this.usersService.createUser(newUserDetails);
		}
		
		@CrossOrigin(origins = "http://localhost:3000")
		@RequestMapping(
				path = "/twins/users/login/{userSpace}/{userEmail}",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary Login(@PathVariable String userSpace,
				@PathVariable String userEmail) {
			return this.usersService.login(userSpace, userEmail);
		}
		
		@CrossOrigin(origins = "http://localhost:3000")
		@RequestMapping(path = "/twins/users/{userSpace}/{userEmail}",
				method = RequestMethod.PUT,
				consumes = MediaType.APPLICATION_JSON_VALUE)
		public void UpdateUser(@RequestBody UserBoundary user,
				@PathVariable("userSpace") String userSpace,
				@PathVariable("userEmail") String userEmail) {
			this.usersService.updateUser(userSpace, userEmail, user);
			
		}
		

}