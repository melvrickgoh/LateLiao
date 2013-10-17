package com.example.nav2;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class FriendsDialogFragment extends DialogFragment {
	private OnFriendsEventListener setFriendsListener;
	private ArrayList userData;
	
	public FriendsDialogFragment(OnFriendsEventListener callback, ArrayList userData){
		setFriendsListener = (OnFriendsEventListener) callback;
		this.userData = userData;
	}
	
	public Dialog onCreateDialog (Bundle savedInstanceState){
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.fragment_friends,null);
		
		final CustomUserListAdapter cula = new CustomUserListAdapter(getActivity(),userData);
		ListView friendsAssignment = (ListView) view.findViewById(R.id.friends_list_view);
		friendsAssignment.setAdapter(cula);
		
		/**
		 * Standard Alert Dialog
		 */
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setPositiveButton(getResources().getString(R.string.add_activity_friend_dialog_positive), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Button friendsButton = (Button) getActivity().findViewById(R.id.buttonSelectFriends);
						ArrayList<User> users = (ArrayList<User>) cula.getSelection();
						Log.d("friendsDialogFragment","users selected size >> " + users.size());
						String usergroups = "";
						for (int i = 0; i<users.size(); i++){
							usergroups+= (users.get(i)).getUsername() + ", ";
						}
						friendsButton.setText(usergroups);
					}
				});
			    //.setNegativeButton(getResources().getString(R.string.add_activity_friend_dialog_negative), null);
		
		builder.setView(view);
		return builder.create();
		
		
		/*Normal Dialog
		Dialog d = new Dialog(getActivity());
		d.setContentView(R.layout.fragment_friends);
		d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		
		ListView friendsAssignment = (ListView) d.findViewById(R.id.friends_list_view);
		friendsAssignment.setAdapter(new CustomUserListAdapter(getActivity(),userData));
		
		return d;*/
		
		/**
		 * Test Multiple Choice Dialog
		 */
		/*
		Dialog d = new MultiChoice(getActivity(),userData,userData);
		d.setContentView(R.layout.fragment_friends);
		d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		
		return d;*/
	}
}
