package com.wwk.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wwk.model.Player;

public class OnlineService {
	private OnlineService() {
	}
	//枚举实现单例
	enum Singleton {
		INSTANCE;
		private OnlineService instance;

		Singleton() {
			instance = new OnlineService();
		}

		OnlineService getInstance() {
			return instance;
		}
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static OnlineService getInstance() {
		return Singleton.INSTANCE.getInstance();
	}
	
	private Map<String, Player> onlinePlayerMap = new ConcurrentHashMap<>();
	
	public void login(Player player){
		onlinePlayerMap.put(player.getId(), player);
	}
	
	public void logout(Player player){
		onlinePlayerMap.remove(player.getId());
	}
	
	public Player getPlayer(String id){
		return onlinePlayerMap.get(id);
	}
}
