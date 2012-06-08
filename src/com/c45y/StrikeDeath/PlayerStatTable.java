package com.c45y.StrikeDeath;

import com.avaje.ebean.Query;

import com.c45y.StrikeDeath.PlayerStat;

public class PlayerStatTable {

	StrikeDeath plugin;

	public PlayerStatTable(StrikeDeath plugin) {
		this.plugin = plugin;
	}

	public PlayerStat getPlayerStat(String player) {
		PlayerStat retVal = null;
		Query<PlayerStat> query = plugin.getDatabase().find(PlayerStat.class).where().eq("playerName", player).query();
		if (query != null) {
			retVal = query.findUnique();
		}
		if (retVal == null) {
			retVal = new PlayerStat();
			retVal.setPlayerName(player);
		}
		return retVal;
	}

	public void save(PlayerStat stat) {
		try {
			plugin.getDatabase().save(stat);
		} catch (Exception e) {System.out.println(e.getMessage());}
	}
}
