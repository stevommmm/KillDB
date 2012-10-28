package com.c45y.StrikeDeath;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.c45y.StrikeDeath.database.DeathStat;

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
			
			DeathStat stat = new DeathStat();
			stat.setPlayerName(player.getName());
			stat.setKillerName(player.getKiller().getName());
			stat.setKillerItem(player.getKiller().getItemInHand().getType().toString());
            String location = String.format("%s,%f,%f,%f", player.getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            stat.setDeathLocation(location);
            boolean is_armor_kill = isArmorKill(player);
            stat.setArmorKill(is_armor_kill);
            stat.setTimestamp(System.currentTimeMillis());

            plugin.deathStatTable.save(stat);
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		int deaths = plugin.deathStatTable.getNumDeaths(ChatColor.stripColor(event.getPlayer().getName()));
		int kills = plugin.deathStatTable.getNumKills(ChatColor.stripColor(event.getPlayer().getName()));
		event.getPlayer().sendMessage("Deaths: " + deaths + " Kills: " + kills);
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