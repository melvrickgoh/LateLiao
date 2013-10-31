package com.example.nav2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

@SuppressLint("NewApi")
public class MapActivity extends ActionBarActivity {
	 private EasyTracker tracker;
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
	 private User currentUser;
	 final private String IMAGEICON = "imageicon";
	 final private String TABNAME = "tabname";
	
	 /**
	 * The serialization (saved instance state) Bundle key representing the
	 * current tab position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		/* Action bar creation */
		ActionBar actionBar = showActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.title_maps)
				.setTabListener( new TabListener<MapFragment>(this, "Map", MapFragment.class)));
				
		actionBar.addTab(actionBar.newTab().setText(R.string.title_details)
			.setTabListener( new TabListener<DetailsFragment>(this, "Details", DetailsFragment.class)));
		
		currentUser = (User) getIntent().getParcelableExtra("user");
		
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
		
	}
	
	private class TabListener<T extends Fragment> implements ActionBar.TabListener {
   	 
    	private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

     
        public TabListener(Activity activity, String tag, Class<T> clz) {
            // TODO Auto-generated constructor stub
        	mActivity = activity;
            mTag = tag;
            mClass = clz;
        }

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			// Check if the fragment is already initialized
            if (mFragment == null) {
            	submitTrackerMessage("Map Activity","Fragment " + mClass.getName() + " selected!","fragment tabbing",null);
    			// If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
   			 	arg1.replace(R.id.container, mFragment);
            } else {
                // If it exists, simply attach it in order to show it
            	submitTrackerMessage("Map Activity","Fragment Tag " + mFragment.getTag() + " selected!","fragment tabbing",null);
    			arg1.replace(R.id.container, mFragment);
            }
			
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			 if (mFragment != null) {
                 // Detach the fragment, because another one is being attached
				 arg1.replace(R.id.container, mFragment);
             }
		}
    }
	
	private OnItemClickListener drawerListener = new OnItemClickListener() {
	    
	       @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	        
	    	   if (position == 0) {
	    		    submitTrackerMessage("Map Activity","Drawer: Select Profile","Go to Profile Activity",null);
	    	   		Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
	                intent.putExtra("user", currentUser);
	                startActivity(intent);
	    	   		
	    	   	}
	    	   	else if (position == 1) {
	    	   		submitTrackerMessage("Map Activity","Drawer: Select Add Event","Go to Add Event",null);
	    	   		Intent intent = new Intent(getApplicationContext(),AddEvent.class);
	    	   		intent.putExtra("user", currentUser);
	                startActivity(intent);
	    	   		//do something
	    	   	} else if (position == 2) {
	    	   		submitTrackerMessage("Map Activity","Drawer: View Friends","Go to View Friends",null);
	    	   		Intent intent = new Intent(getApplicationContext(),FriendsActivity.class);
	    	   		intent.putExtra("user", currentUser);
	                startActivity(intent);
	    	   		//do something
	    	   	} else {	
	    	   		submitTrackerMessage("Map Activity","Drawer: Main Activity","Go to Main Activity",null);
	    	   		Intent intent = new Intent(getApplicationContext(),MainActivity.class);
		        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(intent);
	    	   	}
	        
		        // Closing the drawer
		        mDrawerLayout.closeDrawer(mDrawer);
	       }
	};
	
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
	
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
		 super.onPostCreate(savedInstanceState);
		 mDrawerToggle.syncState();
	 
	 }
	 
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
		
	
