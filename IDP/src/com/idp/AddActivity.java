package com.idp;

import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	/*
	 * Inserting my own code for managing the form input data
	 */
	Button buttonDate,buttonTime,buttonSelectLocation,buttonSelectFriends,buttonSubmit;
	
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	
	//variables to save the user selected date and time
	public int year,month,day,hour,minute;
	//Initial variables showing/setting the date and time when the Time and Date picker dialogs 1st appear
	private int mYear,mMonth,mDay,mHour,mMinute;
	
	// Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
       // the callback received when the user "sets" the Date in the DatePickerDialog
       public void onDateSet(DatePicker view, int yearSelected, int monthOfYear, int dayOfMonth) {
          year = yearSelected;
          month = monthOfYear;
          day = dayOfMonth;
          // Set the Selected Date in Select date Button
          buttonDate.setText(day+"/"+month+"/"+year);
       }
    };
    
    // Register  TimePickerDialog listener                 
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
    	// the callback received when the user "sets" the TimePickerDialog in the dialog
        public void onTimeSet(TimePicker view, int hourOfDay, int min) {
            hour = hourOfDay;
            minute = min;
            // Set the Selected Date in Select date Button
            buttonTime.setText("Time selected :"+hour+"-"+minute);
          }
    };
	
	public AddActivity(){
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main_event_add);
		
		setUpActionBar();
		
		//Listening to the different click inputs
		
		//Get references of buttons
		buttonDate = (Button) findViewById(R.id.buttonSelectDate);
		buttonTime = (Button) findViewById(R.id.buttonSelectTime);
		buttonSubmit = (Button) findViewById(R.id.addActivitySubmit);
		buttonSelectLocation = (Button) findViewById(R.id.buttonSelectLocation);
		buttonSelectFriends = (Button) findViewById(R.id.buttonSelectFriends);
		
		//Set Click Listener on DateButton
		buttonDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Show the Date Picker Dialog
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		//Set Click Listener on TimeButton
		buttonTime.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// Show the Time Picker Dialog
				showDialog(TIME_DIALOG_ID);
			}
		});
		
		//Set Submit Listener on SubmitButton
		buttonSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Call Submit Activity
			}
		});
		
		//Set select location for Selecting Location: Call Activity
		buttonSelectLocation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//define a new Intent for Location Selection Activity
				//Intent intent = new Intent(this,PickActivityLocation.class);
		 
				//start the second Activity
				//this.startActivity(intent);
			}
		});
		
		buttonSelectFriends.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Call Friends Activity
				
			}
		});
	}
	
	//Method automatically gets Called when you call the showDialog() method
	protected Dialog onCreateDialog(int id){
		switch(id){
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
			case TIME_DIALOG_ID:
				return new TimePickerDialog(this,mTimeSetListener,mHour,mMinute,false);
		}
		return null;
	}
	
	private void setUpActionBar(){
		// Set up the action bar.
				final ActionBar actionBar = getActionBar();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

				// Create the adapter that will return a fragment for each of the three
				// primary sections of the app.
				mSectionsPagerAdapter = new SectionsPagerAdapter(
						getSupportFragmentManager());

				// Set up the ViewPager with the sections adapter.
				mViewPager = (ViewPager) findViewById(R.id.pager);
				mViewPager.setAdapter(mSectionsPagerAdapter);

				// When swiping between different sections, select the corresponding
				// tab. We can also use ActionBar.Tab#select() to do this if we have
				// a reference to the Tab.
				mViewPager
						.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
							@Override
							public void onPageSelected(int position) {
								actionBar.setSelectedNavigationItem(position);
							}
						});

				// For each of the sections in the app, add a tab to the action bar.
				for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
					// Create a tab with text corresponding to the page title defined by
					// the adapter. Also specify this Activity object, which implements
					// the TabListener interface, as the callback (listener) for when
					// this tab is selected.
					actionBar.addTab(actionBar.newTab()
							.setIcon(getPageIcon(i))
							.setTabListener(this));
				}
	}
	
	// For each tab, get the corresponding icon to be added to each tab.
	private Drawable getPageIcon(int i) {
		switch(i) {
		case 0:
			return getResources().getDrawable(R.drawable.ic_collections_go_to_today);
		case 1:
			return getResources().getDrawable(R.drawable.ic_content_new_event);
		case 2: 
			return getResources().getDrawable(R.drawable.ic_action_user);
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
		
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView;

			TextView dummyTextView;
			
			switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
				case 1:
					rootView = inflater.inflate(R.layout.fragment_main_dummy,
							container, false); 
					dummyTextView = (TextView) rootView
							.findViewById(R.id.section_label);
					
					dummyTextView.setText(R.string.janan);
				case 2:
					//Do nothing since you're on this view
				case 3:
					//Profile layout
					rootView = inflater.inflate(R.layout.fragment_main_dummy,
							container, false);
					dummyTextView = (TextView) rootView
							.findViewById(R.id.section_label);
					
					dummyTextView.setText(Integer.toString(getArguments().getInt(
							ARG_SECTION_NUMBER)));
				default:
					rootView = inflater.inflate(R.layout.fragment_main_dummy,
							container, false);
					dummyTextView = (TextView) rootView
							.findViewById(R.id.section_label);
					
					dummyTextView.setText(Integer.toString(getArguments().getInt(
							ARG_SECTION_NUMBER)));
			}
			
			return rootView;
		}
	}

}
