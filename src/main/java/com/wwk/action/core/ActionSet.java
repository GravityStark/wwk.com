package com.wwk.action.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.wwk.config.ServerConfig;

public class ActionSet {
	private final String ACTION_FILE = "action.properties";

	private Map<Integer, Action> actionMap = new HashMap<Integer, Action>();

	private ActionSet() {
	}

	enum Singleton {
		INSTANCE;
		ActionSet instance = null;

		Singleton() {
			instance = new ActionSet();
		}

		ActionSet getInstance() {
			return instance;
		}
	}

	public static ActionSet getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	/**
	 * 初始化所有的操作
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		Properties properties = new Properties();
		try (InputStream inputStream = ServerConfig.class.getClassLoader().getResourceAsStream(ACTION_FILE)) {
			properties.load(inputStream);
			for (Object key : properties.keySet()) {
				String keyCode = String.valueOf(key);
				if (Integer.parseInt(keyCode) >= 1000 && Integer.parseInt(keyCode) < 1100) {
					continue;
				}
				String clazz = properties.getProperty(keyCode);
				if (clazz == null) {
					throw new Exception("action load exception: key=" + keyCode + "");
				}
				try {
					Action action = (Action) Class.forName(clazz).newInstance();
					actionMap.put(Integer.valueOf(keyCode), action);
				} catch (Exception e) {
					throw new Exception("init class error:key=" + keyCode + ", class=" + clazz);
				}
			}
		}
	}

	public Action getAction(int keyCode) {
		return actionMap.get(keyCode);
	}
}
