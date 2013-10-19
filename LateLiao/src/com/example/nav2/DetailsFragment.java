package com.example.nav2;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Retrieve values from UserActivity.class
		String strEventName = getActivity().getIntent().getStringExtra("eventName");
		Location strEventLocation = (Location) getActivity().getIntent().getParcelableExtra("eventLocation");
		String strEventDate = getActivity().getIntent().getStringExtra("eventDate");
		String strEventTime = getActivity().getIntent().getStringExtra("eventTime");
		ArrayList<String> eventAttendees = getActivity().getIntent().getStringArrayListExtra("eventAttendees");
		
		// Inflate event detail fragment onto tab 2
		View rootView = (RelativeLayout) inflater.inflate(R.layout.activity_details_fragment, container, false);
		
		// Define usage of fonts
		Typeface agencyRegTF = Typeface.createFromAsset(getActivity().getAssets(),"fonts/AGENCYR.TTF");
		Typeface agencyBoldTF = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AGENCYR.TTF");
		
		// Populate name of the event
		EditText eventName = (EditText) rootView.findViewById(R.id.eventName);
		TextView eventName_edit = (TextView) rootView.findViewById(R.id.eventName_edit);
		
		eventName.setTypeface(agencyBoldTF);
		eventName_edit.setTypeface(agencyRegTF);
		eventName_edit.setText(strEventName);
		
		// Populate location of the event
		EditText location = (EditText) rootView.findViewById(R.id.location);
		TextView location_edit = (TextView) rootView.findViewById(R.id.location_edit);
		
		location.setTypeface(agencyBoldTF);
		location_edit.setTypeface(agencyRegTF);
		location_edit.setText(strEventLocation.getLocationName());
		
		// Populate date and time of the event
		TextView dateTime = (TextView) rootView.findViewById(R.id.from);
		EditText date = (EditText) rootView.findViewById(R.id.date_from);
		EditText time = (EditText) rootView.findViewById(R.id.time_from);
		
		dateTime.setTypeface(agencyBoldTF);
		date.setTypeface(agencyRegTF);
		date.setText(strEventDate);
		time.setTypeface(agencyRegTF);
		time.setText(strEventTime);
		
		// Populate list of friends
		TextView friendTitle = (TextView) rootView.findViewById(R.id.friends);
		friendTitle.setTypeface(agencyBoldTF);
		
		ListView lv = (ListView) rootView.findViewById(R.id.friendList);

        // This is the array adapter, it takes the context of the activity as a first // parameter, the type of list view as a second parameter and your array as a third parameter
        CustomAdapter arrayAdapter = new CustomAdapter(getActivity(), android.R.layout.simple_list_item_1, eventAttendees, "fonts/AGENCYR.TTF");
        lv.setAdapter(arrayAdapter); 
        
		return rootView;

	}
	
	class CustomAdapter extends ArrayAdapter<String>{

	    Context context; 
	    int layoutResourceId;    
	    ArrayList<String> data = new ArrayList<String>();
	    Typeface tf; 

		public CustomAdapter(Context context, int layoutResourceId, ArrayList<String> data, String FONT ) { 
		    super(context, layoutResourceId, data);
		    this.layoutResourceId = layoutResourceId;
		    this.context = context;
		    this.data = data;
		    tf = Typeface.createFromAsset(context.getAssets(), FONT);
		}  
		
		@Override
	    public View getView(final int position, View convertView, final ViewGroup parent)
	    {
			Typeface agencyRegTF = Typeface.createFromAsset(context.getAssets(),"fonts/AGENCYR.TTF");
			View view =super.getView(position, convertView, parent);

            TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
            textView.setTypeface(agencyRegTF);;

            return view;
	    }
	}
}
