package com.wwk.net;

import java.net.InetSocketAddress;

import com.wwk.config.ServerConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import message.core.PBMessageProto.PBMessage;

public class NetManager {
	private NetManager() {
	}

	enum Singleton {
		INSTANCE;
		NetManager instance = null;

		Singleton() {
			instance = new NetManager();
		}

		NetManager getInstance() {
			return instance;
		}
	}

	public static NetManager getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	public void init() throws Exception {

		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
				.childHandler(new SocketServerInitializer());
		serverBootstrap.bind(new InetSocketAddress(ServerConfig.HOST, ServerConfig.PORT)).sync().channel()
				.closeFuture();
		System.out.println("HOST:"+ServerConfig.HOST+"===="+"PORT:"+ServerConfig.PORT);
	}

	/**
	 * 发送消息给客户端
	 * 
	 * @param channel
	 * @param msg
	 */
	public void sendMsg(Channel channel, PBMessage msg) {
		channel.writeAndFlush(msg);
	}
}
