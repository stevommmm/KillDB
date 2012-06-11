package com.c45y.StrikeDeath;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.Query;
import com.c45y.StrikeDeath.PlayerStat;

public class StrikeDeath extends JavaPlugin
{
	private final HandleDeath HandleDeath = new HandleDeath(this);
	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(HandleDeath, this);
		setupDatabase();
		this.log.info("StrikeDeath enabled.");
	}

	public void onDisable() {
		this.log.info("StrikeDeath disabled.");
	}

	public boolean setupDatabase() {
		try {
			getDatabase().find(PlayerStat.class).findRowCount();
		} catch (PersistenceException ex) {
			getLogger().log(Level.INFO, "First run, initializing database.");
			installDDL();
			return true;
		}

		return false;
	}

	@Override
	public ArrayList<Class<?>> getDatabaseClasses() {
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		list.add(PlayerStat.class);
		return list;
	}
	
	public PlayerStat getPlayerStat(String player) {
		PlayerStat retVal = null;
		Query<PlayerStat> query = getDatabase().find(PlayerStat.class).where().eq("playerName", player).query();
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
			getDatabase().save(stat);
		} catch (Exception e) {System.out.println(e.getMessage() + "\n" + e.toString());}
		//22:24:37 [INFO] get kills on [com.c45y.StrikeDeath.PlayerStat] type[com.c45y.StrikeDeath.PlayerStat] threw error.
		//java.lang.RuntimeException: get kills on [com.c45y.StrikeDeath.PlayerStat] type[com.c45y.StrikeDeath.PlayerStat] threw error.
	}
}