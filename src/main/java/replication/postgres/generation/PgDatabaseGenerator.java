package replication.postgres.generation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import replication.postgres.dao.UserDao;
import replication.postgres.model.User;

/**
 * Created by ciuc on 4/14/17.
 */
public class PgDatabaseGenerator {
	@Autowired
	private DriverManagerDataSource m_driverManagerDatasource;

	private DriverManagerDataSource getDriverManagerDataSource() {
		return m_driverManagerDatasource;
	}

	// private void setDriverManagerDatasource(DriverManagerDataSource dMds) {
	// this.m_driverManagerDatasource = dMds;
	// }

	@Autowired
	private UserDao m_userDao;

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

		PgDatabaseGenerator pgDatabaseGenerator = context.getBean(PgDatabaseGenerator.class);
		try {
			Connection conn = pgDatabaseGenerator.m_driverManagerDatasource.getConnection();
			conn.createStatement();

			List<User> users = new ArrayList<User>();
			for (int count = 0; count <= 1000000; count++) {
				users.add(pgDatabaseGenerator.getRandomUser());

			}
			pgDatabaseGenerator.m_userDao.saveBatch(users);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}

	private User getRandomUser() {
		User u = new User();
		u.setName(getRandomName());
		return u;
	}

	private String getRandomName() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 7) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}
}
