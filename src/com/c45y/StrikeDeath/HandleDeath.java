package com.c45y.StrikeDeath;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class HandleDeath extends EntityListener{
	public StrikeDeath plugin;

	public HandleDeath(StrikeDeath instance) {
		plugin = instance;
	}

	public void onEntityDeath(EntityDeathEvent event){
		if (event.getEntity() instanceof Player) {
			event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
		}
	}
}
