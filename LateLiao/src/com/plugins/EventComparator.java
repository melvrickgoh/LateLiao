package com.plugins;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

import com.example.nav2.Event;

public class EventComparator implements Comparator<Event> {

	@Override
	public int compare(Event e1, Event e2) {
		Calendar e1Cal = getEventTime(e1);
		Calendar e2Cal = getEventTime(e2);
		
		return e1Cal.compareTo(e2Cal);
	}

	private Calendar getEventTime(Event e) {
		int date = e.getEventDate();
		int month = e.getEventMonth();
		int year = e.getEventYear();
		int time = Integer.parseInt(e.getEventTime());
		int hour = time/100;
		int minute = time%100;
		
		//GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute)
		return new GregorianCalendar(year,month,date,hour,minute);
	}

}
