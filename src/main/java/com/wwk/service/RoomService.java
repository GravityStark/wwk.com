package com.wwk.service;

import com.wwk.model.Joiner;
import com.wwk.model.Player;
import com.wwk.model.Room;

import message.RoomMsgProto.GameRole;
import message.RoomMsgProto.JoinerStatus;
import message.RoomMsgProto.RoomStatus;
import message.core.PBMessageProto.PBMessage;

public class RoomService {
	private RoomService() {
	}

	// 枚举实现单例
	enum Singleton {
		INSTANCE;
		private RoomService instance;

		Singleton() {
			instance = new RoomService();
		}

		RoomService getInstance() {
			return instance;
		}
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static RoomService getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	// 创建房间
	public Room createRoom(Player player) {
		Room room = new Room(player.getId());

		return room;
	}

	/**
	 * 加入房间
	 * 
	 * @param player
	 * @param room
	 * @return
	 */
	public boolean comeInRoom(Player player, Room room) {
		Joiner joiner = new Joiner(player);
		int index = getSpaceIndex(room);
		if (index > 0) {
			room.getJoiners().put(index, joiner);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 离开房间
	 * 
	 * @param player
	 * @param room
	 * @return
	 */
	public boolean leaveRoom(int index, Room room) {
		Joiner joiner = room.getJoiners().remove(index);
		if (joiner.getPlayer().getId().equals(room.getId())) {// 房主离开，更换房主
			while(true){
				index++;
				index = index > 10 ? 1 : index;
				if(room.getJoiners().containsKey(index)){
					break;
				}
			}
			room.setId(room.getJoiners().get(index).getPlayer().getId());
		}
		return true;
	}

	/**
	 * 参与者准备
	 * 
	 * @param index
	 * @param room
	 * @return
	 */
	public boolean JoinerReady(int index, Room room) {
		room.getJoiners().get(index).setStatus(JoinerStatus.READY_VALUE);
		return true;
	}
	/**
	 * 踢出房间
	 * @param index
	 * @param room
	 * @return
	 */
	public boolean kickJoiner(int index, Room room) {
		room.getJoiners().remove(index);
		return true;
	}
	/**
	 * 开始游戏
	 * @param room
	 * @return
	 */
	public boolean start(Room room) {
		room.setStatus(RoomStatus.RUNNING_VALUE);
		return true;
	}
	/**
	 *  房间广播
	 * @param role
	 * @param msg
	 * @param room
	 */
	public void broadcast(int role,PBMessage msg,Room room){
		for ( Joiner joiner : room.getJoiners().values()) {
			if(role == GameRole.ALLROLE_VALUE || joiner.getRole() == role){
				joiner.getPlayer().getChannel().writeAndFlush(msg);
			}
		}
	}
	/**
	 * 获取房间空闲位置
	 * 
	 * @param room
	 * @return
	 */
	private int getSpaceIndex(Room room) {
		int index = 1;
		while (true) {
			if (index >= 10) {
				return -1;
			}
			if (room.getJoiners().containsKey(index)) {
				index++;
				continue;
			} else {
				return index;
			}
		}
	}

}
