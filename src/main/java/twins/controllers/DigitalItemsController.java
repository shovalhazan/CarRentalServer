package twins.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twins.boundaries.ItemBoundary;
import twins.logic.ExtendedItemService;

@RestController
public class DigitalItemsController {
	
	private ExtendedItemService itemService;

	@Autowired
	public void setItemService(ExtendedItemService itemService) {
		this.itemService = itemService;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping
	(
		path="/twins/items/{userSpace}/{userEmail}",
		method= RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE)
		public ItemBoundary createItem( @RequestBody ItemBoundary itemBoundary, @PathVariable("userSpace")String space
				,@PathVariable("userEmail") String email ) {
		
			return this.itemService.createItem(space,email,itemBoundary);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(
			path="/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary updateItem (
			@PathVariable("userSpace")String space
			,@PathVariable("userEmail")String email,@PathVariable("itemSpace")String itemSpace,@PathVariable("itemId")String itemId,@RequestBody ItemBoundary itemBoundary){
		    return this.itemService
			.updateItem(space,email,itemSpace, itemId,itemBoundary);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping
	(
		path="/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}",
		method= RequestMethod.GET,
	    produces = MediaType.APPLICATION_JSON_VALUE)
		public ItemBoundary getSpecificItem(@PathVariable("userSpace")String space
				,@PathVariable("userEmail")String email,@PathVariable("itemSpace")String itemSpace,@PathVariable("itemId")String itemId) {
			return this.itemService
				.getSpecificItem(space,email,itemSpace,itemId);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping
	(
		path="/twins/items/{userSpace}/{userEmail}",
		method= RequestMethod.GET,
	    produces = MediaType.APPLICATION_JSON_VALUE)
	
		public ItemBoundary[] getAllItems(
				@PathVariable("userSpace")String space,
				@PathVariable("userEmail")String email,
				@RequestParam(name="size", required = false, defaultValue = "20") int  size,
				@RequestParam(name="page", required = false, defaultValue = "0") int page){
		
			List<ItemBoundary> items= this.itemService.getAllItems(space,email,size,page);
			
			return items.toArray(new ItemBoundary[0]);
		
	}

}
