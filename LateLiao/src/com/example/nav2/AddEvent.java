package com.example.nav2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.aws.AWSClientManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddEvent extends Activity implements OnDateSetListener, OnTimeSetListener, OnFriendsEventListener  {
	Button dateButton;
	Button timeButton;
	Button locationButton;
	Button friendsButton;
	
	private static User currentUser;
	
	private int eventDate = 0;
	private int eventMonth = 0;
	private int eventYear = 0;
	private static Bundle savedInstanceState = new Bundle();
	private String eventTime = "";
	private final Location eventLocation = new Location("", 0, 0);
	private final ArrayList<String> eventFriends = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(this.savedInstanceState);
		setContentView(R.layout.activity_add_event);
		
		Bundle intentBundle = getIntent().getExtras();
		this.currentUser = (User) intentBundle.get("user");
		this.savedInstanceState.putParcelable("user", this.currentUser);
		
		setupUI(findViewById(R.id.addEventActivity));
		Log.d("add event","saved instance state > " + savedInstanceState + "eventlocation > " + this.eventLocation + "eventFriends > " + eventFriends);
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
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
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
            	}
            	
            	public void sendToDatabase(Event event){
            		AWSClientManager aws = new AWSClientManager().getInstance();
            		aws.addNewEvent(event);
            	}
            });
            builder.show();
            eventName.setVisibility(View.VISIBLE);
	    }
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
		return true;
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private ArrayList getListData(){
		AWSClientManager aws = AWSClientManager.getInstance();		
		return aws.getAllUsers();
	}
}