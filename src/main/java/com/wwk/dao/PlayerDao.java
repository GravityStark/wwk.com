package com.wwk.dao;

import org.mongodb.morphia.Datastore;

import com.wwk.model.Player;

public class PlayerDao extends BaseDao<Player> {
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public Player queryPlayer(String id) {

		Datastore ds = getDatastore();

		Player player = ds.get(Player.class, id);

		return player;
	}
}
