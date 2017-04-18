package replication.postgres.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import replication.postgres.model.User;

public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory m_sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.m_sessionFactory = sessionFactory;
	}

	@Override
	public void save(User u) {
		Session session = this.m_sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(u);
		tx.commit();
		session.close();
	}

	@Override
	public void saveBatch(List<User> users){
		Session session = this.m_sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (User user : users) {
			session.persist(user);	
		}
		tx.commit();
		session.close();
	}
	@Override
	public List<User> getUsers() {

		return null;
	}

}
