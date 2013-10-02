package com.idp;

import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddActivity extends Fragment implements OnDateSetListener{
	
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
		FrameLayout fLayout = (FrameLayout) inflater.inflate(R.layout.add_activity_fragment_container, container, false);
		//Listening to the different click inputs
		LinearLayout linearMainForm = (LinearLayout) fLayout.findViewById(R.id.AddActivity_MainPage);
		
		//Get references of buttons
		buttonDate = (Button) linearMainForm.findViewById(R.id.buttonSelectDate);
		buttonTime = (Button) linearMainForm.findViewById(R.id.buttonSelectTime);
		buttonSubmit = (Button) linearMainForm.findViewById(R.id.addActivitySubmit);
		buttonSelectLocation = (Button) linearMainForm.findViewById(R.id.buttonSelectLocation);
		buttonSelectFriends = (Button) linearMainForm.findViewById(R.id.buttonSelectFriends);
		
		//Set Click Listener on DateButton
		buttonDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show the Date Picker Dialog
				((FragmentActivity) getActivity()).showDialog(DATE_DIALOG_ID);
			}
		});
		
		//Set Click Listener on TimeButton
		buttonTime.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// Show the Time Picker Dialog
				((FragmentActivity) getActivity()).showDialog(TIME_DIALOG_ID);
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
		
		return fLayout;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
        // Set the Selected Date in Select date Button
        buttonDate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
	}

}