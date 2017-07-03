package com.wwk.action.core;

import com.wwk.model.core.ConnectSession;

public interface Action {
	void run(ConnectSession session) throws Exception;
}
