package com.example.nav2;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
	
@SuppressLint("NewApi")
public class MapActivity extends ActionBarActivity {
	
	 int mPosition = -1;
	 String mTitle = "";
	 
	 // Array of strings storing country names
	 String[] mOptions ;
	 
	 // Array of integers points to images stored in /res/drawable-ldpi/
	 int[] mLogos = new int[]{
	R.drawable.notification,
	R.drawable.validation,
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
	 final private String COUNTRY = "country";
	 final private String FLAG = "flag";
	 final private String COUNT = "count";
	
	 /**
	 * The serialization (saved instance state) Bundle key representing the
	 * current tab position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
	 Fragment detailsFragment = new DetailsFragment();
	 Fragment mapFragment = new MapFragment();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		ActionBar actionBar = showActionBar();
		
		//Everything bfore u create u should get the Assignment Id
		setTitle("Assigment Id");
		//final ActionBar actionBar = getSupportActionBar();
		//Tab1 = actionBar.newTab().setText(R.string.title_maps);
      //  Tab2 = actionBar.newTab().setText(R.string.title_details);
       // Set up the action bar to show tabs.
	
		/*actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#68e4d6")));
		actionBar.setIcon(R.drawable.blue);*/
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Tab1.setTabListener(new TabListener(detailsFragment));
		// Tab1.setTabListener(new TabListener(mapFragment));
		//actionBar.addTab(Tab1);
		//actionBar.addTab(Tab2); 
		
		actionBar.addTab(actionBar.newTab().setText(R.string.title_maps)
				.setTabListener( new TabListener(mapFragment)));
				
		//For each of the sections in the app, add a tab to the action bar.
	actionBar.addTab(actionBar.newTab().setText(R.string.title_details)
		.setTabListener( new TabListener(detailsFragment)));
	
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
        hm.put(COUNTRY, mOptions[i]);
        hm.put(COUNT, mCount[i]);
        hm.put(FLAG, Integer.toString(mLogos[i]) );
        mList.add(hm);
    }
    
   // Keys used in Hashmap
    String[] from = { FLAG,COUNTRY,COUNT };
    
   // Ids of views in listview_layout
    int[] to = { R.id.flag , R.id.country , R.id.count};
    
   // Instantiating an adapter to store each items
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
        	getSupportActionBar().setTitle("SideBar");
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
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current tab position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current tab position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.user, menu);
	     return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		 }
	    // Handle presses on the action bar items
	    /*switch (item.getItemId()) {
	    	case 
	          
	            return true;
	        
	          
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }*/
		return false;
	}
	
	private ActionBar showActionBar() {
        LayoutInflater inflator = (LayoutInflater) this
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	View v = inflator.inflate(R.layout.custom, null);
        	android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		    actionBar.setDisplayHomeAsUpEnabled(true);
		    actionBar.setDisplayShowHomeEnabled (false);
		    actionBar.setDisplayShowCustomEnabled(true);
		    actionBar.setDisplayShowTitleEnabled(false);
		    actionBar.setCustomView(v);
		    return actionBar;
		    
	}
	
	
	 
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
	 super.onPostCreate(savedInstanceState);
	 mDrawerToggle.syncState();
	 
	 }
	 
	 public void clearCountOnClick(int position) {
		 HashMap<String, String> item = mList.get(position);
		 String count = item.get(COUNT);
		 item.remove(COUNT);
		 int cnt = Integer.parseInt(count.trim());
		 cnt = 0;
		 count = " " + cnt + " ";
		 
		 item.put(COUNT, count);
		 mAdapter.notifyDataSetChanged();
	 }
	 public void incrementHitCount(int position){
		 HashMap<String, String> item = mList.get(position);
		 String count = item.get(COUNT);
		 item.remove(COUNT);
		 if(count.equals("")){
		 count = " 1 ";
		 }else{
		 int cnt = Integer.parseInt(count.trim());
		 cnt ++;
		 count = " " + cnt + " ";
		 }
		 item.put(COUNT, count);
		 mAdapter.notifyDataSetChanged();
	}
		 
		 public void showFragment(int position){
		 
		 //Currently selected country
		 mTitle = mOptions[position];
		 
		// Creating a fragment object
		 CountryFragment cFragment = new CountryFragment();
		 
		// Creating a Bundle object
		 Bundle data = new Bundle();
		 
		// Setting the index of the currently selected item of mDrawerList
		 data.putInt("position", position);
		 
		// Setting the position to the fragment
		 cFragment.setArguments(data);
		 
		// Getting reference to the FragmentManager
		 android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		 
		// Creating a fragment transaction
		 android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
		 
		// Adding a fragment to the fragment transaction
		 ft.replace(R.id.content_frame, cFragment);
		 
		// Committing the transaction
		 ft.commit();
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
		
	
