package com.c45y.StrikeDeath;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.c45y.StrikeDeath.PlayerStat;

public class StrikeDeath extends JavaPlugin
{
	private final HandleDeath HandleDeath = new HandleDeath(this);
	Logger log = Logger.getLogger("Minecraft");
	PlayerStatTable stattab;

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(HandleDeath, this);
		setupDatabase();
		stattab = new PlayerStatTable(this);
		this.log.info("StrikeDeath enabled.");
	}

	public void onDisable() {
		this.log.info("StrikeDeath disabled.");
	}

	public boolean setupDatabase() {
		try {
			getDatabase().find(PlayerStatTable.class).findRowCount();
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
}