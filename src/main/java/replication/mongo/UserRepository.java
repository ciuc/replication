package replication.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import replication.postgres.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{

	@Query(value="{}", fields="{_id : 1}")
	public List<User> findUsersIds();
}
