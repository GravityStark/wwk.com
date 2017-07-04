package com.wwk.action;

import com.wwk.action.core.BaseAction;
import com.wwk.model.Player;
import com.wwk.model.Room;
import com.wwk.model.core.ConnectSession;
import com.wwk.service.PlayerService;
import com.wwk.service.RoomService;

import message.RoomMsgProto.CreatRoomRsp;

public class CreatRoomAction extends BaseAction{

	@Override
	protected void done(ConnectSession session) throws Exception {
		Player player = PlayerService.getInstance().getPlayer(session.getPlayerId());
		CreatRoomRsp.Builder builder = CreatRoomRsp.newBuilder();
		Room room = RoomService.getInstance().createRoom(player);
		builder.setRoom(room.genProto());
	}
}
