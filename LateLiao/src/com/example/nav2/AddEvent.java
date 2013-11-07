package com.example.nav2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.aws.AWSClientManager;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class AddEvent extends ActionBarActivity implements OnDateSetListener, OnTimeSetListener, OnFriendsEventListener  {
	private EasyTracker tracker;
	
	Button dateButton;
	Button timeButton;
	Button locationButton;
	Button friendsButton;
	
	private User currentUser;
	private Event editEvent;
	private boolean editEventStatus = false;
	
	private int eventDate = 0;
	private int eventMonth = 0;
	private int eventYear = 0;
	private Bundle savedInstanceState = new Bundle();
	private String eventTime = "";
	private Location eventLocation = new Location("", 0, 0);
	private ArrayList<String> eventFriends = new ArrayList<String>();
	
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
	// Array of strings storing country names
	String[] mOptions ;
		 
	// Array of integers points to images stored in /res/drawable-ldpi/
	 int[] mLogos = new int[4];
	 
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
		setContentView(R.layout.activity_add_event);
		
		showActionBar();
		
		Bundle intentBundle = getIntent().getExtras();
		this.currentUser = (User) intentBundle.get("user");
		Object editEvent = intentBundle.get("editEvent");
		
		if (editEvent != null){
			editEventStatus = true;
			this.editEvent = (Event) editEvent;
			Log.d("event", "" + this.editEvent.getEventLocation().getLatitude());
			Log.d("event", "" + this.editEvent.getEventLocation().getLongitude());
			this.savedInstanceState.putParcelable("editEvent", this.editEvent);
		}
		
		this.savedInstanceState.putParcelable("user", this.currentUser);
		
		setupUI(findViewById(R.id.addEventActivity));

		this.savedInstanceState.putParcelable("AddLocation", this.eventLocation);
		this.savedInstanceState.putStringArrayList("AddFriend", this.eventFriends);
		
		//Set Submit Button Listener
		Button submitButton = (Button)findViewById(R.id.addActivitySubmit);
		submitButton.setOnClickListener(submitButtonListener);
		
		//Set Date Button Listener
		dateButton = (Button)findViewById(R.id.buttonSelectDate);
		dateButton.setOnClickListener(dateButtonListener);
		
		//Set Time Button Listener
		timeButton = (Button)findViewById(R.id.buttonSelectTime);
		timeButton.setOnClickListener(timeButtonListener);
		
		locationButton = (Button)findViewById(R.id.buttonSelectLocation);
		locationButton.setOnClickListener(locationButtonListener);
		
		//Set Friends Button Listener
		friendsButton = (Button)findViewById(R.id.buttonSelectFriends);
		friendsButton.setOnClickListener(friendsButtonListener);
		
		if (editEventStatus){
			this.setTitle(this.editEvent.getEventName());
			updateButtonValues(this.editEvent,this.currentUser,dateButton,timeButton,locationButton,friendsButton);
		}
		
		mLogos[0] = getUserIcon(this,currentUser);
		mLogos[1] = R.drawable.add_event;
		mLogos[2] =	R.drawable.friends;
		mLogos[3] =	R.drawable.logout;
		
		/* Side bar creation */
	    // Getting an array of country names
	    mOptions = getResources().getStringArray(R.array.options);
	    mOptions[0] = currentUser.getName();
	    
	    // Getting a reference to the drawer listview
	    mDrawerList = (ListView) findViewById(R.id.drawer_list);
	    
	    // Getting a reference to the sidebar drawer ( Title + ListView )
	    mDrawer = ( LinearLayout) findViewById(R.id.drawer);
	    
	    // Each row in the list stores country name, count and flag
	    mList = new ArrayList<HashMap<String,String>>();
	    for(int i=0;i<4;i++){
	        HashMap<String, String> hm = new HashMap<String,String>();
	        hm.put(TABNAME, mOptions[i]);
	        hm.put(IMAGEICON, Integer.toString(mLogos[i]) );
	        mList.add(hm);
	    }
    
	   // Keys used in Hashmap
	    String[] from = { IMAGEICON, TABNAME };
	    
	   // Ids of views in listview_layout
	    int[] to = { R.id.imageicon , R.id.tabname };
    
	   // Instantiating an adapter to store each items
	    mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);
	    
	    // Getting reference to DrawerLayout
	    mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
	    
	    // Creating a ToggleButton for NavigationDrawer with drawer event listener
	    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.sidebar , R.string.drawer_open,R.string.drawer_close){
    
	        //** Called when drawer is closed *//*
	        public void onDrawerClosed(View view) {
		        invalidateOptionsMenu();
	        }
	    
	       //** Called when a drawer is opened *//*
	        public void onDrawerOpened(View drawerView) {
	        	getSupportActionBar().setTitle("SideBar");
	        	invalidateOptionsMenu();
	        }
	    };
    
	    // Setting event listener for the drawer
	    mDrawerLayout.setDrawerListener(mDrawerToggle);
	    
	    // ItemClick event handler for the drawer items
	    mDrawerList.setOnItemClickListener(drawerListener);
    
	    // Enabling Up navigation
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setDisplayShowHomeEnabled(true);
    
	   // Setting the adapter to the listView
	    mDrawerList.setAdapter(mAdapter);
	    Calendar cal = Calendar.getInstance();
	    onLoad(cal.getTimeInMillis());
	}
	
	public void onLoad(long loadTime) {
		  tracker.send(MapBuilder
		      .createTiming("resources",    // Timing category (required)
		                    loadTime,       // Timing interval in milliseconds (required)
		                    "Add Event Begin",  // Timing name
		                    null)           // Timing label
		      .build()
		  );
		}
	
	private void updateButtonValues(Event editEvent, User currentUser, Button dateButton, Button timeButton, Button locationButton,
			Button friendsButton) {
		EditText eventName = (EditText) findViewById(R.id.eventName);
    	eventName.setText(editEvent.getEventName());
    	eventName.setEnabled(false);

		eventDate = editEvent.getEventYear();
		eventMonth = editEvent.getEventMonth();
		eventYear = editEvent.getEventDate();
    	Calendar cal = new GregorianCalendar(eventDate, eventMonth, eventYear);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM, yyyy");
		dateButton.setText(sdf.format(cal.getTime()));
		
		eventTime = editEvent.getEventTime();
		int eventTimeInt = Integer.parseInt(eventTime);
		cal.set(Calendar.HOUR, eventTimeInt/100);
		cal.set(Calendar.MINUTE, eventTimeInt%100);
		timeButton.setText(editEvent.getEventTime());
		SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
		timeButton.setText(sdf1.format(cal.getTime()));
		
		eventLocation = editEvent.getEventLocation();
		locationButton.setText(eventLocation.getLocationName());
		
		eventFriends = editEvent.getEventAttendees();
		String friends = "";
		for (String s : eventFriends){
			friends += s + ",";
		}
		friendsButton.setText(friends.substring(0, friends.length()-1));
	}

	private OnClickListener friendsButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ArrayList<User> users = getListData();
			users = filterCurrentUser(users,currentUser);
			DialogFragment friendsFragment = new FriendsDialogFragment(AddEvent.this,users,savedInstanceState);
			friendsFragment.show(ft, "friends_dialog");
		}

		private ArrayList<User> filterCurrentUser(ArrayList<User> users, User currentUser) {
			User toRemove = currentUser;
			for (User u : users){
				if (u.getUsername().equalsIgnoreCase(currentUser.getUsername())){
					toRemove = u;
				}
			}
			users.remove(toRemove);
			return users;
		}
		
	};
	
	@Override
	public void OnFriendsSet() {
		// TODO Auto-generated method stub
		
	}
	
	private OnClickListener timeButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			DialogFragment timeFragment = new TimeDialogFragment(AddEvent.this);
			timeFragment.show(ft, "time_dialog");
		}
		
	};
	
	@Override
	public void onTimeSet(TimePicker view, int hours, int minutes) {
		Calendar cal = new GregorianCalendar().getInstance();
		cal.set(Calendar.HOUR, hours);
		cal.set(Calendar.MINUTE, minutes);
		eventTime = hours + "" +minutes;
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		timeButton.setText(sdf.format(cal.getTime()));
	}
	
	private OnClickListener dateButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			DialogFragment dateFragment = new DateDialogFragment(AddEvent.this);
			dateFragment.show(ft, "date_dialog");
		}
	
	};
	
	private OnClickListener locationButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			DialogFragment locationFragment = new LocationDialogFragment(savedInstanceState);
			locationFragment.show(ft, "location_dialog");
		}
		
	};
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM, yyyy");
		eventDate = dayOfMonth;
		eventMonth = monthOfYear;
		eventYear = year;
		dateButton.setText(sdf.format(cal.getTime()));
	}		
			
	private OnClickListener submitButtonListener = new OnClickListener() {
	    @Override
		public void onClick(View v) {
	    	// do something when the button is clicked
	    	EditText eventName = (EditText) findViewById(R.id.eventName);
	    	String eventNameString = eventName.getText().toString();
	    	
	    	eventName.setVisibility(View.INVISIBLE);
	    	final Event newEvent = new Event(eventNameString,eventDate,eventMonth,eventYear,eventTime,eventFriends,eventLocation);
	    	
            AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent.this);
            
            builder.setTitle(eventNameString);
            builder.setMessage(eventNameString + " has been submitted");
	    	
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            			
            	@Override
            	public void onClick(DialogInterface dialog, int which){
            		Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            		intent.putExtra("event", newEvent);//eventually we should be dealing with the object separately
            		intent.putExtra("user", currentUser);
            		sendToDatabase(newEvent);
            		startActivity(intent);
            		Calendar cal = Calendar.getInstance();
            		onLoad(cal.getTimeInMillis());
            	}
            	
            	public void sendToDatabase(Event event){
            		AWSClientManager aws = new AWSClientManager().getInstance();
            		aws.addNewEvent(event);
            	}
            	
            	public void onLoad(long loadTime){
              	  tracker.send(MapBuilder
              	      .createTiming("resources",    // Timing category (required)
              	                    loadTime,       // Timing interval in milliseconds (required)
              	                    "Add Event Completed",  // Timing name
              	                    null)           // Timing label
              	      .build()
              	  );
              	}
            });

            builder.show();
            eventName.setVisibility(View.VISIBLE);
	    }
	};
	
	private OnItemClickListener drawerListener = new OnItemClickListener() {
	    
	       @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	        
	    	   if (position == 0) {
	    		   	submitTrackerMessage("User Activity","Drawer: Select Profile","Go to Profile Activity",null);
	    	   		Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
	                intent.putExtra("user", currentUser);
	                startActivity(intent);
	    	   		
	    	   	}
	    	   	else if (position == 1) {
	    	   		//do something
	    	   	}
	    	   	else if (position == 2) {
	    	   		submitTrackerMessage("User Activity","Drawer: View Friends","Go to View Friends",null);
	    	   		Intent intent = new Intent(getApplicationContext(),FriendsActivity.class);
	    	   		intent.putExtra("user", currentUser);
	                startActivity(intent);
	    	   		//do something
	    	   	} else {	
	    	   		submitTrackerMessage("User Activity","Drawer: Main Activity","Go to Main Activity",null);
	    	   		Intent intent = new Intent(getApplicationContext(),MainActivity.class);
		        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(intent);
	    	   	}
	        
		        // Closing the drawer
		        mDrawerLayout.closeDrawer(mDrawer);
	       }
	};
	
	private ActionBar showActionBar() {
        LayoutInflater inflator = (LayoutInflater) this
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	View v = inflator.inflate(R.layout.custom, null);
        	ActionBar actionBar = getSupportActionBar();
		    actionBar.setDisplayHomeAsUpEnabled(true);
		    actionBar.setDisplayShowHomeEnabled (false);
		    actionBar.setDisplayShowCustomEnabled(true);
		    actionBar.setDisplayShowTitleEnabled(false);
		    actionBar.setCustomView(v);
		    return actionBar;
	}
	
	private int getUserIcon(Context context, User user){
		 String imageLocation = user.getImageLocation();
		 return context.getResources().getIdentifier(imageLocation.toLowerCase(), "drawable", context.getPackageName());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current tab position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getSupportActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current tab position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getSupportActionBar()
				.getSelectedNavigationIndex());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
	     return super.onCreateOptionsMenu(menu);
	}
	
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	
	public void setupUI(View view) {

	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) {

	        view.setOnTouchListener(new OnTouchListener() {

	            public boolean onTouch(View v, AddEvent event) {
	                hideSoftKeyboard(event);
	                return false;
	            }

				public void onClick(View arg0) {
					// TODO Nothing
				}

				@Override
				public boolean onTouch(View arg0, android.view.MotionEvent arg1) {
					// TODO Auto-generated method stub
					return false;
				}

	        });
	    }

	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) {

	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

	            View innerView = ((ViewGroup) view).getChildAt(i);

	            setupUI(innerView);
	        }
	    }
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		 }
		return false;
	}
	
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
		 super.onPostCreate(savedInstanceState);
		 mDrawerToggle.syncState();
	 
	 }
	 
	private ArrayList getListData(){
		AWSClientManager aws = AWSClientManager.getInstance();		
		return aws.getAllUsers();
	}
	
	public void setEventLocation(Location locale){
		this.eventLocation = locale;
	}
	
	public void onStart() {
		 super.onStart();
		 tracker = EasyTracker.getInstance(this);
		 tracker.activityStart(this);  // Add this method.
	 }
		 
	 public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
	 
	 private void submitTrackerMessage(String category, String action, String label, Long value){
		 tracker.send(MapBuilder.createEvent(category,action,label,value).build());
	 }
}