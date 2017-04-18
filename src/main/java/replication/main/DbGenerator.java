package replication.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import replication.postgres.generation.PgDatabaseGenerator;
import replication.postgres.model.User;

public class DbGenerator {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

		PgDatabaseGenerator pgDatabaseGenerator = context.getBean(PgDatabaseGenerator.class);

		List<User> users = new ArrayList<User>();
		for (int count = 0; count <= 1000000; count++) {
			users.add(pgDatabaseGenerator.getRandomUser());

		}
		pgDatabaseGenerator.getUserDao().saveBatch(users);

	}
}
