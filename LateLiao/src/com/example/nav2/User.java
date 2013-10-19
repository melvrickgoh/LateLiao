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
	private String name;
	private String imageLocation;
	private int level;
	private int currentPoints;
	private int totalMeetings;
	private int meetingsLate;
	private double totalLateTime;
	private Location currentLocation;
	
	public User(Parcel p){
		Bundle values = p.readBundle();
		username = values.getString("username");
		name = values.getString("name");
		imageLocation = values.getString("imageLocation");
		level = values.getInt("level");
		currentPoints = values.getInt("currentPoints");
		totalMeetings = values.getInt("totalMeetings");
		meetingsLate = values.getInt("meetingsLate");
		totalLateTime = values.getDouble("totalLateTime");
		currentLocation = new Location(values.getString("locationName"),values.getDouble("locationLatitude"),values.getDouble("locationLongitude"));
	}
	
	public User(String username, String name, String imageLocation, int level, int currentPoints, int totalMeetings, int meetingsLate, double totalLateTime,
			Location currentLocation) {
		super();
		this.username = username;
		this.name = name;
		this.imageLocation = imageLocation;
		this.level = level;
		this.currentPoints = currentPoints;
		this.totalMeetings = totalMeetings;
		this.meetingsLate = meetingsLate;
		this.totalLateTime = totalLateTime;
		this.currentLocation = currentLocation;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalMeetings() {
		return totalMeetings;
	}

	public void setTotalMeetings(int totalMeetings) {
		this.totalMeetings = totalMeetings;
	}

	public int getMeetingsLate() {
		return meetingsLate;
	}

	public void setMeetingsLate(int meetingsLate) {
		this.meetingsLate = meetingsLate;
	}

	public double getTotalLateTime() {
		return totalLateTime;
	}

	public void setTotalLateTime(double totalLateTime) {
		this.totalLateTime = totalLateTime;
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
		values.putString("name",name);
		values.putString("imageLocation", imageLocation);
		values.putInt("level", level);
		values.putInt("currentPoints", currentPoints);
		values.putInt("totalMeetings", totalMeetings);
		values.putInt("meetingsLate", meetingsLate);
		values.putDouble("totalLateTime", totalLateTime);
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