package com.wwk.action;

import com.wwk.action.core.BaseAction;
import com.wwk.model.Account;
import com.wwk.model.Player;
import com.wwk.model.core.ConnectSession;
import com.wwk.service.AccountService;
import com.wwk.service.OnlineService;
import com.wwk.service.PlayerService;

import message.AccountProto.LOGIN_TYPE;
import message.AccountProto.LoginReq;
import message.AccountProto.LoginRsp;
import message.AccountProto.LOGIN_RESULT_TYPE;

public class LoginAction extends BaseAction {

	@Override
	public void done(ConnectSession session) throws Exception {
		LoginReq req = LoginReq.parseFrom(session.getRequest().getData());
		LoginRsp.Builder builder = LoginRsp.newBuilder();
		if (req.getType() == LOGIN_TYPE.LOGIN_VALUE) {
			Account account = AccountService.getInstance().loadAccount(req.getAccountName());
			if(account == null){
				builder.setResult(LOGIN_RESULT_TYPE.NO_ACCOUNT_VALUE);
			}else if(!account.getPwd().equals(req.getPassword())){
				builder.setResult(LOGIN_RESULT_TYPE.PWD_ERROR_VALUE);
			}else{
				builder.setResult(LOGIN_RESULT_TYPE.SUCCESS_VALUE);
				Player player = PlayerService.getInstance().getPlayer(account.getPlayerId());
				if(player == null){
					player = PlayerService.getInstance().createPlayer();
					account.setPlayerId(player.getId());
					AccountService.getInstance().save(account);
				}	
				builder.setPlayer(player.genProto());
				player.setChannel(session.getChannel());
			}
		} else if(req.getType() == LOGIN_TYPE.CREATE_VALUE){
			Account account = AccountService.getInstance().loadAccount(req.getAccountName());
			if(account != null){
				builder.setResult(LOGIN_RESULT_TYPE.CREATE_NAME_EXIST_VALUE);
			}else{
				Player player = AccountService.getInstance().createAccount(req.getAccountName(), req.getPassword());
				builder.setPlayer(player.genProto());
				builder.setResult(LOGIN_RESULT_TYPE.SUCCESS_VALUE);
				player.setChannel(session.getChannel());
				OnlineService.getInstance().login(player);
			}
		}
		session.setResponseMsg(builder.build());
	}

}
