package com.wwk.action;

import com.wwk.action.core.BaseAction;
import com.wwk.model.Player;
import com.wwk.model.core.ConnectSession;
import com.wwk.service.OnlineService;
import com.wwk.service.PlayerService;

import message.PlayerProto.LoginReq;
import message.PlayerProto.LoginRsp;

public class LoginAction extends BaseAction {

	@Override
	public void done(ConnectSession session) throws Exception {
		LoginReq req = LoginReq.parseFrom(session.getRequest().getData());
		LoginRsp.Builder builder = LoginRsp.newBuilder();
		Player player = null;
		if (req.hasId()) {
			player = PlayerService.getInstance().loadPlayer(req.getId());
		} else {
			player = PlayerService.getInstance().createPlayer();
		}
		if (player != null) {
			builder.setPlayer(player.genProto());
			builder.setSuccess(true);
			OnlineService.getInstance().login(player);
		} else {
			builder.setSuccess(false);
		}
		session.setResponseMsg(builder.build());
	}

}
