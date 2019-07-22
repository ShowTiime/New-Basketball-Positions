package br.home.project.java.newbbpos;

public class Player {
	
	private String name;
	private double points;
	private double rebounds;
	private double assists;
	private double blocks;
	private double steals;
	
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

	public double getRebounds() {
		return rebounds;
	}

	public void setRebounds(double rebounds) {
		this.rebounds = rebounds;
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

	@Override
	public String toString() {
		return "Player [name=" + name + ", points=" + points + ", rebounds=" + rebounds + ", assists=" + assists
				+ ", blocks=" + blocks + ", steals=" + steals + "]";
	}
	
}
