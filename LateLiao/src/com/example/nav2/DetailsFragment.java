package com.example.nav2;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = (RelativeLayout) inflater.inflate(R.layout.activity_details_fragment, container, false);
		
		Typeface agencyRegTF = Typeface.createFromAsset(getActivity().getAssets(),"fonts/AGENCYR.TTF");
		Typeface agencyBoldTF = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AGENCYR.TTF");
		
		EditText eventName = (EditText) rootView.findViewById(R.id.eventName);
		TextView eventName_edit = (TextView) rootView.findViewById(R.id.eventName_edit);
		
		eventName.setTypeface(agencyBoldTF);
		eventName_edit.setTypeface(agencyRegTF);
		eventName_edit.setText("IDP Meeting");
		
		EditText location = (EditText) rootView.findViewById(R.id.location);
		TextView location_edit = (TextView) rootView.findViewById(R.id.location_edit);
		
		location.setTypeface(agencyBoldTF);
		location_edit.setTypeface(agencyRegTF);
		location_edit.setText("SIS GSR 2.1");
		
		TextView dateTime = (TextView) rootView.findViewById(R.id.from);
		EditText date = (EditText) rootView.findViewById(R.id.date_from);
		EditText time = (EditText) rootView.findViewById(R.id.time_from);
		
		dateTime.setTypeface(agencyBoldTF);
		date.setTypeface(agencyRegTF);
		date.setText("Thur, 10 Oct 2013");
		time.setTypeface(agencyRegTF);
		time.setText("10:00");
		
		TextView friendTitle = (TextView) rootView.findViewById(R.id.friends);
		friendTitle.setTypeface(agencyBoldTF);
		
		ListView lv = (ListView) rootView.findViewById(R.id.friendList);
        // Instanciating an array list (you don't need to do this, you already have yours)
        ArrayList<String> nameList = new ArrayList<String>();
        nameList.add("Wyner Lim");
        nameList.add("Melvrick");
        nameList.add("Janan");
        // This is the array adapter, it takes the context of the activity as a first // parameter, the type of list view as a second parameter and your array as a third parameter
        CustomAdapter arrayAdapter = new CustomAdapter(getActivity(), android.R.layout.simple_list_item_1, nameList, "fonts/AGENCYR.TTF");
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
