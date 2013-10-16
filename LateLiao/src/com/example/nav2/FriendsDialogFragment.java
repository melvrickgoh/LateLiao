package com.example.nav2;

import android.app.DialogFragment;

public class FriendsDialogFragment extends DialogFragment {
	private OnFriendsEventListener setFriendsListener;
	
	private FriendsDialogFragment(OnFriendsEventListener callback){
		setFriendsListener = (OnFriendsEventListener) callback;
	}
	
	
}
