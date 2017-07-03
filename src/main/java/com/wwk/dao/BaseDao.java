package com.wwk.dao;

import java.util.Arrays;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.wwk.config.MongoConfig;
import com.wwk.model.core.BaseModel;

public abstract class BaseDao<T extends BaseModel> {
	private static MongoClient mongoClient = null;
	private static Morphia morphia = null;
	private static MongoConfig config;

	/**
	 * 初始化Mongo实例
	 * 
	 * @throws Exception
	 */
	public static void initMongo(MongoConfig cfg) throws Exception {
		config = cfg;
		createMongo(config.host, config.port, config.dbName);
		morphia = new Morphia();
	}

	/**
	 * 创建Mongo实例
	 * 
	 * @param host
	 * @param port
	 * @param dbName
	 * @return
	 */
	private static void createMongo(String host, int port, String dbName) {
		MongoCredential credential = MongoCredential.createCredential(config.username, config.dbName,
				config.password.toCharArray());
	    mongoClient = new MongoClient(new ServerAddress(config.host, config.port),
				Arrays.asList(credential));
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public Datastore getDatastore() {
		Datastore ds = morphia.createDatastore(mongoClient, config.dbName);
		ds.ensureIndexes();
		return ds;
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	public void save(T model) {
		final Datastore ds = getDatastore();
		try {
			ds.save(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 * 
	 * @param object
	 */
	public void delete(T model) {
		final Datastore ds = getDatastore();
		try {
			ds.delete(model);
		} catch (Exception e) {
		}
	}

}
