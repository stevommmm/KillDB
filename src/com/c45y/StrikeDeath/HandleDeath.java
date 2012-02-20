package com.c45y.StrikeDeath;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class HandleDeath implements Listener
{
	public StrikeDeath plugin;

	public HandleDeath(StrikeDeath instance)
	{
		this.plugin = instance;
	}

	public void onEntityDeath(EntityDeathEvent event) {
		if ((event instanceof PlayerDeathEvent)) {
			Player player = (Player)event.getEntity();
			if (((player.getKiller() instanceof Player)) && (
					(player.getInventory().getChestplate().getType().getMaxDurability() > 150) || 
					(player.getInventory().getLeggings().getType().getMaxDurability() > 115))) {
				Location loc = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() + 5.0D, player.getLocation().getZ(), 360.0F, 0.0F);
				event.getEntity().getWorld().strikeLightningEffect(loc);
			}
		}
	}
}