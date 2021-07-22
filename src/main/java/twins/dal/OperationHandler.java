package twins.dal;

import org.springframework.data.repository.CrudRepository;

public interface OperationHandler  extends CrudRepository<OperationEntity, String> {

}
