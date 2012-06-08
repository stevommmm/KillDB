package com.c45y.StrikeDeath;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.avaje.ebean.Query;

import com.c45y.StrikeDeath.PlayerStat;
@Entity()
@Table(name = "death_stats")
public class PlayerStatTable {

	StrikeDeath plugin;
	
	public PlayerStatTable(StrikeDeath plugin) {
		this.plugin = plugin;
	}
	
	public PlayerStat getPlayerStat(String player) {
		PlayerStat retVal = null;
		Query<PlayerStat> query = plugin.getDatabase().find(PlayerStat.class).where().eq("playerName", player).query();
		if (query != null) {
			return query.findUnique();
		}
		retVal = new PlayerStat();
		retVal.setPlayerName(player);
		return retVal;
	}

	public void save(PlayerStat stat) {
		plugin.getDatabase().save(stat);
	}
}
