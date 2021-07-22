package twins.boundaries;

import java.util.Date;
import java.util.Map;

import twins.dal.CreatedBy;
import twins.dal.ItemId;
//import twins.dal.Location;

public class ItemBoundary {
	private ItemId itemId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private CreatedBy createdBy;
	private Location location;
	private Map <String,Object> itemAttributes;
	
	
	
	public ItemBoundary(ItemId itemId, String type, String name, boolean active, Date createdTimestamp,
			CreatedBy createdBy,Location location, Map<String, Object> itemAttributes) {
		super();
		this.itemId = itemId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.location = location;
		this.itemAttributes = itemAttributes;
	}
	


	public ItemBoundary() {
	}
	

	@Override
	public String toString() {
		return "ItemBoundary [itemId=" + itemId + ", type=" + type + ", name=" + name + ", active=" + active
				+ ", createdTimestamp=" + createdTimestamp + ", createdBy=" + createdBy + ", location=" + location
				+ ", itemAttributes=" + itemAttributes + "]";
	}
	
	
	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public ItemId getItemId() {
		return itemId;
	}

	public void setItemId(ItemId itemId) {
		this.itemId = itemId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public CreatedBy getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}

	public Map<String, Object> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, Object> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
	
}
