package replication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import replication.mongo.UserRepository;
import replication.postgres.dao.UserDao;
import replication.postgres.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class ForkJoinTest {

	@Autowired
	private UserDao m_userDao;

	@Autowired
	private UserRepository m_userRepository;

	@Test
	public void testCorrectInsertion() {
		List<User> users = m_userRepository.findUsersIds();
		int[] prev = { 0 };
		users.stream().mapToInt(u -> u.getId()).sorted().forEach(e -> {
			if (e - 1 != prev[0]) {
				System.out.println("jumped user " + (e - 1));
			}
			prev[0] = e;
		});
		System.out.println(users.size());
	}

	@Test
	public void testDuplicateNames() {
		List<User> users = (List<User>) m_userRepository.findAll();
		users.stream().filter(u -> Collections.frequency(users, u) > 1).collect(Collectors.toSet())
				.forEach(System.out::println);
		;

		System.out.println(users.size());
	}
}
