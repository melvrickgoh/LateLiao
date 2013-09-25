package com.amazon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.entity.Location;
import com.entity.User;

public class UserDAO {
	private SimpleDB sdb;
	private LocationDAO lDAO;
	
	public UserDAO(LocationDAO lDAO){
		sdb = new SimpleDB();
		this.lDAO = lDAO;
	}
	
	public boolean checkUserDomainExists(){
		@SuppressWarnings("static-access")
		List<String> domainNames = sdb.getDomainNames();
		if (domainNames.contains("Users")){
			return true;
		}
		return false;
	}
	
	public boolean checkUsernameExists(String username){
		@SuppressWarnings("static-access")
		String[] usernames = sdb.getItemNamesForDomain("Users");
		if (Arrays.asList(usernames).contains(username)){
			return true;
		}
		return false;
	}
	
	public User getUser(String username){
		@SuppressWarnings("static-access")
		HashMap<String,String> userAttributes = sdb.getAttributesForItem("Users", username);
		String userName = userAttributes.get("username");
		int level = Integer.parseInt(userAttributes.get("level"));
		int currentPoints = Integer.parseInt((userAttributes.get("currentPoints")));
		String locationName = userAttributes.get("locationName");
		Location locale = lDAO.getLocation(locationName);
		
		return new User(userName,level,currentPoints,locale);
	}
	
	@SuppressWarnings("static-access")
	public void addUser(User user){
		String username = user.getUsername();
		sdb.createItem("Users", username);
		sdb.createAttributeForItem("Users", username, "level", "" + user.getLevel());
		sdb.createAttributeForItem("Users", username, "currentPoints", "" + user.getCurrentPoints());
		sdb.createAttributeForItem("Users", username, "locationName", "" + user.getCurrentLocation().getLocationName());
	}
	
	@SuppressWarnings("static-access")
	public void updateUser(User user){
		String username = user.getUsername();
		sdb.updateAttributesForItem("Users", username, mapUserAttributes(user));
	}
	
	private HashMap<String,String> mapUserAttributes (User user){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("username", user.getUsername());
		map.put("level", "" + user.getLevel());
		map.put("currentPoints", "" + user.getCurrentPoints());
		map.put("locationName", "" + user.getCurrentLocation().getLocationName());
		return map;
	}
}
