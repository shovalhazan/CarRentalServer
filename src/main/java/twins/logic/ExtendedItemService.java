package twins.logic;

import java.util.List;

import twins.boundaries.ItemBoundary;

public interface ExtendedItemService extends ItemsService{
	public List<ItemBoundary> getItemsByActive(int page,int size);
	public List<ItemBoundary> getItemsByCompanyAndYear(String company ,String year);
	public List<ItemBoundary> getAllItems(String space, String email, int size, int page);
}