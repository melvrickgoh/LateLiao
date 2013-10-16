package com.example.nav2;

import java.util.Calendar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;

public class TimeDialogFragment extends DialogFragment {
	private OnTimeSetListener setTimeListener;
	
	public TimeDialogFragment(OnTimeSetListener callback){
		setTimeListener = (OnTimeSetListener) callback;
	}
	
	public Dialog onCreateDialog (Bundle savedInstanceState){
		Calendar cal = Calendar.getInstance();
		return new TimePickerDialog(getActivity(),setTimeListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false);
	}
}
