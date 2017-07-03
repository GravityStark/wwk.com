package com.wwk.config;
/**
 * 配置数据
 * @author LiuWei
 *
 */ 
public class ServerConfig {
	public static String HOST = "192.168.0.222";
	public static int PORT = 28000;
	
	private ServerConfig() {
	}

	enum Singleton {
		INSTANCE;
		ServerConfig instance = null;

		Singleton() {
			instance = new ServerConfig();
		}

		ServerConfig getInstance() {
			return instance;
		}
	}

	public static ServerConfig getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

}
