package twins.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twins.boundaries.ItemBoundary;
import twins.boundaries.OperationBoundary;
import twins.dal.InvokedBy;
import twins.dal.Item;
import twins.dal.ItemEntity;
import twins.dal.ItemId;
import twins.dal.OperationEntity;
import twins.dal.OperationHandler;
import twins.dal.OperationId;
import twins.dal.UserEntity;
import twins.dal.UserId;
import twins.dal.UserRole;

@Service
public class OperationServiceImplementation implements ExtendedOperationsService{

	private OperationHandler OperationHandler;
	private DataConverter dataConverter;
	private UsersServiceImplementation userService;
	private ItemsServiceImplementaion itemsService;

	@Autowired
	public OperationServiceImplementation(OperationHandler oph,UsersServiceImplementation userService,ItemsServiceImplementaion itemsService) {
		this.OperationHandler = oph;
		this.dataConverter = new DataConverter();
		this.userService = userService;
		this.itemsService = itemsService;
	}
	
	@Override
	@Transactional(readOnly = false) 
	public Object invokeOperation(OperationBoundary operation) {
		String userSpace = operation.getInvokeBy().getUserId().getSpace();
		String userEmail = operation.getInvokeBy().getUserId().getEmail();
		
		UserRole role = userService.getUserRole(userSpace, userEmail); 
		
		if(!role.toString().equals("PLAYER"))
		{
			throw new RuntimeException("Invalid operation for this user details.");
		}
		
		String itemSpace = operation.getItem().getItemId().getSpace();
		String itemId = itemSpace+"_"+operation.getItem().getItemId().getId();
		
		ItemBoundary opItem = null;
		
		if(!operation.getType().equals("SearchCar"))
		{
			opItem = itemsService.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
			
			if(opItem == null || !opItem.getActive()) {
				throw new RuntimeException("Invalid operation.");
			}
		}
		
		switch(operation.getType()) {
		
		case "SearchCar":
			  List<ItemBoundary> items= itemsService.getItemsByCompanyAndYear((String)operation.getOperationAttributes().get("brand"),
					  (String)operation.getOperationAttributes().get("manufactureYear"));
			  return items;
			  
		  case "RentCar":
			  if((boolean)opItem.getItemAttributes().get("isTaken") == true)
				  throw new RuntimeException("Car is already in use.");
			  opItem.getItemAttributes().put("isTaken",true);

			  break; 
			  
		  case "ReturnCar":
			  if((boolean)opItem.getItemAttributes().get("isTaken") == false)
				  throw new RuntimeException("Car is already in use.");
			  opItem.getItemAttributes().put("isTaken",false);

		    break;
		    
		  	default:
		    
		}
		userService.changeUserRole(userSpace, userEmail, UserRole.MANAGER);
		itemsService.updateItem(userSpace,userEmail,itemSpace,itemId,opItem);
		userService.changeUserRole(userSpace, userEmail, UserRole.PLAYER);
		OperationEntity opEntity = convertToEntity(operation);
		
		opEntity.setCreatedTimestamp(new Date());
		opEntity.setId(UUID.randomUUID().toString());
		opEntity.setSpace(itemSpace);
		OperationHandler.save(opEntity);
		return convertToBoundary(opEntity);
		
	}
	
	
	@Override
	@Transactional(readOnly = false) 
	public OperationBoundary invokeAsyncOperation(OperationBoundary operation) {
		String userSpace = operation.getInvokeBy().getUserId().getSpace();
		String userEmail = operation.getInvokeBy().getUserId().getEmail();
		
		UserRole role = userService.getUserRole(userSpace, userEmail); 
		
		if(!role.toString().equals("PLAYER"))
		{
			throw new RuntimeException("Invalid operation for this user details.");
		}
		
		try {
			Thread.sleep(5000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		OperationEntity opEntity = convertToEntity(operation);
		
		opEntity.setCreatedTimestamp(new Date());
		opEntity.setId(UUID.randomUUID().toString());
		opEntity.setSpace(opEntity.getItemSpace());
		opEntity.setItemId(UUID.randomUUID().toString());
		OperationHandler.save(opEntity);
		
		return convertToBoundary(opEntity);
	}
	
	public OperationEntity convertToEntity( OperationBoundary boundary) {
		OperationEntity entity = new OperationEntity();
		entity.setSpace(boundary.getOperationId().getSpace());
		entity.setType(boundary.getType());
		entity.setItemId(boundary.getItem().getItemId().getId());
		entity.setItemSpace(boundary.getItem().getItemId().getSpace());
		entity.setInvokeById(boundary.getInvokeBy().getUserId().getEmail() + "_" + boundary.getInvokeBy().getUserId().getSpace());
		entity.setOperationAttributes(this.dataConverter.marshall(boundary.getOperationAttributes()));
		
		return entity;	
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail,int page,int size) {
		UserRole role = userService.getUserRole(adminSpace, adminEmail); 
		
		if(!role.toString().equals("ADMIN"))
		{
			throw new RuntimeException("Invalid operation for this user details.");
		}
		
		Iterable<OperationEntity> allEntities = this.OperationHandler
				.findAll();
		List<OperationBoundary> rv = new ArrayList<>(); 
		for (OperationEntity entity : allEntities) 
		{
			OperationBoundary boundary = this.convertToBoundary(entity);
			rv.add(boundary);
		}
		return rv;
	}
	
	private OperationBoundary convertToBoundary(OperationEntity entity) {
		OperationBoundary boundary = new OperationBoundary();
		boundary.setOperationId(new OperationId(entity.getSpace(),entity.getId()));
		boundary.setType(entity.getType());
		String[] invokedBy = entity.getInvokeById().split("_");
		String email = invokedBy[0] , space = invokedBy[1];
		boundary.setInvokeBy(new InvokedBy(new UserId(email,space)));
		boundary.setItem(new Item(new ItemId(entity.getItemSpace(),entity.getItemId())));
		Map<String, Object> moreDetailsMap = this.dataConverter.unmarshall(entity.getOperationAttributes(), Map.class);
		boundary.setOperationAttributes(moreDetailsMap);
		return boundary;
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		UserRole role = userService.getUserRole(adminSpace, adminEmail); 
		
		if(!role.toString().equals("ADMIN"))
		{
			throw new RuntimeException("Invalid operation for this user details.");
		}
		return new ArrayList<OperationBoundary>();
	}
	
	@Override
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		OperationHandler.deleteAll();
	}

}
