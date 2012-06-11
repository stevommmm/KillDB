package com.c45y.StrikeDeath;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import lib.PatPeter.SQLibrary.*;

public class StrikeDeath extends JavaPlugin
{
	private final HandleDeath HandleDeath = new HandleDeath(this);
	Logger log = Logger.getLogger("Minecraft");
	public SQLite sqlite;

	public void onEnable() {
		this.sqlite = new SQLite(this.log, "[StrikeDeath]", "StrikeDeath", this.getDataFolder().getPath());
		this.sqlite.open();
		if (!this.sqlite.checkTable("player_stats")) {
			this.log.info("Creating table player_stats");
			String query = "CREATE TABLE IF NOT EXISTS player_stats (playerName VARCHAR(25) PRIMARY KEY NOT NULL, kills INT, deaths INT);";
			this.sqlite.createTable(query); // Use SQLite.createTable(query) to create tables
			}
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(HandleDeath, this);
		this.log.info("StrikeDeath enabled.");
	}

	public void onDisable() {
		this.log.info("StrikeDeath disabled.");
	}
}