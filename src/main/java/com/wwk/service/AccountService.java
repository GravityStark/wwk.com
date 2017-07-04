package com.wwk.service;

import com.wwk.dao.AccountDao;
import com.wwk.model.Account;
import com.wwk.model.Player;

public class AccountService {
	private  AccountDao dao = new AccountDao();
	
	private AccountService() {
	}

	//枚举实现单例
	enum Singleton {
		INSTANCE;
		private AccountService instance;

		Singleton() {
			instance = new AccountService();
		}

		AccountService getInstance() {
			return instance;
		}
	}

	/**
	 * 获取实例
	 * @return
	 */
	public static AccountService getInstance() {
		return Singleton.INSTANCE.getInstance();
	}
	
	public  void save(Account account) {
		dao.save(account);
	}
	
	public Account loadAccount(String id){
		return dao.queryAccount(id);
	}
	
	public  Player createAccount(String name,String pwd){
		Account account = new Account();
		account.setActName(name);
		account.setPwd(pwd);
		
		Player player = PlayerService.getInstance().createPlayer();
		account.setPlayerId(player.getId());
		
		dao.save(account);
		
		return player;
	}
}
