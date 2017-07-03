package com.wwk.service;

import com.wwk.dao.PlayerDao;
import com.wwk.model.Player;

public class PlayerService {
	private PlayerDao dao = new PlayerDao();
	
	private PlayerService() {
	}

	//枚举实现单例
	enum Singleton {
		INSTANCE;
		private PlayerService instance;

		Singleton() {
			instance = new PlayerService();
		}

		PlayerService getInstance() {
			return instance;
		}
	}

	/**
	 * 获取实例
	 * @return
	 */
	public static PlayerService getInstance() {
		return Singleton.INSTANCE.getInstance();
	}
	
	
	
	public Player loadPlayer(String id){
		return dao.queryPlayer(id);
	}
	
	public  Player getPlayer(String id){
		Player player = OnlineService.getInstance().getPlayer(id);
		if(player == null){
			player = loadPlayer(id);
			if(player != null){
				OnlineService.getInstance().login(player);
			}
		}
		return player;
	}
	
	public  Player createPlayer(){
		Player player = new Player();
		int generateId = IdGenerator.generateId(Player.class.getSimpleName());
		player.setId(Integer.toString(generateId));
		player.setImgId(1);
		player.setName("player"+player.getId());
		
		return player;
	}
}
