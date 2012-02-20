package com.c45y.StrikeDeath;

import java.util.logging.Logger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class StrikeDeath extends JavaPlugin
{
	private final HandleDeath HandleDeath = new HandleDeath(this);
	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		//pm.registerEvent(Event.Type.ENTITY_DEATH, this.HandleDeath, Event.Priority.Normal, this);
		pm.registerEvents(HandleDeath, this);
		this.log.info("StrikeDeath enabled.");
	}

	public void onDisable() {
		this.log.info("StrikeDeath disabled.");
	}
}