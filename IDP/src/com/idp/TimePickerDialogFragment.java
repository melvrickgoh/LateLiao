package com.idp;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;

public class TimePickerDialogFragment extends DialogFragment{
	private Fragment mFragment;
	private int mHour,mMinute;	
	
	public Dialog onCreateDialog(Fragment callback) {
        // Use the current time as the default values for the picker
		mFragment = callback;
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), (OnTimeSetListener) mFragment, mHour, mMinute,false);
   }
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), (OnTimeSetListener) mFragment, mHour, mMinute,false);
    }
}
