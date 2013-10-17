package com.example.nav2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;

@SuppressLint("NewApi")
public class MapActivity extends ActionBarActivity {
	//public static Context appContext;
	
	 //int mPosition = -1;
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
	 //String[] mCount = new String[]{
	 //"4", "", "" };
	 
	 private DrawerLayout mDrawerLayout;
	 private ListView mDrawerList;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private LinearLayout mDrawer ;
	 private List<HashMap<String,String>> mList ;
	 private SimpleAdapter mAdapter;
	 final private String IMAGEICON = "imageicon";
	 final private String TABNAME = "tabname";
	 //final private String COUNT = "count";
	
	 /**
	 * The serialization (saved instance state) Bundle key representing the
	 * current tab position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	 
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
				.setTabListener( new TabListener<MapFragment>(this, "Map", MapFragment.class)));
				
		//For each of the sections in the app, add a tab to the action bar.
	actionBar.addTab(actionBar.newTab().setText(R.string.title_details)
		.setTabListener( new TabListener<DetailsFragment>(this, "Details", DetailsFragment.class)));
	
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
    String[] from = { IMAGEICON, TABNAME };
    
   // Ids of views in listview_layout
    int[] to = { R.id.imageicon , R.id.tabname };
    
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
	
	class TabListener<T extends Fragment> implements ActionBar.TabListener {
   	 
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
     
        /*
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // TODO A	uto-generated method stub
            ft.replace(R.id.container, fragment);
        }
     
        
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            // TODO Auto-generated method stub
            ft.remove(fragment);
        }
     
       
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // TODO Auto-generated method stub
     
        }*/
        @Override
    	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
    		// TODO Auto-generated method stub
    		//Toast.makeText(MapActivity.appContext, "Reselected!", Toast.LENGTH_LONG).show();
    	}
        @Override
    	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
    		// Check if the fragment is already initialized
            if (mFragment == null) {
                // If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
               arg1.add(android.R.id.content, mFragment, mTag);
            } else {
                // If it exists, simply attach it in order to show it
            	arg1.attach(mFragment);
            }
    	}
        @Override
    	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
    		 if (mFragment != null) {
                 // Detach the fragment, because another one is being attached
    			 arg1.detach(mFragment);
             }
    	}
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
        	ActionBar actionBar = getActionBar();
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
	*/
}
		
	
