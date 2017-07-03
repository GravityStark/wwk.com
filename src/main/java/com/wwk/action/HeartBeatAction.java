package com.wwk.action;

import com.wwk.action.core.BaseAction;
import com.wwk.model.core.ConnectSession;

public class HeartBeatAction extends BaseAction{

	@Override
	public void done(ConnectSession session) {
		System.out.println("心跳");
	}


}
