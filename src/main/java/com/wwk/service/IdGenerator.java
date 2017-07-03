package com.wwk.service;

import com.wwk.dao.IdDao;

/**
 * ID生成器
 * 
 * @author Administrator
 * 
 */
public class IdGenerator {

	private static IdDao idDao = new IdDao();

	/**
	 * 根据表名生成id
	 * 
	 * @param table
	 * @return
	 */
	public static int generateId(String table) {
		synchronized (table) {
			int newMaxId = idDao.findAndModify(table);
			return newMaxId;
		}
	}
}
