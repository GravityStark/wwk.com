package com.wwk.net;

import com.wwk.action.core.Action;
import com.wwk.action.core.ActionSet;
import com.wwk.model.core.ConnectSession;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import message.core.PBMessageProto.PBMessage;

public class SocketServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		PBMessage pbMessage = (PBMessage) msg;
		int actionCode = pbMessage.getCode();
		Action action = ActionSet.getInstance().getAction(actionCode);
		ConnectSession session = new ConnectSession(pbMessage, ctx.channel());
		action.run(session);
	}

}
