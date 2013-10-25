package com.example.nav2;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
	private String eventName;
	private int eventDate;
	private int eventMonth;
	private int eventYear;
	private String eventTime;
	private ArrayList<String> eventAttendees;
	private Location eventLocation;
	
	public Event(Parcel p){
		Bundle values = p.readBundle();
		eventName = values.getString("eventName");
		eventDate = values.getInt("eventDate");
		eventMonth = values.getInt("eventMonth");
		eventYear = values.getInt("eventYear");
		eventTime = values.getString("eventTime");
		eventAttendees = values.getStringArrayList("eventAttendees");
		eventLocation = new Location(values.getString("locationName"),values.getDouble("locationLatitude"),values.getDouble("locationLongitude"));
	}
	
	public Event(String eventName, int eventDate, int eventMonth, int eventYear, String eventTime, ArrayList<String> eventAttendees,
			Location eventLocation) {
		super();
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventMonth = eventMonth;
		this.eventYear = eventYear;
		this.eventTime = eventTime;
		this.eventAttendees = eventAttendees;
		this.eventLocation = eventLocation;
	}
	
	public ArrayList<String> getEventAttendees() {
		return eventAttendees;
	}
	public void setEventAttendees(ArrayList<String> eventAttendees) {
		this.eventAttendees = eventAttendees;
	}
	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public int getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(int eventDate) {
		this.eventDate = eventDate;
	}
	
	public int getEventMonth() {
		return eventMonth;
	}
	
	public void setEventMonth(int eventMonth) {
		this.eventMonth = eventMonth;
	}
	public int getEventYear() {
		return eventYear;
	}
	
	public void setEventYear(int eventYear) {
		this.eventYear = eventYear;
	}
	
	public String getEventTime() {
		return eventTime;
	}
	
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	public Location getEventLocation() {
		return eventLocation;
	}
	
	public void setEventLocation(Location eventLocation) {
		this.eventLocation = eventLocation;
	}
	
	public String getEventLocationName(){
		return eventLocation.getLocationName();
	}

	@Override
	public int describeContents() {
		// Useless method to be implemented
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle values = new Bundle();
		values.putString("eventName",eventName);
		values.putInt("eventDate", eventDate);
		values.putInt("eventMonth", eventMonth);
		values.putInt("eventYear", eventYear);
		values.putString("eventTime", eventTime);
		values.putStringArrayList("eventAttendees", eventAttendees);
		values.putString("locationName", eventLocation.getLocationName());
		values.putDouble("locationLatitude", eventLocation.getLatitude());
		values.putDouble("locationLongitude", eventLocation.getLongitude());
		
		out.writeBundle(values);
	}
	
	public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){
		public Event createFromParcel(Parcel in){
			return new Event(in);
		}

		@Override
		public Event[] newArray(int size) {
			return new Event[size];
		}
		
	};
	
	
}
