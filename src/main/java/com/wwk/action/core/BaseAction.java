package com.wwk.action.core;

import com.wwk.model.core.ConnectSession;

import io.netty.channel.Channel;
import message.core.ClientActionProto.ClientAction;
import message.core.PBMessageProto.PBMessage;

public abstract class BaseAction implements Action {
	@Override
	public void run(ConnectSession session) throws Exception {
		final int actionCode = session.getActionCode();
		if (actionCode != ClientAction.ACTION_LOGIN_VALUE) {
			 doInit(session);
		}
		done(session);
		send2Client(session);
		session = null;
	}

	/**
	 * 初始化操作(判断是否登录、更新操作时间等) //所有非登录操作action
	 * 
	 * @param session
	 * @throws Exception
	 */
	private void doInit(ConnectSession session) throws Exception {
	}


	/**
	 * 返回数据给客户端
	 * 
	 * @param session
	 */
	private void send2Client(ConnectSession session) {
		PBMessage response = session.getResponse().build();
		Channel channel = session.getChannel();
		channel.writeAndFlush(response);
	}

	/**
	 * 子类实现(每个操作的具体逻辑)
	 * 
	 * @param session
	 */
	protected abstract void done(ConnectSession session) throws Exception;
}
