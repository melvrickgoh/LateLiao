package com.example.nav2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UserActivity extends ActionBarActivity  {

	 // Array of strings storing tab names
	 String[] mOptions ;
	 
	 // Array of integers points to images stored in /res/drawable-ldpi/
	 int[] mLogos = new int[]{
		R.drawable.user,
		R.drawable.add_event,
		R.drawable.logout
	 };
	 
	 // Drawer Layout setup
	 private DrawerLayout mDrawerLayout;
	 private ListView mDrawerList;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private LinearLayout mDrawer;
	 private List<HashMap<String,String>> mList ;
	 private SimpleAdapter mAdapter;
	 
	 // input of values into the interface
	 final private String IMAGEICON = "imageicon";
	 final private String TABNAME = "tabname";
	 //final private String COUNT = "count";
		
	 //Populate list view of assignments
	 ArrayList<Event> events = getListData();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		// Show action bar
		showActionBar();
        
		/** Restore from the previous state if exists */
        if(savedInstanceState!=null){
        	//onSaveInstanceState(savedInstanceState);
        }
        
        /* to create list view for assignments*/
        final ListView lvAssignments = (ListView) findViewById(R.id.lv);
        lvAssignments.setAdapter(new CustomListAdapter(this,events));
        
        /*to create the listener var that does the function when the assignments are clicked*/
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lv, View item, int position, long id) {

            	CustomListAdapter adapter = (CustomListAdapter) lvAssignments.getAdapter();
            	Event eventItem = (Event) adapter.getItem(position);
 
                /*to send to the next activity*/
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra("eventName", (String)eventItem.getEventName());
	            intent.putExtra("eventLocation", (Location)eventItem.getEventLocation());
	            intent.putExtra("eventDate", (String)eventItem.getEventDate());
	            intent.putExtra("eventTime", (String)eventItem.getEventTime());
	            intent.putStringArrayListExtra("eventAttendees", eventItem.getEventAttendees());
	            startActivity(intent);
            }
        };
        
        /*set the created assignmentList to the listener*/
        lvAssignments.setOnItemClickListener(itemClickListener);
        
        
        // Getting an array of tab names
        mOptions = getResources().getStringArray(R.array.options);
        
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
        
       // Instantiating an adapter to store each items
        // R.layout.drawer_layout defines the layout of each item
        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);
        
        // Getting reference to DrawerLayout
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        
        // Creating a ToggleButton for NavigationDrawer with drawer event listener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.sidebar , R.string.drawer_open,R.string.drawer_close){
        
	        //** Called when drawer is closed *//*
	        public void onDrawerClosed(View view) {
		        supportInvalidateOptionsMenu();
	        }
        
	       //** Called when a drawer is opened *//*
	        public void onDrawerOpened(View drawerView) {
	        	getSupportActionBar().setTitle("Sidebar");
	        	supportInvalidateOptionsMenu();
	        }
        };
        
        // Setting event listener for the drawer
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        // ItemClick event handler for the drawer items
        mDrawerList.setOnItemClickListener(drawerListener);
        
        // Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);
        
        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
 
	// Item click listen for drawer layout
	OnItemClickListener drawerListener = new OnItemClickListener() {
        
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
		       
		        // Closing the drawer
		        mDrawerLayout.closeDrawer(mDrawer);
	        }
     };
	
    /** Saving the current state of the activity
    * for configuration changes [ Portrait <=> Landscape ]
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
			
			if (item.getItemId() == (R.id.drawer_layout)) {
				Intent intent = new Intent(this,AssignmentActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
			} else {
				return super.onOptionsItemSelected(item);
			}
			
			return false;
	 }
	
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
		 super.onPostCreate(savedInstanceState);
		 mDrawerToggle.syncState();
	 
	 }	 
	 
	 /**
	  * Static information for UAT purpose without database (Amazon)
	  * @return
	  */
	 private ArrayList<Event> getListData() {
			ArrayList<Event> events = new ArrayList<Event>();
			ArrayList<String> dummyAttendees = new ArrayList<String>();
			dummyAttendees.add("Leon Lee");
			dummyAttendees.add("Melvrick Goh");
			dummyAttendees.add("Janan Tan");
			dummyAttendees.add("Wyner Lim");
			dummyAttendees.add("Ben Gan");
			
			events.add(new Event("IDP Meeting","Fri, 18 0ct 2013","0800", dummyAttendees, new Location("SIS GSR 2.1",1.29757,103.84944)));
			events.add(new Event("IDP Lesson","Fri, 18 0ct","1200", dummyAttendees, new Location("SIS SR 3.4",1.29757,103.84944)));
			events.add(new Event("Dinner with GF","Fri, 18 0ct","1900", dummyAttendees, new Location("313 @ Somerset",1.300386800000000000,103.838803999999980000)));
			events.add(new Event("Chinatown Brugge","Fri, 18 0ct","2300", dummyAttendees, new Location("William's Cafe",1.28216,103.8448)));
			events.add(new Event("The Swansong Feast","Fri, 13 Nov","0800", dummyAttendees, new Location("Big Steps",1.29757,103.84944)));
			
			return events;
		}
	 
}
