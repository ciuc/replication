package replication.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import replication.mongo.ReplicationManagerImpl;
import replication.postgres.dao.UserDao;
import replication.postgres.model.User;

public class ReplicationApp {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		ReplicationManagerImpl replicationManager = context.getBean(ReplicationManagerImpl.class);
		UserDao userDao = context.getBean(UserDao.class);

		replicationManager.cleanup();

		long start = System.currentTimeMillis();
		List<User> users = userDao.getUsers();
		replicationManager.insertUsers(users);
		long end = System.currentTimeMillis();

		System.out.println("Took " + (end - start) / 1000 + " seconds");

	}
}
