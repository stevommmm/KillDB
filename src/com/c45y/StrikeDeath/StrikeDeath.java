package com.c45y.StrikeDeath;

import java.util.logging.Logger;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class StrikeDeath extends JavaPlugin {
	private final HandleDeath HandleDeath = new HandleDeath(this);
	Logger log = Logger.getLogger("Minecraft");

	public void onEnable(){ 
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_DEATH, HandleDeath, Event.Priority.Normal, this);
		log.info("StrikeDeath enabled.");
	}

	public void onDisable(){ 
		log.info("StrikeDeath disabled.");
	}
}
