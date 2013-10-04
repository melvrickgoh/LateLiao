package com.example.nav2;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
	/*
	 * locationName
	 * latitude
	 * longitude
	 */
	private String locationName;
	private double latitude;
	private double longitude;
	
	public Location(Parcel source){
		Bundle values = source.readBundle();
		locationName = values.getString("locationName");
		latitude = values.getDouble("locationLatitude");
		longitude = values.getDouble("locationLongitude");
	}
	
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

	@Override
	public int describeContents() {
		// Useless method to be implemented
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle values = new Bundle();
		values.putString("locationName", locationName);
		values.putDouble("locationLatitude", latitude);
		values.putDouble("locationLongitude", longitude);
		
		out.writeBundle(values);
	}
	
	public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {

		@Override
		public Location createFromParcel(Parcel source) {
			return new Location(source);
		}

		@Override
		public Location[] newArray(int size) {
			return new Location[size];
		}
	};
}