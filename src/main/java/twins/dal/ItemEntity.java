package twins.dal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ITEM_TABLE")
public class ItemEntity {
	
	//itemId
	private String id;
	
	//createdBy
	private String createdBy;
	private String name;
	private String type;
	private boolean active;
	private Date createdTimestamp;
	private double lng;
	private double lat;
	private String itemAttributes;
	
	public ItemEntity(String id,String createdBy,String name,String type,boolean active,Date createdTimestamp,double lng,double lat,String itemAttributes) {
		this.id = id;
		this.createdBy = createdBy;
		this.name = name;
		this.type = type;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.lng = lng;
		this.lng = lng;
		this.itemAttributes = itemAttributes;
	}
	
	public ItemEntity() {}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MESSAGE_TIMESTAMP") //  set column name: MESSAGE_TIMESTAMP
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	
	@Lob  // configure column type to support really large JSONs
	public String getItemAttributes() {
		return itemAttributes;
	}
	public void setItemAttributes(String itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
}
