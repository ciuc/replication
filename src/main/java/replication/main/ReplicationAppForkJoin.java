package replication.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import replication.mongo.ReplicationManagerForkJoin;

public class ReplicationAppForkJoin {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		ReplicationManagerForkJoin replicationManager = context.getBean(ReplicationManagerForkJoin.class);

		replicationManager.cleanup();

		long start = System.currentTimeMillis();
		replicationManager.insertPagedUsers(10000);
		long end = System.currentTimeMillis();

		System.out.println("Took " + (end - start) / 1000 + " seconds");

	}
}
