package com.wwk.action;

import com.wwk.action.core.BaseAction;
import com.wwk.model.Player;
import com.wwk.model.Room;
import com.wwk.model.core.ConnectSession;
import com.wwk.service.PlayerService;
import com.wwk.service.RoomService;

import message.RoomMsgProto.JoinRoomRsp;
/**
 * 加入房间
 * @author LiuWei
 *
 */
public class JoinRoomAction extends BaseAction{

	@Override
	protected void done(ConnectSession session) throws Exception {
		
		Player player = PlayerService.getInstance().getPlayer(session.getPlayerId());
		Room room = RoomService.getInstance().randomRoom(player);
		JoinRoomRsp.Builder builder = JoinRoomRsp.newBuilder();
		if( room != null){
			builder.setRoom(room.genProto());
			builder.setSuccess(true);
		}else{
			builder.setSuccess(false);
		}
		session.setResponseMsg(builder.build());
		
	}
}
