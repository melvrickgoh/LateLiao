package com.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

public class Event {
	/*DATANAME follows attribute names EXACTLY
	 * eventName
	 * startTime
	 * endTime
	 * attendees
	 * location
	 */
	private String eventName;
	private Timestamp startTime;
	private Timestamp endTime;
	private ArrayList<User> attendees;
	private Location location;
	
	public Event(String eventName, Timestamp startTime, Timestamp endTime,
			ArrayList<User> attendees, Location location) {
		super();
		this.eventName = eventName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.attendees = attendees;
		this.location = location;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public ArrayList<User> getAttendees() {
		return attendees;
	}
	public void setAttendees(ArrayList<User> attendees) {
		this.attendees = attendees;
	}
	public void addAttendee(User user){
		this.attendees.add(user);
	}
	public void removeAttendee(User user){
		String wantedUsername = user.getUsername();
		Iterator<User> attendeeIter = attendees.iterator();
		while (attendeeIter.hasNext()){
			User check = attendeeIter.next();
			if (check.getUsername().equalsIgnoreCase(wantedUsername)){
				attendeeIter.remove();
			}
		}
	}
	
}