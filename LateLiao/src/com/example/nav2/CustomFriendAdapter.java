package com.example.nav2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomFriendAdapter extends BaseAdapter {
	
	private ArrayList listData;
	private LayoutInflater layoutInflater;
	private Context context;
	private User user;
	
	public CustomFriendAdapter(Context context, ArrayList listData, User user){
		this.listData = listData;
		this.context = context;
		this.user = user;
		layoutInflater = LayoutInflater.from(context);
	}
	
	public void remove(Object position){
		listData.remove(position);
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
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.friendImage = (ImageView) convertView.findViewById(R.id.friend_image);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
            holder.friendName = (TextView) convertView.findViewById(R.id.friend_name);
            holder.friendLevel = (TextView) convertView.findViewById(R.id.friend_level);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        User profileUser = (User) listData.get(position);
        
        holder.friendImage.setImageResource(getUserIcon(context, profileUser));
        holder.arrow.setImageResource(R.drawable.arrow);
        holder.friendName.setText(profileUser.getName());
        holder.friendLevel.setText("Level " + String.valueOf(profileUser.getLevel()));
        /*
        holder.eventDateView.setTypeface(agencyRegTF);
        holder.eventTitleView.setTypeface(agencyBoldTF);
        holder.eventTimeView.setTypeface(agencyBoldTF);
        holder.eventLocationView.setTypeface(agencyRegTF);
        */
        return convertView;
	}
	
	static class ViewHolder {
		ImageView friendImage;
		ImageView arrow;
		TextView friendName;
		TextView friendLevel;
	}
	
	 private int getUserIcon(Context context, User user){
		 String imageLocation = user.getImageLocation();
		 return context.getResources().getIdentifier(imageLocation.toLowerCase(), "drawable", context.getPackageName());
	 }
}
