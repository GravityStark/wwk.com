package com.wwk.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.wwk.model.core.BaseModel;
import com.wwk.utils.PropUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import message.PlayerProto.PlayerMsg;

/**
 * 账号数据
 * 
 * @author LiuWei
 *
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@Data
public class Account extends BaseModel {
	@Id
	private String actName; // 账号名
	private String pwd;// 玩家名称
	private String playerId;// 玩家ID
	/**
	 * 生成协议
	 * @return
	 * @throws Exception
	 */
	public PlayerMsg.Builder genProto() throws Exception {
		PlayerMsg.Builder builder = PlayerMsg.newBuilder();
		PropUtil.copyProperties(builder, this, PlayerMsg.Builder.getDescriptor());
		return builder;
	}

}
