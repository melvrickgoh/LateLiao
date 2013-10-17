package com.example.nav2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MultiChoiceFriendsListAdapter extends BaseAdapter{
	
	private Context context;
	private Collection options;
	private Collection selection;
	private List filteredOptions;
	
	public MultiChoiceFriendsListAdapter (Context context, Collection options, Collection selection){
		this.context = context;
		this.options = options;
		this.selection = selection;
		this.filteredOptions = new ArrayList<User>(options.size());
		setFilter(null);
	}
	
	@Override
	public int getCount() {
		return filteredOptions.size();
	}

	@Override
	public Object getItem(int position) {
		return filteredOptions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    ChoiceView view;
	    Object item = getItem(position);
	    boolean selected = selection.contains(item);
	    if (convertView == null) {
	      view = new ChoiceView(context, item, selected);
	    } else {
	      view = (ChoiceView) convertView;
	      view.setItem(item, selected);
	    }
	    return view;
	  }
	
	public void setFilter(String filter) {
	    if (filter != null)
	      filter = filter.toLowerCase();

	    filteredOptions.clear();
	    for (Object item : selection){
	    	filteredOptions.add(item);
	    	Log.d("multiadapter","item > " + item + " ,selection > " + selection);
	    }
	    for (Object item : options)
	      if (!selection.contains(item) && (filter == null || item.toString().toLowerCase().contains(filter)))
	        filteredOptions.add(item);
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
			      notifyDataSetChanged();
			}
		}

	}
}
