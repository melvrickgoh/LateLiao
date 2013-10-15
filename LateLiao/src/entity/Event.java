package entity;

public class Event {
	private String eventName;
	private String eventDate;
	private String eventTime;
	private String eventLocation;
	
	public Event(String eventName, String eventDate, String eventTime,
			String eventLocation) {
		super();
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.eventLocation = eventLocation;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
	public String getEventTime() {
		return eventTime;
	}
	
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	public String getEventLocation() {
		return eventLocation;
	}
	
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
}
