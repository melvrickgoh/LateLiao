package com.entity;

public class Location {
	/*
	 * locationName
	 * latitude
	 * longitude
	 */
	private String locationName;
	private double latitude;
	private double longitude;
		
	public Location(String locationName, double latitude, double longitude) {
		super();
		this.locationName = locationName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}