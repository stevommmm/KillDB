package com.c45y.StrikeDeath.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "death_stats")
public class DeathStat {
	
	@Id
	private int id;
	
	@NotNull
	private String playerName;
	private String killerName;
	private String killerItem;
	private String deathLocation;
	
	@NotNull
	private boolean armorKill;
	private long timestamp;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public void setKillerName(String killerName) {
		this.killerName = killerName;
	}
	
	public String getKillerName() {
		return this.killerName;
	}
	
	public void setKillerItem(String killerItem) {
		this.killerItem = killerItem;
	}
	
	public String getKillerItem() {
		return this.killerItem;
	}
	
	public void setDeathLocation(String deathLocation) {
		this.deathLocation = deathLocation;
	}
	
	public String getDeathLocation() {
		return this.deathLocation;
	}
	
	public void setArmorKill(boolean armorKill) {
		this.armorKill = armorKill;
	}
	
	public boolean getArmorKill() {
		return this.armorKill;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
