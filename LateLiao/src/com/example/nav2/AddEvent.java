package com.example.nav2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;



import java.util.Calendar;
import java.util.GregorianCalendar;

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
import android.widget.Toast;

public class AddEvent extends Activity implements OnDateSetListener, OnTimeSetListener, OnFriendsEventListener  {
	Button dateButton;
	Button timeButton;
	Button friendsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		
		//Set UI for Hiding Soft Keyboard
		setupUI(findViewById(R.id.addEventActivity));
		
		//Set Submit Button Listener
		Button submitButton = (Button)findViewById(R.id.addActivitySubmit);
		submitButton.setOnClickListener(submitButtonListener);
		
		//Set Date Button Listener
		dateButton = (Button)findViewById(R.id.buttonSelectDate);
		dateButton.setOnClickListener(dateButtonListener);
		
		//Set Time Button Listener
		timeButton = (Button)findViewById(R.id.buttonSelectTime);
		timeButton.setOnClickListener(timeButtonListener);
		
		//Set Friends Button Listener
		friendsButton = (Button)findViewById(R.id.buttonSelectFriends);
		friendsButton.setOnClickListener(friendsButtonListener);
	}
	
	private OnClickListener friendsButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			DialogFragment timeFragment = new TimeDialogFragment(AddEvent.this);
			timeFragment.show(ft, "time_dialog");
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
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM, yyyy");
		dateButton.setText(sdf.format(cal.getTime()));
	}		
			
	private OnClickListener submitButtonListener = new OnClickListener() {
	    @Override
		public void onClick(View v) {
			
	    	// do something when the button is clicked
	    	EditText eventName = (EditText) findViewById(R.id.eventName);
	    	String eventNameString = eventName.getText().toString();
	    	
	    	eventName.setVisibility(View.INVISIBLE);


            AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent.this);
            
            builder.setTitle(eventNameString);
            builder.setMessage(eventNameString + " has been submitted");
            
            /**
	    	 * Creating a Dummy Event
	    	 * 
	    	 */
	    	ArrayList<String> attendees = new ArrayList<String>();
	    	attendees.add("Leon Lee");
	    	attendees.add("Melvrick Goh");
	    	attendees.add("Janan Tan");
	    	attendees.add("Wyner Lim");
	    	attendees.add("Ben Gan");
	    	
	    	final Event event = new Event(eventNameString,"date here","time here",attendees,new Location("location details here",1.29757,103.84944));
	    	
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            			
            	@Override
            	public void onClick(DialogInterface dialog, int which){
            		Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            		intent.putExtra("event", event);//eventually we should be dealing with the object separately
            		startActivity(intent);
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
}
