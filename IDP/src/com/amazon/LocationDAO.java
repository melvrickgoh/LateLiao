package com.amazon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.entity.Location;

public class LocationDAO {
	private SimpleDB sdb;
	
	public LocationDAO(){
		sdb = new SimpleDB();
	}
	
	public boolean checkLocationDomainExists(){
		@SuppressWarnings("static-access")
		List<String> domainNames = sdb.getDomainNames();
		if (domainNames.contains("Location")){
			return true;
		}
		return false;
	}
	
	public boolean checkLocationExists(String locationName){
		@SuppressWarnings("static-access")
		String[] locations = sdb.getItemNamesForDomain("Location");
		if (Arrays.asList(locations).contains(locationName)){
			return true;
		}
		return false;
	}
	
	public Location getLocation(String locationName){
		@SuppressWarnings("static-access")
		HashMap<String,String> locationAttributes = sdb.getAttributesForItem("Location", locationName);
		String location = locationAttributes.get("locationName");
		double latitude = Double.parseDouble(locationAttributes.get("latitude"));
		double longitude = Integer.parseInt((locationAttributes.get("longitude")));
		
		return new Location(location,latitude,longitude);
	}
	
	@SuppressWarnings("static-access")
	public void addLocation(Location location){
		String locationName = location.getLocationName();
		sdb.createItem("Location", locationName);
		sdb.createAttributeForItem("Location", locationName, "locationName", "" + locationName);
		sdb.createAttributeForItem("Location", locationName, "latitude", "" + location.getLatitude());
		sdb.createAttributeForItem("Location", locationName, "longitude", "" + location.getLongitude());
	}
	
	@SuppressWarnings("static-access")
	public void updateLocation(Location location){
		String locationName = location.getLocationName();
		sdb.updateAttributesForItem("Location", locationName, mapUserAttributes(location));
	}
	
	private HashMap<String,String> mapUserAttributes (Location location){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("locationName", location.getLocationName());
		map.put("latitude", "" + location.getLatitude());
		map.put("longitude", "" + location.getLongitude());
		return map;
	}
}
