package com.example.nav2;

import java.util.ArrayList;

import com.aws.AWSClientManager;

import android.webkit.JavascriptInterface;

public class WebAppInterface {
	
    /** Instantiate the interface and set the context */
    WebAppInterface() {

    }
    
    public double lat=0;
    public double lon=0;
    
    public double current_lat=0;
    public double current_long=0;
    
    public int zoomarea=0;
    
    public String eventName;
    
    ArrayList<String> friendList = new ArrayList<String>();
    
    /** Show a toast from the web page 
     * @return */
    @JavascriptInterface
    public double getCurrentLat() {
    	return current_lat;
    }
    
    @JavascriptInterface
    public double getCurrentLong() {
    	return current_long;
    }
    
    @JavascriptInterface
    public double getLat() {
        return lat;
    }
    
    @JavascriptInterface
    public double getLon() {
        return lon;
    }
    
    @JavascriptInterface
    public int getArea() {
        return zoomarea;
    }
    
   //pass in data and set into a static array
    @JavascriptInterface
    public void setCurrent_Data(double current_lat, double current_long) {
    	this.current_lat = current_lat;
    	this.current_long = current_long;
    }
    @JavascriptInterface
    public String getEventName() {
		return eventName;
	}
    @JavascriptInterface
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@JavascriptInterface
    public void setData(double lat, double lon,int area) {
		this.lat = lat;
		this.lon = lon;
    	this.zoomarea = area;
    }
	
	@JavascriptInterface
	public void pinLocation(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	@JavascriptInterface
	public String passEvent(String eventName) {
		return eventName;
		
	}
	
	@JavascriptInterface
	public void setFriendList(ArrayList<String> friendList) {
		this.friendList = friendList;
	}
	
	@JavascriptInterface
	public ArrayList<String> getFriendList() {
		return this.friendList;
	}
}