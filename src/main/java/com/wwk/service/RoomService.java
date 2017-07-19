package com.wwk.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.wwk.model.Joiner;
import com.wwk.model.Player;
import com.wwk.model.Room;
import com.wwk.utils.TimeUtil;

import message.RoomMsgProto.GameMsgPro;
import message.RoomMsgProto.GameMsgType;
import message.RoomMsgProto.GameRole;
import message.RoomMsgProto.JoinerInfoMsg;
import message.RoomMsgProto.JoinerMsg;
import message.RoomMsgProto.JoinerStatus;
import message.RoomMsgProto.RoomStatus;
import message.core.PBMessageProto.PBMessage;
import message.core.PushMsgProto.PushType;

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
	public Room createRoom(Player player) throws Exception {
		Room room = new Room(player.getId());
		RoomManager.getInstance().createRoom(room);
		comeInRoom(player, room);
		return room;
	}

	public Room randomRoom(Player player) throws Exception{
		Room room = RoomManager.getInstance().randomRoom();
		if(room != null && comeInRoom(player, room)){
			return room;	
		}else{
			return null;
		}
	}
	
	/**
	 * 加入房间
	 * 
	 * @param player
	 * @param room
	 * @return
	 * @throws Exception 
	 */
	public boolean comeInRoom(Player player, Room room) throws Exception {
		Joiner joiner = new Joiner(player);
		int index = getSpaceIndex(room);
		if (index > 0) {
			room.getJoiners().put(index, joiner);
			
			JoinerInfoMsg.Builder joinerInfoBuilder = JoinerInfoMsg.newBuilder();
			joinerInfoBuilder.setLocation(index);
			
			JoinerMsg.Builder builder = JoinerMsg.newBuilder();
			builder.setPlayerMsg(player.genProto());
			joinerInfoBuilder.setJoiner(builder.build());
			
			PBMessage.Builder pbm = PBMessage.newBuilder();
			pbm.setPushCode(PushType.PUSH_JOIN_GAME_VALUE);
			pbm.setData(joinerInfoBuilder.build().toByteString());
			broadcast(GameRole.ALLROLE_VALUE, pbm.build(), room);
			
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
	 * @throws Exception 
	 */
	public boolean leaveRoom(String playerId, String roomId) throws Exception {
		Room room = RoomManager.getInstance().getRoomById(roomId);
		Iterator<Entry<Integer, Joiner>> iterator = room.getJoiners().entrySet().iterator();
		int index = 0;
		while (iterator.hasNext()) {
			Entry<Integer, Joiner> entry = iterator.next();
			index = entry.getKey();
			Joiner joiner = entry.getValue();
			if (joiner.getPlayer().getId().equals(playerId)) {
				iterator.remove();
				JoinerInfoMsg.Builder joinerInfoBuilder = JoinerInfoMsg.newBuilder();
				joinerInfoBuilder.setLocation(index);
				
				JoinerMsg.Builder builder = JoinerMsg.newBuilder();
				Player player= PlayerService.getInstance().getPlayer(playerId);
				builder.setPlayerMsg(player.genProto());
				joinerInfoBuilder.setJoiner(builder.build());
				
				PBMessage.Builder pbm = PBMessage.newBuilder();
				pbm.setPushCode(PushType.PUSH_QUIT_GAME_VALUE);
				pbm.setData(joinerInfoBuilder.build().toByteString());
				broadcast(GameRole.ALLROLE_VALUE, pbm.build(), room);
				if (joiner.getPlayer().getId().equals(room.getId())) {// 房主离开，更换房主
					if(room.getJoiners().size() != 0){
						while (true) {
							index++;
							index = index > 10 ? 1 : index;
							if (room.getJoiners().containsKey(index)) {
								break;
							}
						}
						room.setId(room.getJoiners().get(index).getPlayer().getId());
						
						
						PBMessage.Builder pbmr = PBMessage.newBuilder();
						pbmr.setPushCode(PushType.PUSH_ROOM_MSG_VALUE);
						pbmr.setData(room.genProto().build().toByteString());
						broadcast(GameRole.ALLROLE_VALUE, pbmr.build(), room);
					}else{
						RoomManager.getInstance().removeRoom(roomId);
						return true;
					}
				}
				break;
			}
		}
		return true;
	}
	
	/**
	 * 向房间发送消息
	 * @param playerId
	 * @param roomId
	 * @param content
	 * @return
	 */
	public boolean sendTxtMsgToRoom(String playerId,String  roomId, String content) {
		
		Room room = RoomManager.getInstance().getRoomById(roomId);
		
		int location = getPlayerLocation(playerId, room);
		
		GameMsgPro.Builder builder = GameMsgPro.newBuilder();
		builder.setTime(TimeUtil.now());
		builder.setPMsg(content);
		builder.setSenderId(location);
		builder.setType(GameMsgType.PLAYER_VALUE);
		
		PBMessage.Builder pbm = PBMessage.newBuilder();
		pbm.setPushCode(PushType.PUSH_SEND_TXT_MSG_VALUE);
		pbm.setData(builder.build().toByteString());
		
		broadcast(GameRole.ALLROLE_VALUE, pbm.build(), room);
		
		return true;
	}

	/**
	 * 参与者准备
	 * 
	 * @param index
	 * @param room
	 * @return
	 */
	public boolean joinerReady(int index, Room room) {
		room.getJoiners().get(index).setStatus(JoinerStatus.READY_VALUE);
		return true;
	}

	/**
	 * 踢出房间
	 * 
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
	 * 
	 * @param room
	 * @return
	 */
	public boolean start(Room room) {
		room.setStatus(RoomStatus.RUNNING_VALUE);
		return true;
	}

	/**
	 * 房间广播
	 * 
	 * @param role
	 * @param msg
	 * @param room
	 */
	public void broadcast(int role, PBMessage msg, Room room) {
		for (Joiner joiner : room.getJoiners().values()) {
			if (role == GameRole.ALLROLE_VALUE || joiner.getRole() == role) {
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
	
	/**
	 * 获取玩家位置
	 * @param palyerId
	 * @param room
	 * @return
	 */
	private int getPlayerLocation(String playerId ,Room room) {
		for (Map.Entry<Integer,Joiner> joiner : room.getJoiners().entrySet()) {
			if(joiner.getValue().getPlayer().getId().equals(playerId)){
				return joiner.getKey(); 
			}
		}
		return 0; 
	}
}
