package com.example.nav2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

public class UserActivity extends ActionBarActivity  {
	//List<Map<String, String>> planetsList = new ArrayList<Map<String,String>>();
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
	 final private String COUNTRY = "country";
	 final private String FLAG = "flag";
	 final private String COUNT = "count";
	 
		
	 	//to populate the list view of assignments
	 	String[] events = new String[] {
	        "Assignment 01",
	        "Assignment 02",
	        "Assignment 03",
	        "Assignment 04",
	        "Assignment 05",
	      
	    };
	 
	    // Array of booleans to store toggle button status
	    public boolean[] status = {
	        true,
	        false,
	        false,
	        false,
	        false,
	    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		showActionBar();
		//ImageButton sidebar = (ImageButton)getSupportActionBar().getCustomView().findViewById(R.id.sidebar);
		
		
		
		/** Restore from the previous state if exists */
        if(savedInstanceState!=null){
            status = savedInstanceState.getBooleanArray("status");
        }
        
        /* to create list view for assignments*/
        ListView lvAssignments = (ListView) findViewById(R.id.lv);
        
        /*to create the listener var that does the function when the assignments are clicked*/
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lv, View item, int position, long id) {
 
                ListView lView = (ListView) lv;
 
                SimpleAdapter adapter = (SimpleAdapter) lView.getAdapter();
 
                HashMap<String,Object> hm = (HashMap) adapter.getItem(position);
 
                /** The clicked Item in the ListView */
                RelativeLayout rLayout = (RelativeLayout) item;
 
                /** Getting the toggle button corresponding to the clicked item */
                ToggleButton tgl = (ToggleButton) rLayout.getChildAt(1);
 
                String strStatus = "";
                if(tgl.isChecked()){
                    tgl.setChecked(false);
                    strStatus = "Off";
                    status[position]=false;
                }else{
                    tgl.setChecked(true);
                    strStatus = "On";
                    status[position]=true;
                }
                /*pop up box to show that the respective assignment is clicked*/
                Toast.makeText(getBaseContext(), (String) hm.get("txt") + " : " + strStatus, Toast.LENGTH_SHORT).show();
                /*to send to the next activity*/
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra("country", (String)hm.get("txt"));
                startActivity(intent);
            }
        };
        
        /*set the created assignmentList to the listener*/
        lvAssignments.setOnItemClickListener(itemClickListener);
 
       
        // Each row in the list stores country name and its status. populate the list with the items
        List<HashMap<String,Object>> aList = new ArrayList<HashMap<String,Object>>();
 
        //the number of items to be populate must match here or there will be indexoutofbound error
        for(int i=0;i<5;i++){
            HashMap<String, Object> hm = new HashMap<String,Object>();
            hm.put("txt", events[i]);
            hm.put("stat",status[i]);
            aList.add(hm);
        }
 
        // Keys used in Hashmap
        String[] there = {"txt","stat" };
 
        // Ids of views in listview_layout
        int[] here = { R.id.tv_item, R.id.tgl_status};
 
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.assignments, there, here);
        
        /*set the list to an adapter to mointor any clicks*/
        lvAssignments.setAdapter(adapter);
        
        
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
	    	   		Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
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
 
    /** Saving the current state of the activity
    * for configuration changes [ Portrait <=> Landscape ]
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray("status", status);
    }

		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.user, menu);
		return super.onCreateOptionsMenu(menu);
		
		
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
