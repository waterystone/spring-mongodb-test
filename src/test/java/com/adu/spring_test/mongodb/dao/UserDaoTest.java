package com.adu.spring_test.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adu.spring_test.mongodb.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest {
	@Autowired
	private UserDao userDao;

	private Log logger = LogFactory.getLog(this.getClass());

	@Test
	public void insert() {
		User user = new User(0, "adu", 27);
		userDao.insert(user);
	}

	@Test
	public void insertWithDefaultCollectionName() {
		User user = new User(0, "adu", 27);
		userDao.insertWithDefaultCollectionName(user);
	}

	@Test
	public void insertMulti() {
		List<User> users = new ArrayList<User>();
		Random random = new Random();
		// mongodb默认使用id做唯一索引，所以id要不同，不然可能插不进去
		for (int i = 1; i <= 50; i++) {
			User user = new User(i, "adu" + i, random.nextInt(50) + 1);
			users.add(user);
		}
		userDao.insertMulti(users);
	}

	@Test
	public void find() {
		Query query = new Query().skip(10).limit(5);
		List<User> users = userDao.find(query);
		for (User user : users) {
			logger.debug(user);
		}
	}

	@Test
	public void findAll() {
		List<User> users = userDao.findAll();
		for (User user : users) {
			logger.debug(user);
		}
	}

}
