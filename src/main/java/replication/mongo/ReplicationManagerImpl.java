package replication.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ReplicationManagerImpl {

	@Autowired
	private MongoTemplate m_mongoTemplate;
	
	
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		ReplicationManagerImpl replicationManager = context.getBean(ReplicationManagerImpl.class);
		
		
	}
}
