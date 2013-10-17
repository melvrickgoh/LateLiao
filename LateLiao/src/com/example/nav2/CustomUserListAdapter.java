package com.example.nav2;

import java.io.Console;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
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
		
		User currentUser = (User) listData.get(position);
				
		holder.usernameView.setText(currentUser.getUsername());
		holder.levelView.setText("Level " + String.valueOf(currentUser.getLevel()));
		
		String imageLocation = currentUser.getImageLocation();
				
		int imageID = context.getResources().getIdentifier(imageLocation.toLowerCase(), "drawable", context.getPackageName());
		
		holder.imageView.setImageDrawable(context.getResources().getDrawable(imageID));
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView usernameView;
		TextView levelView;
		TextView currentPointsView;
		ImageView imageView;
	}
	
	private void getListData(){
		ArrayList users = new ArrayList();

		users.add(new User("Leon Lee","leon.png",1,230,new Location("SIS",1.29757,103.84944)));
		users.add(new User("Janan Tan","janan.png",2,231,new Location("SIS",1.29757,103.84944)));
		users.add(new User("Wyner Lim","wyner.png",3,232,new Location("SIS",1.29757,103.84944)));
		users.add(new User("Melvrick Goh","melvrick.png",4,233,new Location("SIS",1.29757,103.84944)));
		users.add(new User("Benjamin","ben.jpg",5,235,new Location("SIS",1.29757,103.84944)));
		users.add(new User("Yeow Leong","lyl.jpg",5,235,new Location("SIS",1.29757,103.84944)));
		
		listData = users;
	}
}
