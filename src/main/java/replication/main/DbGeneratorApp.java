package replication.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import replication.postgres.generation.PgDatabaseGenerator;

public class DbGeneratorApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

		PgDatabaseGenerator pgDatabaseGenerator = context.getBean(PgDatabaseGenerator.class);

		Number users = pgDatabaseGenerator.generateUsers(10000);

		System.out.println("Now in db: " + users + " users");

	}
}
