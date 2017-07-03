package com.wwk.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.wwk.model.core.Ids;

public class IdDao extends BaseDao<Ids> {

	public int findAndModify(String table) {
		final Datastore ds = getDatastore();
		return findModify(table, ds);
	}

	/**
	 * 通过表名获取该表数据项的最大索引,设置索引为当前索引+1
	 * 
	 * 玩家起始id按KINGDOM配置
	 * 
	 * @param table
	 * @param ds
	 * @return 0表示有异常,最小值为1
	 */
	public int findModify(String table, Datastore ds) {
		Query<Ids> query = ds.createQuery(Ids.class).field("table").equal(table);
		if (query.asList().isEmpty()) {

			Ids i = new Ids();
			i.setMaxId(0);
			i.setTable(table);
			ds.save(i);
		}
		UpdateOperations<Ids> updateOps = ds.createUpdateOperations(Ids.class);
		updateOps.inc("maxId", 1);

		Ids ids = ds.findAndModify(query, updateOps);
		return ids.getMaxId();
	}

}
