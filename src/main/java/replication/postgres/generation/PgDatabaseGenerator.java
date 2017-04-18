package replication.postgres.generation;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import replication.postgres.dao.UserDao;
import replication.postgres.model.User;

/**
 * Created by ciuc on 4/14/17.
 */
public class PgDatabaseGenerator {

	@Autowired
	private UserDao m_userDao;

	public UserDao getUserDao() {
		return m_userDao;
	}

	public User getRandomUser() {
		User u = new User();
		u.setName(getRandomName());
		return u;
	}

	private String getRandomName() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}
}
