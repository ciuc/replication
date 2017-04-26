package replication.postgres.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import replication.postgres.dao.UserDao;
import replication.postgres.model.User;

/**
 * Created by ciuc on 4/14/17.
 */
public class PgDatabaseGenerator {

	@Autowired
	private UserDao m_userDao;

	@Autowired
	private LocalSessionFactoryBean m_sessionFactory;

	public UserDao getUserDao() {
		return m_userDao;
	}

	public User getRandomUser() {
		User u = new User();
		u.setName(getRandomName());
		return u;
	}

	public Number generateUsers(int count) {
		List<User> users = new ArrayList<User>();
		System.out.println("Generating users");
		for (int i = 0; i <= count; i++) {
			users.add(getRandomUser());

		}
		System.out.println("Starting saving batch of " + count + " users");
		getUserDao().saveBatch(users);
		System.out.println("Finished saving batch of " + count + " users");

		Session session = m_sessionFactory.getObject().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Number result = (Number) session.createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
		tx.commit();
		return result;

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
