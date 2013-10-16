package com.example.nav2;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class DateDialogFragment extends DialogFragment {
	private OnDateSetListener setDateListener;
	
	public DateDialogFragment(OnDateSetListener callback){
		setDateListener = (OnDateSetListener) callback;
	}
	
	public Dialog onCreateDialog (Bundle savedInstanceState){
		Calendar cal = Calendar.getInstance();
		
		return new DatePickerDialog(getActivity(),setDateListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
	}
}
