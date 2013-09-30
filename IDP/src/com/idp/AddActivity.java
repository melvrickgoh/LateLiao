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

public class AddActivity extends Fragment {
	
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
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.add_activity_fragment_container, container, false);
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

}