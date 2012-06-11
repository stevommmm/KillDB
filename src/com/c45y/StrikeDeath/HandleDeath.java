package com.c45y.StrikeDeath;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class HandleDeath implements Listener
{
	public StrikeDeath plugin;

	public HandleDeath(StrikeDeath instance) {
		this.plugin = instance;
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = (Player)event.getEntity();
		if ((player.getKiller() instanceof Player)) {
			event.setDeathMessage(""); //We want do do our own messages
			if (isArmorKill(player)) { //If they gave up spawn camping
				Location loc = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() + 5.0D, player.getLocation().getZ(), 360.0F, 0.0F);
				event.getEntity().getWorld().strikeLightningEffect(loc);
				deathMessage(player.getKiller().getName(), " took down ", player.getName(), " with a ", prettyItemName(player.getKiller().getItemInHand()));
			} else {
				deathMessage(player.getKiller().getName(), " killed ", player.getName(), " with a ", prettyItemName(player.getKiller().getItemInHand()));
			}
			incrementDeath(player.getName());
			//Log death
			
			//Log kill
			//			{
			//				try {
			//					PlayerStat stat = plugin.stattab.getPlayerStat(player.getKiller().getName());
			//					stat.incrementKills();
			//					plugin.stattab.save(stat);
			//					System.out.println("kill saved");
			//				} catch (Exception e) { System.out.println(":("); }
			//			}
		}
	}
	
	public void incrementDeath(String player) {
		try {
			String query = "SELECT deaths FROM player_stats WHERE playerName = '" + player + "' ;";
			ResultSet result = 	this.plugin.sqlite.query(query);
			if (result != null && result.next()) {
				int deaths = result.getInt("deaths");
				query = "UPDATE player_stats SET deaths = '" + (deaths + 1) + "' WHERE playerName = '" + player + "';";
				result.close();
				this.plugin.sqlite.query(query);
				System.out.println("Updated");
			} else {
				query = "INSERT INTO player_stats (playerName,kills,deaths) VALUES ('" + player + "','" + 0 + "', '" + 1 + "');";
				this.plugin.sqlite.query(query);
			}
		} catch (SQLException e) {
			System.out.println("----------------------------------");
			e.printStackTrace();
		}
		System.out.println("death saved");
	}

	public boolean isArmorKill(Player dead_guy) {
		if (dead_guy.getInventory().getChestplate() != null) {
			if (dead_guy.getInventory().getChestplate().getType().getMaxDurability() > 150) { //was wearing iron or dia armor
				return true;
			}
		}
		return false;
	}

	public String prettyItemName(ItemStack i) {
		String item = i.getType().toString().replace('_', ' ' ).toLowerCase();
		if(item.equals("air")) {
			item = "fist";
		}
		return item;
	}

	public void deathMessage(String killer,String action,String dead_guy,String joiner,String item) {
		plugin.getServer().broadcastMessage(ChatColor.RED + killer + action + dead_guy + joiner + item);
	}
}