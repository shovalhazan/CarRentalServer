package twins.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ItemHandler extends PagingAndSortingRepository<ItemEntity, String> {
	// C - Create

	public List<ItemEntity> findAllByActive(
			@Param("active") boolean active,
			Pageable pageable);
	
	
	
	
	
	
	
	// R - Read
	
	// U - Update
	
	// D - Delete
}
