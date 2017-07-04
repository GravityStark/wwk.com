package com.wwk.dao;

import org.mongodb.morphia.Datastore;

import com.wwk.model.Account;

public class AccountDao extends BaseDao<Account> {
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public Account queryAccount(String id) {

		Datastore ds = getDatastore();

		Account account = ds.get(Account.class, id);

		return account;
	}
}
