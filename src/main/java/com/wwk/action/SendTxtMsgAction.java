package com.wwk.action;

import com.wwk.action.core.BaseAction;
import com.wwk.model.core.ConnectSession;
import com.wwk.service.RoomService;

import message.RoomMsgProto.QuitRoomRsp;
import message.RoomMsgProto.SendTxtMsgReq;
/**
 * 离开房间
 * @author LiuWei
 *
 */
public class SendTxtMsgAction extends BaseAction{

	@Override
	protected void done(ConnectSession session) throws Exception {
		SendTxtMsgReq req = SendTxtMsgReq.parseFrom(session.getRequest().getData());
		boolean success = RoomService.getInstance().sendTxtMsgToRoom(session.getPlayerId(),session.getPlayerId(), req.getId());
		QuitRoomRsp.Builder builder = QuitRoomRsp.newBuilder();
		builder.setSuccess(success);
		session.setResponseMsg(builder.build());
		
	}
}
