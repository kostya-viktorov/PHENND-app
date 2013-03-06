package edu.haverford.cs.phennd;

import java.util.List;

public class ArticleData {
	final private String pubDate; // Date article published.
	final private String description; // Brief RSS description (typically a snippet).
	final private String url; // URL of the post.
	final private String title; // Title of the post.
	final private String creator; // Who posted the update?
	
	final private boolean event; // Is this an event? (if yes, the following two may be specified).
	final private String eventDate; // Optional. Event date, if specified.
	final private String eventLocation; // Optional. Event location, if specified.
	private String latLong;
	
	
	final private String category; // The post's category.
	private List<String> tags; // A list of the associated tags.


	private String content; // The actual text of the post goes here.
	private boolean favorited; // Has this been favorited?
	
	public ArticleData(String _url, String _pubDate, String _description, String _title, String _creator, String _category) {
		url = _url;
		pubDate = _pubDate;
		description = _description;
		title = _title;
		creator = _creator;
		category = _category;
		event = false;
		eventDate = "";
		eventLocation = "";
	}
	
	public ArticleData(String _url, String _pubDate, String _description, String _title, String _creator, String _category, String _eventDate, String _eventLocation) {
		url = _url;
		pubDate = _pubDate;
		description = _description;
		title = _title;
		creator = _creator;
		category = _category;
		event = true;
		eventDate = _eventDate;
		eventLocation = _eventLocation;
	}
	
	
	public void setFavorited(boolean f) {
		favorited = f;
	}
	
	public boolean isFavorited() {
		return favorited;
	}
	
	
	public void setTags(String _tags) {
		// Need to write this!
	}
	
	public void setContent(String _content) {
		content = _content;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getPubDate() {
		return pubDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public String getCategory() {
		return category;
	}
	
	public boolean isEvent() {
		return event;
	}
	
	public String getEventLocation() {
		return eventLocation;
	}
	
	public String getEventDate() {
		return eventDate;
	}
	
	public void setEventLatLong(String _latLong) {
		latLong = _latLong;
	}
	
	public String getLatLong() {
		return latLong;
	}
	
	public List<String> getTags() {
		return tags;
	}
		
	public String getContent() {
		return content;
	}
	
}
