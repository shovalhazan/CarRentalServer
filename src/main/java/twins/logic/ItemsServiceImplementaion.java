package twins.logic;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twins.boundaries.ItemBoundary;
import twins.boundaries.Location;
import twins.dal.CreatedBy;
import twins.dal.ItemEntity;
import twins.dal.ItemHandler;
import twins.dal.ItemId;
import twins.dal.UserEntity;
import twins.dal.UserId;
import twins.dal.UserRole;

@Service
public class ItemsServiceImplementaion implements ExtendedItemService{
	private ItemHandler itemHandler;
	private DataConverter dataConverter;
	private String dummy;
	private UsersServiceImplementation userService;
		
	
	@Autowired
	public ItemsServiceImplementaion(ItemHandler itemHandler,UsersServiceImplementation userService) {
		super();
		this.itemHandler = itemHandler;
		this.dataConverter =  new DataConverter();
		this.userService = userService;
	}
	// have spring initialize the dummy value using property: spring.application.name 
		// or generate default value if property does not exist: "dummyValue"
	@Value("${spring.application.name:dummyValue}")
	public void setDummy(String dummy) {
			this.dummy = dummy;
		}
		
		
	// have spring invoke this operation after initializing Spring bean
	@PostConstruct
	public void init() {
			System.err.println("dummy: " + this.dummy);
	}
	
	@Override
	@Transactional
	public void deleteAllItems(String adminSpace, String adminEmail) {
		UserEntity userEntity = this.userService.extractSpecificUser(adminSpace, adminEmail);
		
		if(!userEntity.getRole().toString().equals("ADMIN"))
		{
			throw new RuntimeException("Delete is only allowded for admin users.");
		}
		this.itemHandler
		.deleteAll();	
	}

	@Override
	@Transactional (readOnly = false)
	public ItemBoundary createItem(String userSpace, String userEmail, ItemBoundary itemBoundary) {
		
		UserRole role = userService.getUserRole(userSpace, userEmail); 
		
		if(!role.toString().equals("MANAGER"))
		{
			throw new RuntimeException("Invalid operation for this user .");
		}
		
		String id = this.dummy + "_" + UUID.randomUUID().toString();
		ItemEntity entity = this.convertToEntity(itemBoundary);
		entity.setCreatedBy(userEmail+"_"+userSpace);
		entity.setId(id);
		entity.setCreatedTimestamp(new Date());
		
		this.itemHandler.save(entity);
		
		return this.convertToBoundary(entity);
		
	}


	@Override
	@Transactional 
	public ItemBoundary updateItem(String userSpace,String userEmail,String itemSpace,String itemId,ItemBoundary update) {
		
		UserEntity userEntity = this.userService.extractSpecificUser(userSpace, userEmail);
		
		if(!userEntity.getRole().toString().equals("MANAGER"))
		{
			throw new RuntimeException("Invalid operation for this user details.");
		}
		
		Optional<ItemEntity> op = this.itemHandler
				.findById(itemId);
		
		ItemEntity updatedEntity;
			if (op.isPresent()) {
				ItemEntity existing = op.get();
				updatedEntity = this.convertToEntity(update);
				updatedEntity.setId(itemId);
				updatedEntity.setCreatedTimestamp(existing.getCreatedTimestamp());
				updatedEntity.setCreatedBy(userEmail+"_"+userSpace);
				this.itemHandler
					.save(updatedEntity);
			}
			else 
			{
				throw new RuntimeException(); // TODO: return status = 404 instead of status = 500 
			}
		return this.convertToBoundary(updatedEntity);
		
	}

	@Override
	@Transactional(readOnly = true)
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		
		UserRole role = userService.getUserRole(userSpace, userEmail);
		
		if(role.toString().equals("ADMIN"))
		{
			throw new RuntimeException("Invalid operation for this user .");
		}
		
		Optional<ItemEntity> existing = this.itemHandler
				.findById(itemId);
		
		if (existing.isPresent()) {
			
			ItemEntity entity = existing.get();
			
			if(entity.getActive() || role.toString().equals("MANAGER")) //CHECKING IF USER IS ALLOWED TO EXTRACT THE ITEM
				return this.convertToBoundary(entity);
			else
				throw new RuntimeException("User is not allowed to get the specific item.");
		}
		else 
		{
			throw new RuntimeException("item could not be found");
		}	
	}

	@Override
	@Transactional(readOnly = true) // handle race condition
	public List<ItemBoundary> getAllItems(String userSpace,String userEmail) {
		
		Iterable<ItemEntity> allEntities = this.itemHandler
			.findAll();
		List<ItemBoundary> rv = new ArrayList<>(); 
		for (ItemEntity entity : allEntities) {
			ItemBoundary boundary = this.convertToBoundary(entity);
			rv.add(boundary);
		}
		return rv;
	}
	
	@Override
	@Transactional (readOnly = true)
	public List<ItemBoundary> getAllItems(String space,String email, int size, int page) {
		
		UserEntity userEntity = this.userService.extractSpecificUser(space, email);
		
		if(userEntity.getRole().toString().equals("ADMIN"))
		{
			throw new RuntimeException("Invalid operation for this user details.");
		}
		
		boolean isManager = userEntity.getRole().toString().equals("MANAGER");
		
		List<ItemBoundary> itemBoundaries;
		if(isManager) {
			
			itemBoundaries = new ArrayList<ItemBoundary>();
			
			List<ItemEntity> itemEntites = this.itemHandler
					.findAll(PageRequest.of(page,size, Direction.DESC,"id")).getContent();
			
			for(ItemEntity itemEntity: itemEntites)
			{
				ItemBoundary ib = convertToBoundary(itemEntity);
				itemBoundaries.add(ib);
			}
		}
		else
		{
			itemBoundaries = getItemsByActive(page, size);
		}
		return itemBoundaries;
		
	}

	
	@Override
	@Transactional(readOnly = true) 
	public List<ItemBoundary> getItemsByActive(int page,int size) {
		
		List<ItemEntity> itemEntites = this.itemHandler
				.findAll(PageRequest.of(page,size, Direction.DESC,"id")).getContent();
		
		List<ItemBoundary> itemBoundaries = new ArrayList<ItemBoundary>();
		
		for(ItemEntity itemEntity: itemEntites)
		{
			if(itemEntity.getActive())
			{
				ItemBoundary ib = convertToBoundary(itemEntity);
				itemBoundaries.add(ib);
			}
		}
		
		return itemBoundaries;
	}

	@Override
	@Transactional(readOnly = true) 
	public List<ItemBoundary> getItemsByCompanyAndYear(String company ,String year) {
		
	
		Iterable<ItemEntity> allEntities = this.itemHandler
				.findAll();
		
		List<ItemBoundary> itemBoundaries = new ArrayList<ItemBoundary>();
		
		for(ItemEntity itemEntity: allEntities)
		{
				
				ItemBoundary ib = convertToBoundary(itemEntity);
				
					if( ib.getActive() && ib.getItemAttributes().get("brand").equals(company)&&
							String.valueOf(ib.getItemAttributes().get("manufactureYear")).equals(String.valueOf(year)))
					{
						itemBoundaries.add(ib);
					}
							
				}
		
		return itemBoundaries;
	}
	
	public ItemEntity convertToEntity(ItemBoundary boundary) {
		ItemEntity entity = new ItemEntity();
		
		entity.setName(boundary.getName());
		entity.setType(boundary.getType());
		entity.setActive(boundary.getActive());
		entity.setLng(boundary.getLocation().getLng());
		entity.setLat(boundary.getLocation().getLat());
		entity.setItemAttributes(this.dataConverter.marshall(boundary.getItemAttributes()));
		return entity;
	}
	
	private ItemBoundary convertToBoundary(ItemEntity entity) {
		ItemBoundary boundary = new ItemBoundary();
		String[] id = entity.getId().split("_");
		boundary.setItemId(new ItemId(id[0], id[1]));
		boundary.setType(entity.getType());
		boundary.setName(entity.getName());
		boundary.setActive(entity.getActive());
		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		String[] createdBy = entity.getCreatedBy().split("_");
		boundary.setCreatedBy(new CreatedBy(new UserId(createdBy[0],createdBy[1])));
		boundary.setLocation(new Location(entity.getLng(), entity.getLat()));
		String details = entity.getItemAttributes();
		Map<String, Object> moreDetailsMap = this.dataConverter.unmarshall(details, Map.class);
		boundary.setItemAttributes(moreDetailsMap);
		return boundary;
	}
	
	
	
	public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
	
}

