package com.example.nav2;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;

public class FriendsDialogFragment extends DialogFragment {
	private OnFriendsEventListener setFriendsListener;
	private ArrayList userData;
	
	public FriendsDialogFragment(OnFriendsEventListener callback, ArrayList userData){
		setFriendsListener = (OnFriendsEventListener) callback;
		this.userData = userData;
	}
	
	public Dialog onCreateDialog (Bundle savedInstanceState){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dialog d = new Dialog(getActivity());
		d.setContentView(R.layout.fragment_friends);
		d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		
		ListView friendsAssignment = (ListView) d.findViewById(R.id.friends_list_view);
		friendsAssignment.setAdapter(new CustomUserListAdapter(getActivity(),userData));
		
		return d;
	}
}
