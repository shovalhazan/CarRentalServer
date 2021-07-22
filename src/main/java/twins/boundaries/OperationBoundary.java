package twins.boundaries;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import twins.dal.InvokedBy;
import twins.dal.Item;
import twins.dal.OperationId;

public class OperationBoundary {
	private OperationId operationId;
	private String type;
	private Item item;
	private Date createdTimestamp;
	private InvokedBy invokeBy;
	private Map <String,Object> operationAttributes;
	
	
	public OperationBoundary() {
		item = new Item();
		invokeBy = new InvokedBy();
		operationAttributes = new HashMap<String,Object>();
		operationId = new OperationId();
		createdTimestamp = new Date();
	}
	
	public OperationBoundary(OperationId operationId,String type,Item item,
			InvokedBy invokeBy,HashMap <String,Object> operationAttributes) {
		super();
		this.item = item;
		this.type = type;
		this.invokeBy = invokeBy;
		this.operationAttributes = operationAttributes;
		this.operationId = operationId;
		createdTimestamp = new Date();
	}
	
	
	
	public OperationId getOperationId() {
		return operationId;
	}
	
	public void setOperationId(OperationId operationId) {
		this.operationId = operationId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	
	public InvokedBy getInvokeBy() {
		return invokeBy;
	}
	
	public void setInvokeBy(InvokedBy invokeBy) {
		this.invokeBy = invokeBy;
	}
	
	public Map<String, Object> getOperationAttributes() {
		return operationAttributes;
	}
	
	public void setOperationAttributes(Map<String, Object> operationAttributs) {
		this.operationAttributes = operationAttributs;
	}
}
