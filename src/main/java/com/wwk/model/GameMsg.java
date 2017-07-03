package com.wwk.model;

import com.wwk.model.core.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
	}
}
