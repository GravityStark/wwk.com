package com.wwk.action;

import com.wwk.action.core.BaseAction;
import com.wwk.model.core.ConnectSession;
import com.wwk.service.RoomService;

import message.RoomMsgProto.QuitRoomReq;
import message.RoomMsgProto.QuitRoomRsp;
/**
 * 离开房间
 * @author LiuWei
 *
 */
public class QuitRoomAction extends BaseAction{

	@Override
	protected void done(ConnectSession session) throws Exception {
		
		QuitRoomReq req = QuitRoomReq.parseFrom(session.getRequest().getData());
		
		boolean success = RoomService.getInstance().leaveRoom(session.getPlayerId(), req.getId());
		QuitRoomRsp.Builder builder = QuitRoomRsp.newBuilder();
		builder.setSuccess(success);
		session.setResponseMsg(builder.build());
		
	}
}
