package com.wwk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wwk.model.core.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import message.RoomMsgProto.RoomStatus;

/**
 * 房间
 * @author LiuWei
 *
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Room extends BaseModel{
	private String id;//房主id
	private int status;//房间状态     1：空闲 2：游戏中
	private Map<Integer,Joiner> joiners = new ConcurrentHashMap<>();//房间成员 <位置，玩家信息>
    private List<GameMsg> msgList = new ArrayList<>();//房间信息记录
	
    
    public Room(String id) {
		super();
		this.id = id;
		status = RoomStatus.IDLE_VALUE;
	}
    
    
}
