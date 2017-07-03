package com.wwk.model.core;

import org.mongodb.morphia.annotations.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 玩家信息
 */
@Entity(noClassnameStored = true)
@EqualsAndHashCode(callSuper = false)
@Data
public class Ids extends BaseModel {
	private String table; // 表名
	private int maxId; // 最大id
}