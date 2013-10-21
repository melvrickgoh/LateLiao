package com.example.nav2;

//import com.google.android.gms.maps.GoogleMap;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class MapFragment extends Fragment {
	//private GoogleMap googleMap;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			// Retrieve location object
			Location eventLocation = (Location) getActivity().getIntent().getParcelableExtra("eventLocation");
			
			WebView webView;
			RelativeLayout rootView =(RelativeLayout)inflater.inflate(R.layout.map, container, false);
			  		
			// Setting up web view for leaflet map
			webView = (WebView)rootView.findViewById(R.id.mapview);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebViewClient(new WebViewClient());
			webView.setWebChromeClient(new WebChromeClient());
			webView.requestFocusFromTouch();
			
			WebAppInterface wb = new WebAppInterface();
			//webView.loadUrl("file:///android_asset/www/track.html");
			webView.addJavascriptInterface(wb, "Android");
			webView.getSettings().setGeolocationEnabled(true);
		
			// Get event location
			wb.setData(eventLocation.getLatitude(),eventLocation.getLongitude(),14);
			
			// Mark current location
			GPSTracker tracker = new GPSTracker(getActivity());
		    if (tracker.canGetLocation() == false) {
		        tracker.showSettingsAlert();
		    } else {
		    	tracker.getLocation();
		    	wb.setCurrent_Data(tracker.getLatitude(), tracker.getLongitude());
		    }
		    
			webView.loadUrl("file:///android_asset/www/index1.html");
			WebViewClient wvc = new WebViewClient();
			wvc.onPageFinished(webView,"file:///android_asset/www/index1.html");
	        
			return rootView;
		}
}

