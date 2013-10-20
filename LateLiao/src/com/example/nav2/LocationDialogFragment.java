package com.example.nav2;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;

public class LocationDialogFragment extends DialogFragment implements OnTouchListener, Handler.Callback {
	private final Handler handler = new Handler(this);
	private WebView webView;
	private WebViewClient client;
	private WebAppInterface wb;
	
	private LayoutInflater inflater;
	private View view;

	public LocationDialogFragment() {
		
	}
	
	public Dialog onCreateDialog (Bundle savedInstanceState) {
		inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.fragment_map_select, null);
		
		webView = (WebView) view.findViewById(R.id.mapview_select);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient());
		webView.requestFocusFromTouch();
		
		webView.setOnTouchListener(this);
		
		wb = new WebAppInterface();
		webView.addJavascriptInterface(wb, "Android");
		webView.getSettings().setGeolocationEnabled(true);
		
		GPSTracker tracker = new GPSTracker(getActivity());
	    if (tracker.canGetLocation() == false) {
	        tracker.showSettingsAlert();
	        wb.setData(1.38333,103.75000,14);
	    } else {
	    	wb.setData(tracker.getLatitude(), tracker.getLongitude(), 14);
	    }

		client = new WebViewClient(){ 
	        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) { 
	            handler.sendEmptyMessage(1);
	            return false;
	        } 
	    }; 
			
		webView.loadUrl("file:///android_asset/www/index.html");
		WebViewClient wvc = new WebViewClient();
		wvc.onPageFinished(webView,"file:///android_asset/www/index.html");
        
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
			.setPositiveButton(getResources().getString(R.string.add_activity_friend_dialog_positive), locationOnClickListener);
		builder.setView(view);
		return builder.create();
		
	}
	
	private DialogInterface.OnClickListener locationOnClickListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int id) {
			Button locationButton = (Button) getActivity().findViewById(R.id.buttonSelectLocation);
			EditText location_name = (EditText) view.findViewById(R.id.locationName);
			locationButton.setText(location_name.getText());
			
			TextView lat = (TextView) getActivity().findViewById(R.id.coordinates_lat);
			lat.setText(String.valueOf(wb.getLat()));
			TextView lon = (TextView) getActivity().findViewById(R.id.coordinates_lon);
			lon.setText(String.valueOf(wb.getLon()));
		}
	};
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.mapview_select && event.getAction() == MotionEvent.ACTION_DOWN){
	        handler.sendEmptyMessageDelayed(2, 500);
	    }
		return false;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == 1){
	        handler.removeMessages(2);
	        return true;
	    }
	    if (msg.what == 2){
	    	TextView textView = (TextView) view.findViewById(R.id.coordinates);
	        textView.setText("lat:" + wb.getLat() + " lon:" + wb.getLon());
	        return true;
	    }
	    return false;
	}

}
