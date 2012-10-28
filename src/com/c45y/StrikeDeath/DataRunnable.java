package com.c45y.StrikeDeath;

import org.bukkit.entity.Player;

import com.c45y.StrikeDeath.database.DeathStat;

class DataRunnable extends Thread
{
	StrikeDeath plugin = null;
	Player player = null;
	Player killer = null;
	
	DataRunnable(StrikeDeath p, Player pl, Player kl) {
		this.plugin = p;
		this.player = pl;
		this.killer = kl;
	}
	public void run ()
	{
		DeathStat stat = new DeathStat();
		stat.setPlayerName(this.player.getName());
		stat.setKillerName(this.killer.getName());
		stat.setKillerItem(this.killer.getItemInHand().getType().toString());
        String location = String.format("%s,%f,%f,%f", player.getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        stat.setDeathLocation(location);
        boolean is_armor_kill = plugin.HandleDeath.isArmorKill(player);
        stat.setArmorKill(is_armor_kill);
        stat.setTimestamp(System.currentTimeMillis());
        this.plugin.deathStatTable.save(stat);
	}
}