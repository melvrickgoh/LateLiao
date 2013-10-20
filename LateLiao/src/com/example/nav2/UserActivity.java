package com.example.nav2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aws.AWSClientManager;

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
	 int[] mLogos = new int[3];
	 
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
	 //final private String COUNT = "count";
		
	 //Populate list view of assignments
	 ArrayList events;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		showActionBar();
		//ImageButton sidebar = (ImageButton)getSupportActionBar().getCustomView().findViewById(R.id.sidebar);
		
		Intent intent = getIntent();
		User currentUser = (User) intent.getParcelableExtra("user");
		
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
        
        /*to create the listener var that does the function when the assignments are clicked*/
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lv, View item, int position, long id) {
            	
            	Object o = lvAssignments.getItemAtPosition(position);
            	Event event = (Event) o;
            	Toast.makeText(UserActivity.this," " + event.getEventName() + " ",Toast.LENGTH_LONG);

            	CustomListAdapter adapter = (CustomListAdapter) lvAssignments.getAdapter();
                
            	Event eventItem = (Event) adapter.getItem(position);
 
                /** The clicked Item in the ListView */
                //RelativeLayout rLayout = (RelativeLayout) item;
 
                /** Getting the toggle button corresponding to the clicked item */

                
                /*pop up box to show that the respective assignment is clicked*/
                //Toast.makeText(getBaseContext(), (String) hm.get("txt") + " : " + strStatus, Toast.LENGTH_SHORT).show();
                /*to send to the next activity*/
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra("event", eventItem);
                startActivity(intent);
            }
        };
        
        /*set the created assignmentList to the listener*/
        lvAssignments.setOnItemClickListener(itemClickListener);
 
       
        // Each row in the list stores country name and its status. populate the list with the items
        //List<HashMap<String,Object>> aList = new ArrayList<HashMap<String,Object>>();        
        
        /*here onwards will be for the sidebar*/
        // Getting an array of country names
        mOptions = getResources().getStringArray(R.array.options);
        mOptions[0] = currentUser.getName();
        
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
	
	private ArrayList getListData(String username){		
		AWSClientManager aws = AWSClientManager.getInstance();
		
		return aws.getAllEvents();//aws.getFilteredEvents(username);
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
	 /*
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
		 */
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

		 private int getUserIcon(Context context, User user){
			 String imageLocation = user.getImageLocation();
			 return context.getResources().getIdentifier(imageLocation.toLowerCase(), "drawable", context.getPackageName());
		 }
}
