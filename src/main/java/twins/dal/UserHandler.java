package twins.dal;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface UserHandler extends PagingAndSortingRepository<UserEntity, String> {
	public List<UserEntity> findUserByRole(
			@Param("role") UserRole role);
	
}
