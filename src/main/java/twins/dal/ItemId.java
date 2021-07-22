package twins.dal;

public class ItemId {
	private String space; // 2021b.shoval.hazan
	private String id; // 4e7b3596-0d96-44e9-9ffb-dadc1338f85d
	
	public ItemId() {}
	
	public ItemId(String space,String id) {
		super();
		this.space = space;
		this.id = id;
	}

	@Override
	public String toString() {
		return "ItemId [space=" + space + ", id=" + id + "]";
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
