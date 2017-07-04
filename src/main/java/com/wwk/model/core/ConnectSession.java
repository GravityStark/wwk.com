package com.wwk.model.core;

import com.google.protobuf.Message;

import io.netty.channel.Channel;
import lombok.Data;
import message.core.PBMessageProto.PBMessage;

/**
 * 请求对象
 * 
 * @author Administrator
 * 
 */
@Data
public class ConnectSession {
	private PBMessage request = null;// 请求数据
	private Channel channel = null;// 连接
	private PBMessage.Builder response = null; // 响应数据

	public ConnectSession(PBMessage request, Channel channel) {
		this.request = request;
		this.channel = channel;

		// 新建一个响应数据,并设置头部信息
		this.response = PBMessage.newBuilder();
		this.response.setCode(request.getCode());
		this.response.setPlayerId(request.getPlayerId());
	}

	/**
	 * 获取操作码
	 * 
	 * @return
	 */
	public int getActionCode() {
		return this.request.getCode();
	}

	/**
	 * 获取玩家id
	 * 
	 * @return
	 */
	public String getPlayerId() {
		return this.request.getPlayerId();
	}

	/**
	 * 设置服务器端返回数据
	 * 
	 * @param msg
	 */
	public void setResponseMsg(Message msg) {
		this.response.setData(msg.toByteString());
	}

}
