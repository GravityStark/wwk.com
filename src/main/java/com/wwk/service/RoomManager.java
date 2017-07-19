package com.wwk.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wwk.model.Room;
import com.wwk.utils.Utils;

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
	private Map<String, Room> roomMap = new ConcurrentHashMap<>();
	/**
	 * 新建房间
	 * @param room
	 */
	public void createRoom(Room room){
		roomMap.put(room.getId(), room);
	}
	
	/**
	 * 删除房间
	 * @param roomId
	 */
	public void removeRoom(String  roomId){
		roomMap.remove(roomId);
	}
	
	/**
	 * 查询房间
	 * @param roomId
	 * @return
	 */
	public Room getRoomById(String roomId){
		return roomMap.get(roomId);
	}
	
	/**
	 * 查询房间
	 * @param roomId
	 * @return
	 */
	public Room randomRoom(){
		int max = roomMap.size();
		int index = Utils.nextInt(0, max);
		int start = 0;
		for (Map.Entry<String,Room> entry : roomMap.entrySet()) {
			if(start == index){
				return entry.getValue();
			}
			start ++;
		}
		return null;
	}
}
