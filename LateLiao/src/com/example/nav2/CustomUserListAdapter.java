package com.example.nav2;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CustomUserListAdapter extends BaseAdapter {
	
	private ArrayList listData;
	private LayoutInflater layoutInflater;
	private Context context;
	private ArrayList selection;
	
	public CustomUserListAdapter(Context context, ArrayList listData){
		this.listData = listData;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		selection = new ArrayList();
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
	
	public ArrayList getSelection(){
		return (ArrayList) selection;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		Object item = getItem(position);
		boolean selected = selection.contains(item);
		
		if (convertView == null){
			convertView = layoutInflater.inflate(R.layout.friends, null);
			holder = new ViewHolder();
			holder.usernameView = (TextView) convertView.findViewById(R.id.username);
			holder.levelView = (TextView) convertView.findViewById(R.id.user_level);
			holder.imageView = (ImageView) convertView.findViewById(R.id.user_photo);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
			
		}
		
		final User currentUser = (User) listData.get(position);
				
		holder.usernameView.setText(currentUser.getName());
		holder.levelView.setText("Level " + String.valueOf(currentUser.getLevel()));
		
		holder.usernameView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Context cxt = v.getContext();
				Intent intent = new Intent(cxt,ProfileActivity.class);
                intent.putExtra("user", currentUser);
                cxt.startActivity(intent);
			}
			
		});
		
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (selection != null){
					if (selection != null) {
				        if (isChecked && !selection.contains(currentUser))
				          selection.add(currentUser);
				        else if (!isChecked)
				          selection.remove(currentUser);
				      }
					Log.d("customUserListAdapter","user changers > " + selection.size() );
				      notifyDataSetChanged();
				}
			}
			
		});
		
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
		CheckBox checkBox;
	}
	
	public class ChoiceView extends CheckBox implements OnCheckedChangeListener {
		private Object object;
		
		public ChoiceView (Context context, Object object, boolean selected){
			super(context);
			this.object = object;
			setOnCheckedChangeListener(this);
			setItem(object,selected);
		}
		
		public void setItem (Object object, boolean selected){
			this.object = object;
			setChecked(selected);
			setText(object.toString());
		}
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (selection != null){
				if (selection != null) {
			        if (isChecked && !selection.contains(object))
			          selection.add(object);
			        else if (!isChecked)
			          selection.remove(object);
			      }
				Log.d("customUserListAdapter","user changers > " + selection.size() );
			      notifyDataSetChanged();
			}
		}
	}
}
