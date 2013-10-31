package com.example.nav2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.aws.AWSClientManager;
import com.plugins.LevelComparator;
import com.plugins.NameComparator;
import com.plugins.ShakeDetector;
import com.plugins.ShakeDetector.OnShakeListener;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class FriendsActivity extends ActionBarActivity {
	private EasyTracker tracker;
	
	// The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
	
	User currentUser = null;
	 
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
	R.drawable.friends,
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
	 private Comparator nameComparator = new NameComparator();
	 private Comparator levelComparator = new LevelComparator();
	 private boolean isNameComparator = true;

	 final private String IMAGEICON = "imageicon";
	 final private String TABNAME = "tabname";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		
		Intent intent = getIntent();
		currentUser = (User) intent.getParcelableExtra("user");
		/*here onwards will be for the sidebar*/
        // Getting an array of country names
        mOptions = getResources().getStringArray(R.array.options);
        mOptions[0] = currentUser.getName();

		mLogos[0] = getUserIcon(this,currentUser);
		
		ArrayList<User> friends = null;
        boolean useLevelComparator = intent.getBooleanExtra("IsLevelComparator", true);
        
        if (useLevelComparator){
        	friends =  getFriends(levelComparator);
            intent.putExtra("IsLevelComparator",false);
        }else{
        	friends =  getFriends(nameComparator);
            intent.putExtra("IsLevelComparator",true);
        }
        
        final CustomFriendAdapter friendAdapter = new CustomFriendAdapter(this,friends,currentUser);
        
        final ListView lvAssignments = (ListView) findViewById(R.id.list);
        lvAssignments.setAdapter(friendAdapter);
        
        /*set the created assignmentList to the listener*/
        lvAssignments.setOnItemClickListener(itemClickListener);
		
        
        // Title of the activity
        mTitle = (String)getTitle();
        
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
	    	   		submitTrackerMessage("Friends Activity","Drawer: Select Profile","Go to Profile Activity",null);
	    	   		Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
	    	   		intent.putExtra("user", currentUser);
	    	   		startActivity(intent);
	    	   		
	    	   	}
	    	   	else if (position == 1) {
	    	   		Intent intent = new Intent(getApplicationContext(),AddEvent.class);
	    	   		intent.putExtra("user", currentUser);
	                startActivity(intent);
	    	   		//do something
	    	   	} 
	    	   	else if (position == 2) {
	    	   		
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
        
     // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new OnShakeListener() {
 
            @Override
            public void onShake(int count) {
            	finish();
				startActivity(getIntent());
            }
        });
		
	}
	
	@Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,    SensorManager.SENSOR_DELAY_UI);
    }
 
    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
				MenuInflater inflater = getMenuInflater();
				inflater.inflate(R.menu.user, menu);
				return super.onCreateOptionsMenu(menu);
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

 
	//to create the listener that does the function when the events are clicked
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> lv, View item, int position, long id) {
        	submitTrackerMessage("Friends Activity","Select Profile to view","Custom friends view of profile",null);
        	CustomFriendAdapter adapter = (CustomFriendAdapter) lv.getAdapter();  
        	User user = (User) adapter.getItem(position);
        
            /*to send to the next activity*/
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            intent.putExtra("user", currentUser);
            intent.putExtra("profileUser", user);
            startActivity(intent);
        }
    };
	 
	 private int getUserIcon(Context context, User user){
		 String imageLocation = user.getImageLocation();
		 return context.getResources().getIdentifier(imageLocation.toLowerCase(), "drawable", context.getPackageName());
	 }
	 
	 @SuppressWarnings("unchecked")
	private ArrayList<User> getFriends(Comparator comparator){
		 AWSClientManager aws = AWSClientManager.getInstance();
		 ArrayList<User> friends =  aws.getAllUsers(); 
	        
	     for(int i = 0; i < friends.size(); i++) {
	    	 if(friends.get(i).getName().equals(currentUser.getName())) {
	        		friends.remove(i--);
	    	 }
	     }
	     Collections.sort(friends,comparator);
		return friends;
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
