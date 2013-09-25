package com.entity;

public class User {
	/*ATTRIBUTE NAMES TO BE EXACTLY THE SAME
	 * username
	 * level
	 * currentPoints
	 * currentLocation {object}
	 */
	private String username;
	private int level;
	private int currentPoints;
	private Location currentLocation;
	
	public User(String username, int level, int currentPoints,
			Location currentLocation) {
		super();
		this.username = username;
		this.level = level;
		this.currentPoints = currentPoints;
		this.currentLocation = currentLocation;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getCurrentPoints() {
		return currentPoints;
	}
	public void setCurrentPoints(int currentPoints) {
		this.currentPoints = currentPoints;
	}
	public Location getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	
}