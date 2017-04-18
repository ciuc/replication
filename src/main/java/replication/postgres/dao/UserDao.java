package replication.postgres.dao;

import java.util.List;

import replication.postgres.model.User;

public interface UserDao {

	public void save(User u);
	public List<User> getUsers();
	void saveBatch(List<User> users);
	
}
