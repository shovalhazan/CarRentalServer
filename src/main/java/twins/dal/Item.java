package twins.dal;


public class Item {
	private ItemId itemId;

	public Item() {
		itemId = new ItemId();
	}
	
	public Item(ItemId itemId) {
		super();
		this.itemId = itemId;
	}
	
	public ItemId getItemId() {
		return itemId;
	}

	public void setItemId(ItemId itemId) {
		this.itemId = itemId;
	} 
}
