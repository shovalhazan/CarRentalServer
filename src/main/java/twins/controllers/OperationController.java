package twins.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.boundaries.OperationBoundary;
import twins.logic.OperationsService;

@RestController
public class OperationController {
	
private OperationsService operationsService;
	
	@Autowired
	public void setOperationsService(OperationsService operationsService) {
		this.operationsService = operationsService;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(
			path="/twins/operations",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object invokeOperation(@RequestBody OperationBoundary ob) {
		return this.operationsService.invokeOperation(ob);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(
			path="/twins/operations/async",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary invokeAsyncOperation(@RequestBody OperationBoundary ob) {
		return this.operationsService.invokeAsyncOperation(ob);
	}
}
