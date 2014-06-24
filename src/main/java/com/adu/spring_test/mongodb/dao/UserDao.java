package com.adu.spring_test.mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.adu.spring_test.mongodb.model.User;

public interface UserDao {
	/**
	 * 插入一个用户.spring直接将User的各个字段按照其类型保存到mongodb中
	 * 
	 * @param user
	 */
	public void insert(User user);

	/**
	 * 使用默认的collectionName插入.默认的collectionName是"user"
	 * 
	 * @param user
	 */
	public void insertWithDefaultCollectionName(User user);

	/**
	 * 插入多个user
	 * 
	 * @param users
	 */
	public void insertMulti(List<User> users);

	/**
	 * 按照条件查找用户
	 * 
	 * @param query
	 * @return
	 */
	public List<User> find(Query query);

	/**
	 * 查询所有user
	 * 
	 * @return
	 */
	public List<User> findAll();

}
