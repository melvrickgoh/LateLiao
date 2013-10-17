package com.example.nav2;


//import com.google.android.gms.maps.GoogleMap;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MapFragment extends Fragment {
	//private GoogleMap googleMap;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			WebView webView;
			//RelativeLayout rootView =(RelativeLayout)inflater.inflate(R.layout.activity_map, container, false);
			RelativeLayout rootView =(RelativeLayout)inflater.inflate(R.layout.map, container, false);
			  		
			webView = (WebView)rootView.findViewById(R.id.mapview);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebViewClient(new WebViewClient());
			webView.setWebChromeClient(new WebChromeClient());
			webView.requestFocusFromTouch();
			//webView.loadUrl("file:///android_asset/www/track.html");
			WebAppInterface wb = new WebAppInterface(this);
			webView.addJavascriptInterface(wb, "Android");
			webView.getSettings().setGeolocationEnabled(true);
		
				wb.setData(1.3,103.8,14);
			webView.loadUrl("file:///android_asset/www/index1.html");
			WebViewClient wvc = new WebViewClient();
			wvc.onPageFinished(webView,"file:///android_asset/www/index1.html");
	        
			return rootView;
			/*

			try {
	            // Loading map
	            initilizeMap();
	            
	        } catch (Exception e) {
	            //e.printStackTrace();
	        }
			return (RelativeLayout) inflater.inflate(R.layout.map, container, false);
			*/
	}

	/**
     * function to load map. If map is not created it will create it for you
     * */
		/*
    private void initilizeMap() {
        if (googleMap == null) {
           googleMap = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                //Toast.makeText(MapFragment.this,
                        //"Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        //.show();
            }
        }
    }
 
    @Override
	public void onResume() {
        super.onResume();
        initilizeMap();
    }
    */
}

