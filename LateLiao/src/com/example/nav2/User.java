package com.example.nav2;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	/*ATTRIBUTE NAMES TO BE EXACTLY THE SAME
	 * username
	 * level
	 * currentPoints
	 * currentLocation {object}
	 */
	private String username;
	private String imageLocation;
	private int level;
	private int currentPoints;
	private Location currentLocation;
	
	public User(Parcel p){
		Bundle values = p.readBundle();
		username = values.getString("username");
		imageLocation = values.getString("imageLocation");
		level = values.getInt("level");
		currentPoints = values.getInt("currentPoints");
		currentLocation = new Location(values.getString("locationName"),values.getDouble("locationLatitude"),values.getDouble("locationLongitude"));
	}
	
	public User(String username, String imageLocation, int level, int currentPoints,
			Location currentLocation) {
		super();
		this.username = username;
		this.imageLocation = imageLocation;
		this.level = level;
		this.currentPoints = currentPoints;
		this.currentLocation = currentLocation;
	}
	
	public User(String username, int level, int currentPoints,
			Location currentLocation) {
		super();
		this.username = username;
		this.level = level;
		this.currentPoints = currentPoints;
		this.currentLocation = currentLocation;
	}
	public String getImageLocation() {
		return imageLocation;
	}
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
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
	@Override
	public int describeContents() {
		// Useless method to be implemented
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle values = new Bundle();
		values.putString("username",username);
		values.putString("imageLocation", imageLocation);
		values.putInt("level", level);
		values.putInt("currentPoints", currentPoints);
		values.putString("locationName", currentLocation.getLocationName());
		values.putDouble("locationLatitude", currentLocation.getLatitude());
		values.putDouble("locationLogitude", currentLocation.getLongitude());
		
		out.writeBundle(values);
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}