package com.adu.spring_test.mongodb.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adu.spring_test.mongodb.model.User;
import com.mongodb.WriteResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MongoTemplateTest {
	@Autowired
	private MongoTemplate mongoTemplate;
	private static final String COLLECTION_NAME = "user";

	private Log logger = LogFactory.getLog(this.getClass());

	@Test
	public void insert() {
		User user = new User(0, "adu", 27);
		mongoTemplate.insert(user, COLLECTION_NAME);
	}

	@Test
	public void save() {
		User user = new User(0, "adu", 27);
		mongoTemplate.save(user, COLLECTION_NAME);
	}

	@Test
	public void insertMulti() {
		List<User> users = new ArrayList<User>();
		Random random = new Random();

		for (int i = 1; i <= 10; i++) {
			users.add(new User(i, "adu" + i, random.nextInt(50) + 1));
		}

		mongoTemplate.insert(users, COLLECTION_NAME);
	}

	@Test
	public void find() {
		Criteria criteria = Criteria.where("id").gt(1).and("age").gt(20);
		Query query = new Query(criteria).skip(2).limit(3);
		List<User> users = mongoTemplate.find(query, User.class,
				COLLECTION_NAME);
		for (User user : users) {
			logger.debug(user);
		}
	}

	@Test
	public void findOne() {
		Criteria criteria = Criteria.where("id").is(1);
		Query query = new Query(criteria);

		User res = mongoTemplate.findOne(query, User.class, COLLECTION_NAME);
		logger.debug("res=" + res);
	}

	@Test
	public void findById() {
		int id = 1;
		User res = mongoTemplate.findById(id, User.class, COLLECTION_NAME);
		logger.debug("res=" + res);
	}

	@Test
	public void findAndModify() {
		Criteria criteria = Criteria.where("id").gte(1);
		Query query = new Query(criteria);

		Update update = new Update().inc("age", 100);

		FindAndModifyOptions options = new FindAndModifyOptions().upsert(true)
				.returnNew(true);

		User res = mongoTemplate.findAndModify(query, update, options,
				User.class, COLLECTION_NAME);
		logger.debug("res=" + res);
	}

	@Test
	public void findAndRemove() {
		Criteria criteria = Criteria.where("id").is(10);
		Query query = new Query(criteria);

		// 删除并返回之前的数据
		User res = mongoTemplate.findAndRemove(query, User.class,
				COLLECTION_NAME);
		logger.debug("res=" + res);
	}

	@Test
	public void findAll() {
		List<User> users = mongoTemplate.findAll(User.class, COLLECTION_NAME);
		for (User user : users) {
			logger.debug(user);
		}
	}

	@Test
	public void update() {
		Criteria criteria = Criteria.where("id").is(1);
		Query query = new Query(criteria);

		Update update = new Update().inc("age", 100);

		WriteResult result = mongoTemplate.updateFirst(query, update,
				User.class, COLLECTION_NAME);
		logger.debug("result=" + result.getN());
	}

	@Test
	public void updateMulti() {
		Criteria criteria = Criteria.where("id").gte(1);
		Query query = new Query(criteria);

		Update update = new Update().inc("age", -100);

		WriteResult result = mongoTemplate.updateMulti(query, update,
				User.class, COLLECTION_NAME);
		logger.debug("result=" + result.getN());
	}

	@Test
	public void remove() {
		Criteria criteria = Criteria.where("id").is(11);
		Query query = new Query(criteria);

		// 删除所有满足条件的doc
		mongoTemplate.remove(query, User.class, COLLECTION_NAME);
	}

	@Test
	public void upsert() {
		Criteria criteria = Criteria.where("id").is(11);
		Query query = new Query(criteria);

		Update update = new Update().inc("age", 100);

		// 存在则更新，不存在则插入新doc并应用更新
		WriteResult result = mongoTemplate.upsert(query, update, User.class,
				COLLECTION_NAME);
		logger.debug("result=" + result.getN());
	}

	@Test
	public void count() {
		Query query = new Query();
		long count = mongoTemplate.count(query, User.class);
		logger.debug("count=" + count);
	}

	@Test
	public void collectionExists() {
		boolean res = mongoTemplate.collectionExists(COLLECTION_NAME);
		logger.debug("res=" + res);
	}

	@Test
	public void collectionExists1() {
		boolean res = mongoTemplate.collectionExists(User.class);
		logger.debug("res=" + res);
	}

}
