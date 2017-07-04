package com.wwk.model;

import com.wwk.model.core.BaseModel;
import com.wwk.utils.PropUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import message.RoomMsgProto.GameMsgPro;
import message.RoomMsgProto.SysMsgPro;

@EqualsAndHashCode(callSuper = false)
@Data
public class GameMsg  extends BaseModel{
	private long time;//时间
	private int type;//0:系统;1：玩家   GameMsgType
	private String pMsg;//玩家发送信息
	private SysMsg sysMsg ;//系统信息
	
	
	/**
	 * 系统信息
	 * @author LiuWei
	 *
	 */
	@EqualsAndHashCode(callSuper = false)
	@Data
	class SysMsg{
		private int type;//枚举类型 SysMsgType
		private int target1;//
		private int target2;//
		
		/**
		 * 生成协议
		 * @return
		 * @throws Exception
		 */
		public SysMsgPro.Builder genProto() throws Exception {
			SysMsgPro.Builder builder = SysMsgPro.newBuilder();
			PropUtil.copyProperties(builder, this, SysMsgPro.Builder.getDescriptor());
			return builder;
		}
	}
	
	/**
	 * 生成协议
	 * @return
	 * @throws Exception
	 */
	public GameMsgPro.Builder genProto() throws Exception {
		GameMsgPro.Builder builder = GameMsgPro.newBuilder();
		PropUtil.copyProperties(builder, this, GameMsgPro.Builder.getDescriptor());
		builder.setSys(sysMsg.genProto());
		return builder;
	}
}
