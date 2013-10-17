package com.example.nav2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ProfileActivity extends ActionBarActivity {

	ProgressBar myProgressBar; 
	int myProgress = 50;
	
	int mPosition = -1;
	 String mTitle = "";
	 
	 // Array of strings storing country names
	 String[] mOptions ;
	 
	 // Array of integers points to images stored in /res/drawable-ldpi/
	 int[] mLogos = new int[]{
	R.drawable.user,
	R.drawable.add_event,
	R.drawable.logout
	 };
	 
	// Array of strings to initial counts
	 String[] mCount = new String[]{
	 "4", "", "" };
	 
	 private DrawerLayout mDrawerLayout;
	 private ListView mDrawerList;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private LinearLayout mDrawer ;
	 private List<HashMap<String,String>> mList ;
	 private SimpleAdapter mAdapter;

	 final private String IMAGEICON = "imageicon";
	 final private String TABNAME = "tabname";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		showActionBar();
		
		myProgressBar=(ProgressBar)findViewById(R.id.progressbar_Horizontal); 
		myProgressBar.setProgress(myProgress);
		
		/*here onwards will be for the sidebar*/
        // Getting an array of country names
        mOptions = getResources().getStringArray(R.array.options);
        
        // Title of the activity
        mTitle = (String)getTitle();
        
        // Getting a reference to the drawer listview
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        
        // Getting a reference to the sidebar drawer ( Title + ListView )
        mDrawer = ( LinearLayout) findViewById(R.id.drawer);
        

        // Each row in the list stores country name, count and flag
        mList = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<3;i++){
	        HashMap<String, String> hm = new HashMap<String,String>();
	        hm.put(TABNAME, mOptions[i]);
	        hm.put(IMAGEICON, Integer.toString(mLogos[i]) );
	        mList.add(hm);
        }
        

        // Keys used in Hashmap
        String[] from = { IMAGEICON,TABNAME };
        
       // Ids of views in listview_layout
        int[] to = { R.id.imageicon , R.id.tabname};
        
        // R.layout.drawer_layout defines the layout of each item
        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);
        
        // Getting reference to DrawerLayout
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        
        // Creating a ToggleButton for NavigationDrawer with drawer event listener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.sidebar , R.string.drawer_open,R.string.drawer_close){
        
	        //** Called when drawer is closed *//*
	        public void onDrawerClosed(View view) {
		        highlightSelectedCountry();
		        supportInvalidateOptionsMenu();
	        }
        
	       //** Called when a drawer is opened *//*
	        public void onDrawerOpened(View drawerView) {
	        	getSupportActionBar().setTitle("");
	        supportInvalidateOptionsMenu();
	        }
        };
        
        // Setting event listener for the drawer
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        // ItemClick event handler for the drawer items
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
        
	       @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	        
	    	   	if (position == 0) {
	    	   		Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
	                startActivity(intent);
	    	   		
	    	   	}
	    	   	else if (position == 1) {
	    	   		Intent intent = new Intent(getApplicationContext(),AddEvent.class);
	                startActivity(intent);
	    	   		//do something
	    	   	} else {	
	    	   		Intent intent = new Intent(getApplicationContext(),MainActivity.class);
		        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(intent);
	    	   	}
		        // Increment hit count of the drawer list item
		        /*incrementHitCount(position);
		        
		        if(position < 5) { // Show fragment for options : 0 to 4
		        	showFragment(position);
		        }else{ // Show message box for options : 5 to 9
		        	Toast.makeText(getApplicationContext(), mOptions[position], Toast.LENGTH_LONG).show();
		        }*/
	        
		        // Closing the drawer
		        mDrawerLayout.closeDrawer(mDrawer);
	        }
        });
        
        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
       // Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);
        
       /* sidebar.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	 mDrawerLayout.openDrawer(mDrawer);
	        }
	    });*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}
	
	
	private void showActionBar() {
        LayoutInflater inflator = (LayoutInflater) this
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	View v = inflator.inflate(R.layout.custom, null);
		    ActionBar actionBar = getSupportActionBar();
		    actionBar.setDisplayHomeAsUpEnabled(true);
		    actionBar.setDisplayShowHomeEnabled (false);
		    actionBar.setDisplayShowCustomEnabled(true);
		    actionBar.setDisplayShowTitleEnabled(false);
		    actionBar.setCustomView(v);
		    TextView temp = (TextView)findViewById(R.id.homeScreen);
		    temp.setText("Tan Janan");
		    
	}
	

	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
			if (mDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			 }
			switch (item.getItemId()) {
	        	case R.id.drawer_layout:
	        	
		        	Intent intent = new Intent(this,AssignmentActivity.class);
		        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(intent);
	         
	                break;
				       // case R.id.btn2:
				            //center button
				           // return true;
				        //case R.id.btn3:
				            // right button
				            //return true;
	        		default:
	        		return super.onOptionsItemSelected(item);
	        }
			return false;
	 }
	
	
	
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
	 super.onPostCreate(savedInstanceState);
	 mDrawerToggle.syncState();
	 
	 }
	
	// Highlight the selected country : 0 to 4
	 public void highlightSelectedCountry(){
	 int selectedItem = mDrawerList.getCheckedItemPosition();
	 
	 if(selectedItem > 4)
	 mDrawerList.setItemChecked(mPosition, true);
	 else
	 mPosition = selectedItem;
	 
	 if(mPosition!=-1)
	 getSupportActionBar().setTitle(mOptions[mPosition]);
	 }
}
