package com.example.nav2;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomUserListAdapter extends BaseAdapter {
	
	private ArrayList listData;
	private LayoutInflater layoutInflater;
	private Context context;
	
	public CustomUserListAdapter(Context context, ArrayList listData){
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null){
			convertView = layoutInflater.inflate(R.layout.friends, null);
			holder = new ViewHolder();
			holder.usernameView = (TextView) convertView.findViewById(R.id.username);
			holder.levelView = (TextView) convertView.findViewById(R.id.user_level);
			holder.imageView = (ImageView) convertView.findViewById(R.id.user_photo);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.usernameView.setText(((User) listData.get(position)).getUsername());
		holder.levelView.setText(((User) listData.get(position)).getLevel());
		
		String imageLocation = ((User) listData.get(position)).getImageLocation();
				
		int imageID = context.getResources().getIdentifier("com.example.nav2:drawable/" + imageLocation, null, null);
		holder.imageView.setImageResource(imageID);
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView usernameView;
		TextView levelView;
		TextView currentPointsView;
		ImageView imageView;
	}
}
