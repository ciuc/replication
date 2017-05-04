package replication.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import replication.postgres.model.User;

public abstract class AbstractReplicationManager {

	@Autowired
	private UserRepository m_repository;

	public void insertUser(User u) {
		m_repository.save(u);
	}

	public void insertUsers(List<User> users) {
		for (User u : users) {
			insertUser(u);
		}
	}

	public abstract void insertPagedUsers(int page);

	public void cleanup() {
		m_repository.deleteAll();
	}
}
