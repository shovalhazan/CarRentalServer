package twins.logic;
import java.util.List;

import twins.boundaries.ItemBoundary;

public interface ItemsService {
	public ItemBoundary createItem(String userSpace,String userEmail ,ItemBoundary itemBoundary) ;
	public ItemBoundary updateItem(String userSpace,String userEmail,String itemSpace,String itemId,ItemBoundary update);
	public ItemBoundary getSpecificItem(String userSpace,String userEmail,String itemSpace,String itemId);
	public void deleteAllItems(String adminSpace,String adminEmail);
	public List<ItemBoundary>getAllItems(String adminSpace,String adminEmail);
}
