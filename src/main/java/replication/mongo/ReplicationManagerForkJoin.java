package replication.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import org.springframework.beans.factory.annotation.Autowired;

import replication.postgres.dao.UserDao;
import replication.postgres.model.User;

public class ReplicationManagerForkJoin extends AbstractReplicationManager {
	@Autowired
	private UserDao m_userDao;

	private int m_pageThreshold;

	private List<Integer> m_pages = new ArrayList<>();

	@Autowired
	private UserRepository m_userRepository;

	@SuppressWarnings("serial")
	private class Worker extends RecursiveAction {

		private int m_start;
		private int m_count;

		public Worker(int page, int count) {
			this.m_count = count;
			this.m_start = page;
			// System.out.println(this.toString());
		}

		protected void computeDirectly() {
			m_pages.add(m_start);
			List<User> users = m_userDao.getPagedUsers(m_start, m_count);
			System.out.println("before:" + m_userRepository.count() + "("+(m_userRepository.count()+8009)+")");
			m_userRepository.save(users);
			System.out.println("after:" + m_userRepository.count());
		}

		@Override
		protected void compute() {
			if (m_count < m_pageThreshold) {
				computeDirectly();
			} else {
				int count = ((int) Math.floor(m_count / 2)) + 1;
				Worker w1 = new Worker(m_start, count);
				Worker w2 = new Worker(m_start + count + 1, count);
				invokeAll(w1, w2);
			}

		}

		@Override
		public String toString() {
			return String.format("Created worker page: %d count: %d", m_start, m_count);
		}
	}

	@Override
	public void insertPagedUsers(int pageSize) {
		m_pageThreshold = pageSize;
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new Worker(0, m_userDao.countUsers().intValue()));

		m_pages.stream().sorted().forEach(i -> System.out.println(i + "; " + (i + 8009)));
	}

}
