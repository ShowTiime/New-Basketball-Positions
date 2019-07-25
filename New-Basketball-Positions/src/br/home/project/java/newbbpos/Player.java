package br.home.project.java.newbbpos;

public class Player {
	
	private String name;			// [1]
	private int position;			// [2]
	private int gamesStarted;		// [6]
	private double minutesPlayed;	// [7]
	private double FGPctg;			// [10]
	private double threeptFGPctg;	// [13]
	private double points;			// [29]
	private double offRebounds;		// [21]
	private double defRebounds;		// [22]
	private double assists;			// [24]
	private double blocks;			// [26]
	private double steals;			// [25]
	
	public Player() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public double getAssists() {
		return assists;
	}

	public void setAssists(double assists) {
		this.assists = assists;
	}
	
	public double getBlocks() {
		return blocks;
	}
	
	public void setBlocks(double blocks) {
		this.blocks = blocks;
	}

	public double getSteals() {
		return steals;
	}

	public void setSteals(double steals) {
		this.steals = steals;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getGamesStarted() {
		return gamesStarted;
	}

	public void setGamesStarted(int gamesStarted) {
		this.gamesStarted = gamesStarted;
	}

	public double getMinutesPlayed() {
		return minutesPlayed;
	}

	public void setMinutesPlayed(double minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}

	public double getFGPctg() {
		return FGPctg;
	}

	public void setFGPctg(double fGPctg) {
		FGPctg = fGPctg;
	}

	public double getThreeptFGPctg() {
		return threeptFGPctg;
	}

	public void setThreeptFGPctg(double threeptFGPctg) {
		this.threeptFGPctg = threeptFGPctg;
	}

	public double getOffRebounds() {
		return offRebounds;
	}

	public void setOffRebounds(double offRebounds) {
		this.offRebounds = offRebounds;
	}

	public double getDefRebounds() {
		return defRebounds;
	}

	public void setDefRebounds(double defRebounds) {
		this.defRebounds = defRebounds;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", position=" + position + ", gamesStarted=" + gamesStarted + ", minutesPlayed="
				+ minutesPlayed + ", FGPctg=" + FGPctg + ", threeptFGPctg=" + threeptFGPctg + ", points=" + points
				+ ", offRebounds=" + offRebounds + ", defRebounds=" + defRebounds + ", assists=" + assists + ", blocks="
				+ blocks + ", steals=" + steals + "]";
	}
	
}
