package com.c45y.StrikeDeath;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "death_stats")
public class PlayerStat {
	
	@Id
	private int id;
	
	@Column
	private String playerName = null;
	private int kills = 0;
	private int deaths = 0;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		System.out.println("a");
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public void setPlayerKills(int killnum) {
		this.kills = killnum;
	}
	
	public int getPlayerKills() {
		return this.kills;
	}
	
	public void setPlayerDeaths(int deathnum) {
		this.deaths = deathnum;
	}
	
	public int getPlayerDeaths() {
		return this.deaths;
	}
	
	public void incrementKills() {
		this.kills++;
	}
	
	public void incrementDeaths() {
		this.deaths++;
	}
}
