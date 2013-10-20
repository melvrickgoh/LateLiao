package com.example.nav2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;
    Fragment mFragment;
    /** Instantiate the interface and set the context */
  
    WebAppInterface(Fragment mapFragment) {
        mFragment = mapFragment;
    }
    
    public double lon=0;
    public double lat=0;
    public int zoomarea=0;
    /** Show a toast from the web page 
     * @return */
    @JavascriptInterface
    public double getLat() {
        return lon;
    }
    
    @JavascriptInterface
    public double getLon() {
        return lat;
    }
    
    @JavascriptInterface
    public int getArea() {
        return zoomarea;
    }
    @JavascriptInterface
    public void setData() {
    	
    	String a=lon+"";
    	// Toast.makeText(mFragment, a, Toast.LENGTH_SHORT).show();
    }
   //pass in data and set into a static array

	@JavascriptInterface
    public void setData(double lon1,double lat1,int area1) {
    	lon=lon1;
    	lat=lat1;
    	zoomarea=area1;
    }
}