package edu.haverford.cs.phennd;

import java.util.List;

public class ArticleData {
	final private String pubDate; // Date article published.
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
	
	public ArticleData(String _url, String _pubDate, String _content, String _title, String _creator, String _category) {
		url = _url;
		pubDate = _pubDate;
		content = _content;
		title = _title;
		creator = _creator;
		category = _category;
		event = false;
		eventDate = "";
		eventLocation = "";
	}
	
	public ArticleData(String _url, String _pubDate, String _content, String _title, String _creator, String _category, String _eventDate, String _eventLocation) {
		url = _url;
		pubDate = _pubDate;
		content = _content;
		title = _title;
		creator = _creator;
		category = _category;
		event = true;
		eventDate = _eventDate;
		eventLocation = _eventLocation;
	}
	
	
	public void setFavorited(boolean f) {
		favorited = f;
		if (f) {
			DataManager.addFavorite(this.title);
		}
		else {
			DataManager.removeFavorite(this.title);
		}
	}
	
	public boolean isFavorited() {
		return favorited;
	}
	
	
	public void setTags(String _tags) {
		String[] split = _tags.split(",");
		for (int i = 0; i < split.length; i++) {
			tags.clear();
			tags.add(split[i]);
		}
	}
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	
	public String getUrl() {
		return url;
	}
	
	public String getPubDate() {
		return pubDate;
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
