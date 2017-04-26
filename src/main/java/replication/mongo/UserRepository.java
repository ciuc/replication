package replication.mongo;

import org.springframework.data.repository.PagingAndSortingRepository;

import replication.postgres.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{

	
}
