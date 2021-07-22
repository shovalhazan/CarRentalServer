package twins.dal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="OPERATION_TABLE")
public class OperationEntity{

	//Id
	private String id;
	private String space;
	
	private String type;
	private String itemId;
	private String itemSpace;
	private Date createdTimestamp;
	private String invokeById;
	private String operationAttributes;
	
	
	public OperationEntity() {
		createdTimestamp = new Date();
	}
	
	public OperationEntity(String space,String id,String type,String itemId,String itemSpace,
			String invokeById,String operationAttributes) {
		super();
		this.space = space;
		this.id = id;
		this.itemId = itemId;
		this.itemSpace = itemSpace;
		this.type = type;
		this.invokeById = invokeById;
		this.operationAttributes = operationAttributes;
		
		createdTimestamp = new Date();
	}
	
	public String getItemSpace() {
		return itemSpace;
	}

	public void setItemSpace(String itemSpace) {
		this.itemSpace = itemSpace;
	}

	@Column(name="SPACE")
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}
	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="TYPE") 
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="ITEM_ID") 
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OPERATION_TIMESTAMP") 
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	
	@Column(name="INVOKE_BY") 
	public String getInvokeById() {
		return invokeById;
	}
	
	public void setInvokeById(String invokeById) {
		this.invokeById = invokeById;
	}
	
	
	@Column(name="OPERATION_ATTRIBUTES") 
	public String getOperationAttributes() {
		return operationAttributes;
	}
	
	public void setOperationAttributes(String operationAttributes) {
		this.operationAttributes = operationAttributes;
	}
}

