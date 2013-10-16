package com.example.nav2;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.ListView;

public class FriendsDialogFragment extends DialogFragment {
	private OnFriendsEventListener setFriendsListener;
	
	public FriendsDialogFragment(OnFriendsEventListener callback){
		setFriendsListener = (OnFriendsEventListener) callback;
	}
	
	public Dialog onCreateDialog (Bundle savedInstanceState){
		Dialog d = new Dialog(getActivity());
		d.setContentView(R.layout.fragment_friends);
		
		ListView friendsAssignment = (ListView) d.findViewById(R.id.friends_list_view);
		return d;
	}
}
