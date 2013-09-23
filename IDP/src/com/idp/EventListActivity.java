package com.idp;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
 
public class EventListActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        // storing string resources into Array
        String[] events = getResources().getStringArray(R.array.events);
         
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.fragment_main_event_list, R.id.label, events));
         
    }
}
