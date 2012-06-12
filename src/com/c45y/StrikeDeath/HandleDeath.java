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
import org.bukkit.event.player.PlayerJoinEvent;
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
			incrementKills(player.getKiller().getName());
			incrementDeaths(player.getName());
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event){
		String player = event.getPlayer().getName();
		if(!userExists(player)) {
			createDbUser(player);
			System.out.println("User created.");
		}

	}

	public void createDbUser(String player) {
		String query = "INSERT INTO player_stats (playerName,kills,deaths) VALUES ('" + player + "','0', '0');";
		ResultSet result = this.plugin.sqlite.query(query);
		try {
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created db player: " + player);
	}

	public boolean userExists(String player) {
		String query = "SELECT count(*) AS count FROM player_stats WHERE playerName = '" + player + "' ;";
		ResultSet result = 	this.plugin.sqlite.query(query);
		boolean returnval = false;
		try {
			if (result != null && result.next()) {
				if(result.getInt("count") == 1) {
					returnval = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnval;
	}

	public int getKills(String player) {
		String query = "SELECT kills FROM player_stats WHERE playerName = '" + player + "' ;";
		int kills = 0;
		ResultSet result = this.plugin.sqlite.query(query);
		try {
			if (result != null && result.next()) {
				kills = result.getInt("kills");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(player + " has " + kills + " kills");
		return kills;
	}

	public void incrementKills(String player) {
		incrementKills(player,getKills(player));
	}

	public void incrementKills(String player,int kills){
		String query = "UPDATE player_stats SET kills = '" + (kills + 1) + "' WHERE playerName = '" + player + "';";
		try {
			this.plugin.sqlite.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("kills set to " + kills+ " for " + player);
	}
	
	public int getDeaths(String player) {
		String query = "SELECT deaths FROM player_stats WHERE playerName = '" + player + "' ;";
		int deaths = 0;
		ResultSet result = this.plugin.sqlite.query(query);
		try {
			if (result != null && result.next()) {
				deaths = result.getInt("deaths");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(player + " has " + deaths + " deaths");
		return deaths;
	}

	public void incrementDeaths(String player) {
		incrementDeaths(player,getDeaths(player));
	}

	public void incrementDeaths(String player,int deaths){
		String query = "UPDATE player_stats SET deaths = '" + (deaths + 1) + "' WHERE playerName = '" + player + "';";
		try {
			this.plugin.sqlite.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("deaths set to " + deaths + " for " + player);
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