package com.idp;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

    public class EventViewAdapter extends ArrayAdapter<String> {

        public EventViewAdapter (Context c, int textViewResource, List<String> events) {
            super(c, R.layout.fragment_main_event_list, events);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout row = (RelativeLayout)convertView;
            ListView_Text holder = null;

            if (row == null)
            {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                row = (RelativeLayout)inflater.inflate(R.layout.fragment_main_event_list, parent, false);
                holder = new ListView_Text(row);
                row.setTag(holder);
            }
            else
            {
                holder = (ListView_Text) row.getTag();
            }

            ImageView eventImage = (ImageView)row.findViewById(R.id.event_image);
            TextView eventName = (TextView)row.findViewById(R.id.event_name);
            TextView eventAbout = (TextView)row.findViewById(R.id.event_about);
            ProgressBar indicator = (ProgressBar)row.findViewById(R.id.progress);

            indicator.setVisibility(View.VISIBLE);
            eventImage.setVisibility(View.INVISIBLE);
            
            
            holder.populateFrom(getItem(position));

            return row;
        }

        static class ListView_Text {
            private TextView cell_name = null;

            ListView_Text(View row) {
                cell_name = (TextView) row.findViewById(R.id.eventListView);
            }

            void populateFrom(String index) {
                cell_name.setText(index);
            }

        }
    }