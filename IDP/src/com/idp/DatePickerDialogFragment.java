package com.idp;

import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

@SuppressLint("ValidFragment")
public class DatePickerDialogFragment extends DialogFragment{
	private Fragment mFragment;
	private int mYear,mMonth,mDay;

    @SuppressLint("ValidFragment")
	public DatePickerDialogFragment(Fragment callback) {
        mFragment = callback;
        final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
    }

     public Dialog onCreateDialog(Bundle savedInstanceState) {
         return new DatePickerDialog(getActivity(), (OnDateSetListener) mFragment, mYear, mMonth, mDay);
     }
}
