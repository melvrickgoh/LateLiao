package com.amazon;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.entity.Location;
import com.entity.User;
import com.entity.Event;

public class EventsDAO {
	private SimpleDB sdb;
	private LocationDAO lDAO;
	private UserDAO uDAO;
	
	public EventsDAO(LocationDAO lDAO, UserDAO uDAO){
		sdb = new SimpleDB();
		this.lDAO = lDAO;
		this.uDAO = uDAO;
	}
	
	public boolean checkEventDomainExists(){
		@SuppressWarnings("static-access")
		List<String> domainNames = sdb.getDomainNames();
		if (domainNames.contains("Events")){
			return true;
		}
		return false;
	}
	
	public boolean checkEventExists(String eventName){
		@SuppressWarnings("static-access")
		String[] eventNames = sdb.getItemNamesForDomain("Events");
		if (Arrays.asList(eventNames).contains(eventName)){
			return true;
		}
		return false;
	}
	
	public Event getEvent(String eventName){
		@SuppressWarnings("static-access")
		HashMap<String,String> eventAttributes = sdb.getAttributesForItem("Events", eventName);
		long startTime = Long.parseLong(eventAttributes.get("startTime"));
		long endTime = Long.parseLong(eventAttributes.get("endTime"));
		String attendeeNames = eventAttributes.get("attendees");
		String locationName = eventAttributes.get("location");
		
		ArrayList<User> attendees = getUsers(attendeeNames);
		Location locale = lDAO.getLocation(locationName);
		
		return new Event(eventName,getTimestamp(startTime),getTimestamp(endTime),attendees,locale);
	}
	
	@SuppressWarnings("static-access")
	public void addEvent(Event event){
		String eventName = event.getEventName();
		sdb.createItem("Events", eventName);
		sdb.createAttributeForItem("Events", eventName, "startTime", "" + convertTimestamp(event.getStartTime()));
		sdb.createAttributeForItem("Events", eventName, "endTime", "" + convertTimestamp(event.getEndTime()));
		sdb.createAttributeForItem("Events", eventName, "attendees", linkUsers(event.getAttendees()));
		sdb.createAttributeForItem("Events", eventName, "location",event.getLocation().getLocationName());
	}
	
	@SuppressWarnings("static-access")
	public void updateEvent(Event event){
		String eventName = event.getEventName();
		sdb.updateAttributesForItem("Events", eventName, mapEventAttributes(event));
	}
	
	private HashMap<String,String> mapEventAttributes (Event event){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("eventName", event.getEventName());
		map.put("startTime", "" + convertTimestamp(event.getStartTime()));
		map.put("endTime", "" + convertTimestamp(event.getEndTime()));
		map.put("attendees", "" + linkUsers(event.getAttendees()));
		map.put("location", event.getLocation().getLocationName());
		return map;
	}
	
	private ArrayList<User> getUsers (String usernames){
		ArrayList<User> users = new ArrayList<User>();
		String[] userArray = usernames.split(",");
		for (String username: userArray){
			users.add(uDAO.getUser(username));
		}
		return users;
	}
	
	private String linkUsers (ArrayList<User> users){
		String usernameConcat = "";
		for (User u : users){
			usernameConcat += u.getUsername() + ",";
		}
		return usernameConcat.substring(0, usernameConcat.length());
	}
	
	private Timestamp getTimestamp(long time){
		return new Timestamp(time);
	}
	
	private long convertTimestamp(Timestamp ts){
		return ts.getTime();
	}
}
