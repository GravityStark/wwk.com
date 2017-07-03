package com.wwk.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import com.wwk.model.core.BaseModel;
import com.wwk.utils.PropUtil;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import message.PlayerProto.PlayerMsg;

/**
 * 玩家数据
 * 
 * @author LiuWei
 *
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@Data
public class Player extends BaseModel {
	@Id
	private String id; // 玩家唯一ID
	private String name;// 玩家名称
	private int imgId;// 玩家头像ID
	@Transient
	private Channel channel;
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
