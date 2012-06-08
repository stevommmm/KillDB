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
			retVal.setPlayerKills(0);
			retVal.setPlayerDeaths(0);
		}
		return retVal;
	}

	public void save(PlayerStat stat) {
		System.out.println(stat.getId() + "\n\t" + stat.getPlayerName() + "\n\t" + stat.getPlayerKills() + "\n\t" + stat.getPlayerDeaths());	
		try {
			plugin.getDatabase().save(stat);
		} catch (Exception e) {System.out.println(e.getMessage() + "\n" + e.toString());}
		//22:24:37 [INFO] get kills on [com.c45y.StrikeDeath.PlayerStat] type[com.c45y.StrikeDeath.PlayerStat] threw error.
		//java.lang.RuntimeException: get kills on [com.c45y.StrikeDeath.PlayerStat] type[com.c45y.StrikeDeath.PlayerStat] threw error.
	}
}
