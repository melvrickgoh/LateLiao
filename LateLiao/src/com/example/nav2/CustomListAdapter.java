package com.example.nav2;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
	
	private ArrayList listData;
	private LayoutInflater layoutInflater;
	private Context context;
	
	public CustomListAdapter(Context context, ArrayList listData){
		this.listData = listData;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Typeface agencyRegTF = Typeface.createFromAsset(context.getAssets(),"fonts/AGENCYR.TTF");
		Typeface agencyBoldTF = Typeface.createFromAsset(context.getAssets(), "fonts/AGENCYR.TTF");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.assignments, null);
            holder = new ViewHolder();
            holder.eventDateView = (TextView) convertView.findViewById(R.id.event_date);
            holder.eventTitleView = (TextView) convertView.findViewById(R.id.event_title);
            holder.eventTimeView = (TextView) convertView.findViewById(R.id.event_time);
            holder.eventLocationView = (TextView) convertView.findViewById(R.id.event_location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.eventDateView.setText(((Event) listData.get(position)).getEventDate() + "");
        holder.eventTitleView.setText(((Event) listData.get(position)).getEventName());
        holder.eventTimeView.setText("@ " + ((Event) listData.get(position)).getEventTime());
        holder.eventLocationView.setText(((Event) listData.get(position)).getEventLocationName());
        
        holder.eventDateView.setTypeface(agencyRegTF);
        holder.eventTitleView.setTypeface(agencyBoldTF);
        holder.eventTimeView.setTypeface(agencyBoldTF);
        holder.eventLocationView.setTypeface(agencyRegTF);
        
        return convertView;
	}
	
	static class ViewHolder {
		TextView eventDateView;
		TextView eventTitleView;
		TextView eventTimeView;
		TextView eventLocationView;
	}
}
