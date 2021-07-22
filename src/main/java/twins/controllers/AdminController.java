package twins.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twins.boundaries.OperationBoundary;
import twins.boundaries.UserBoundary;
import twins.logic.ExtendedOperationsService;
import twins.logic.ExtendedUserService;
import twins.logic.ItemsService;


@RestController
public class AdminController {

	private ExtendedUserService userService;
	private ExtendedOperationsService operationsService;
	private ItemsService itemsService;

	@Autowired
	public void setItemsService(ItemsService itemsService) {
		this.itemsService = itemsService;
	}
	
	@Autowired
	public void setOperationsService(ExtendedOperationsService operationsService) {
		this.operationsService = operationsService;
	}
	
	@Autowired
	public void setUserService(ExtendedUserService userService) {
		this.userService = userService;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(path = "/twins/admin/users/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteAllUsers(@PathVariable("userSpace") String userSpace,@PathVariable("userEmail") String userEmail) {
		this.userService.deleteAllUsers(userSpace, userEmail);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(path = "/twins/admin/items/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteAllItems(@PathVariable("userSpace") String userSpace,@PathVariable("userEmail") String userEmail) {
		this.itemsService.deleteAllItems(userSpace, userEmail);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(path = "/twins/admin/operations/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteAllOperations(@PathVariable("userSpace") String userSpace,@PathVariable("userEmail") String userEmail) {
		this.operationsService.deleteAllOperations(userSpace, userEmail);
		System.err.println("Deleted all Operations from space");
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(path = "/twins/admin/users/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@RequestParam(name="size", required = false, defaultValue = "20") int  size,
			@RequestParam(name="page", required = false, defaultValue = "0") int page) {
		
		List<UserBoundary> users = this.userService.getAllUsers(userSpace, userEmail,size,page);
		return users.toArray(new UserBoundary[0]);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(path = "/twins/admin/operations/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary[] exportAllOperations(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@RequestParam(name="size", required = false, defaultValue = "20") int  size,
			@RequestParam(name="page", required = false, defaultValue = "0") int page) {
		List<OperationBoundary> operations = this.operationsService.getAllOperations(userSpace, userEmail,size,page);
		return operations.toArray(new OperationBoundary[0]);
	}
}

