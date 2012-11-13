package com.c45y.KillDB.database;

import com.avaje.ebean.Query;
import com.c45y.KillDB.KillDB;

public class DeathStatTable {

	KillDB plugin;
	
	public DeathStatTable(KillDB plugin) {
		this.plugin = plugin;
	}
	
	public int getNumKills(String username) {
		int retVal = 0;
		Query<DeathStat> query = plugin.getDatabase().find(DeathStat.class).where().ieq("killerName", username).query();
		
		if (query != null) {
			retVal = query.findRowCount();
		}
		
		return retVal;
	}
	
	public int getNumDeaths(String username) {
		int retVal = 0;
		Query<DeathStat> query = plugin.getDatabase().find(DeathStat.class).where().ieq("playerName", username).query();
		
		if (query != null) {
			retVal = query.findRowCount();
		}
		
		return retVal;
	}
	
	public DeathStat getRequest(int id) {
		DeathStat retVal = null;
		
		Query<DeathStat> query = plugin.getDatabase().find(DeathStat.class).where().eq("id", id).query();
		
		if (query != null) {
			retVal = query.findUnique();
		}
		
		return retVal;
	}
	
	public void save(DeathStat deathstat) {
		plugin.getDatabase().save(deathstat);
	}
	
}
