package replication.mongo;

import java.util.List;

import replication.postgres.model.User;

public interface ReplicationManager {

	void insertUser(User u);

	void insertUsers(List<User> users);

	void cleanup();

	
}
