package com.adu.spring_test.mongodb.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.adu.spring_test.mongodb.dao.UserDao;
import com.adu.spring_test.mongodb.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String collectionName = "user";

	public void insert(User user) {
		mongoTemplate.insert(user, collectionName);
	}

	public void insertWithDefaultCollectionName(User user) {
		mongoTemplate.insert(user);

	}

	public void insertMulti(List<User> users) {
		mongoTemplate.insert(users, collectionName);

	}

	public List<User> find(Query query) {
		List<User> res = this.mongoTemplate.find(query, User.class,
				collectionName);
		return res;
	}

	public List<User> findAll() {
		List<User> res = this.mongoTemplate.findAll(User.class, collectionName);
		return res;
	}

}
