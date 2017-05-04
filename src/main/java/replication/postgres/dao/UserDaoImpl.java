package replication.postgres.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import replication.postgres.model.User;
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private LocalSessionFactoryBean m_sessionFactory;

	public void setSessionFactory(LocalSessionFactoryBean sessionFactory) {
		this.m_sessionFactory = sessionFactory;
	}

	@Override
	public void save(User u) {
		Session session = this.m_sessionFactory.getObject().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.persist(u);
		tx.commit();
		session.close();
	}

	@Override
	public void saveBatch(List<User> users) {
		Session session = this.m_sessionFactory.getObject().getCurrentSession();
		Transaction tx = session.beginTransaction();
		for (User user : users) {
			session.persist(user);
		}
		tx.commit();
		session.close();
	}

	@Override
	@Transactional
	public List<User> getUsers() {
		Session s = this.m_sessionFactory.getObject().getCurrentSession();
		Transaction tx = s.beginTransaction();
		List<User> users = (List<User>) s.createCriteria(User.class).list();
		tx.commit();

		return users;
	}

	@Override
	public List<User> getPagedUsers(int start, int count) {
		Session s = this.m_sessionFactory.getObject().getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query query = s.createQuery("From User");
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<User> users = (List<User>) query.list();
		tx.commit();

		return users;
	}
	
	@Override
	public Number countUsers() {
		Session session = m_sessionFactory.getObject().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Number result = (Number) session.createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
		tx.commit();
		return result;
	}

}
