package com.wwk.service;

import java.util.HashMap;
import java.util.Map;

import com.wwk.model.Room;

public class RoomManager{

	private RoomManager() {
	}

	//枚举实现单例
	enum Singleton {
		INSTANCE;
		private RoomManager instance;

		Singleton() {
			instance = new RoomManager();
		}

		RoomManager getInstance() {
			return instance;
		}
	}

	/**
	 * 获取实例
	 * @return
	 */
	public static RoomManager getInstance() {
		return Singleton.INSTANCE.getInstance();
	}
	//房间集合
	private Map<String, Room> roomMap = new HashMap<>();
}
