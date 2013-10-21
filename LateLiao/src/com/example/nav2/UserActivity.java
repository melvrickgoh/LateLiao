package com.example.nav2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aws.AWSClientManager;

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
	
	 User currentUser = null;
	 
	 // Array of strings storing tab names
	 String[] mOptions ;

	 // Array of integers points to images stored in /res/drawable-ldpi/
	 int[] mLogos = new int[3];
	 
	 // Drawer Layout setup
	 private DrawerLayout mDrawerLayout;
	 private ListView mDrawerList;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private LinearLayout mDrawer ;
	 private List<HashMap<String,String>> mList ;
	 private SimpleAdapter mAdapter;
	 
	 // input of values into the interface
	 final private String IMAGEICON = "imageicon";
	 final private String TABNAME = "tabname";
		
	 //Populate list view of assignments
	 ArrayList<Event> events;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		showActionBar();
		
		Intent intent = getIntent();
		currentUser = (User) intent.getParcelableExtra("user");
		
		if (currentUser!=null){
			events = getListData(currentUser.getUsername());
		}
		
		mLogos[0] = getUserIcon(this,currentUser);
		mLogos[1] = R.drawable.add_event;
		mLogos[2] =	R.drawable.logout;

		/** Restore from the previous state if exists */
        if(savedInstanceState!=null){
            //status = savedInstanceState.getBooleanArray("status");
        }
        
        /* to create list view for assignments*/
        final ListView lvAssignments = (ListView) findViewById(R.id.lv);
        lvAssignments.setAdapter(new CustomListAdapter(this,events));
        
        /*set the created assignmentList to the listener*/
        lvAssignments.setOnItemClickListener(itemClickListener);
        
        // Getting an array of tab names
        mOptions = getResources().getStringArray(R.array.options);
        mOptions[0] = currentUser.getName();
        
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
	
	//to create the listener that does the function when the events are clicked
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> lv, View item, int position, long id) {

        	CustomListAdapter adapter = (CustomListAdapter) lv.getAdapter();  
        	Event eventItem = (Event) adapter.getItem(position);
        
            /*to send to the next activity*/
            Intent intent = new Intent(getApplicationContext(),MapActivity.class);
            intent.putExtra("user", currentUser);
            intent.putExtra("eventName", (String)eventItem.getEventName());
            intent.putExtra("eventLocation", (Location)eventItem.getEventLocation());
            intent.putExtra("eventDate", eventItem.getEventDate() + "-" + eventItem.getEventMonth() + "-" + eventItem.getEventYear() + " " + eventItem.getEventTime());
            intent.putExtra("eventTime", (String)eventItem.getEventTime());
            intent.putStringArrayListExtra("eventAttendees", eventItem.getEventAttendees());
            startActivity(intent);
        }
    };
    
	// Item click listen for drawer layout
    private OnItemClickListener drawerListener = new OnItemClickListener() {
        
	       @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	        
	    	   	if (position == 0) {
	    	   		Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
	                startActivity(intent);
	    	   		
	    	   	}
	    	   	else if (position == 1) {
	    	   		Intent intent = new Intent(getApplicationContext(),AddEvent.class);
	    	   		intent.putExtra("user", currentUser);
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
        //outState.putBooleanArray("status", status);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.user, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private ArrayList<Event> getListData(String username){		
		AWSClientManager aws = AWSClientManager.getInstance();
		ArrayList<Event> events = filterEvents(username,aws.getAllEvents());
		
		return events;//aws.getFilteredEvents(username);
	}
	
	private ArrayList<Event> filterEvents(String username,ArrayList<Event> allEvents) {
		ArrayList<Event> toRemove = new ArrayList<Event>();
		for (Event e:allEvents){
			if (!e.getEventAttendees().contains(username)){
				toRemove.add(e);
			}
		}
		
		allEvents.removeAll(toRemove);
 		return allEvents;
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
		 super.onPostCreate(savedInstanceState);
		 mDrawerToggle.syncState();
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
	 
	 private int getUserIcon(Context context, User user){
		 String imageLocation = user.getImageLocation();
		 return context.getResources().getIdentifier(imageLocation.toLowerCase(), "drawable", context.getPackageName());
	 }
}
