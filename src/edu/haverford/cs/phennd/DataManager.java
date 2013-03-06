package edu.haverford.cs.phennd;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import android.util.Log;
/* test */
public class DataManager {
 
	private static Date lastUpdate;
	private static List<String> allTags = new ArrayList<String>();
	private static List<String> flaggedTags = new ArrayList<String>();
	private static List<String> favoriteUIDs = new ArrayList<String>();
	private static List<ArticleData> articles = new ArrayList<ArticleData>();
	private static List<String> favoriteNames = new ArrayList();
	private static URL url; 

	/* BAM! A little bit of that Spice Weasel. */
	
	public static List<String> getFlaggedTags(){
		flaggedTags.add("Test");
		return flaggedTags;
	}

	public static List<String> getFavorites(){
		return favoriteNames;
	}

	
	public static ArticleData getArticle(String title) { // url is the best we can do as far as unique identifiers go
		for (int i = 0; i < articles.size(); i++ ) {
			if (articles.get(i).getTitle().equals(title)) {
				return articles.get(i);
			}
		}
		return null;
	}
	
	public static boolean updateArticles() {
		// http://www.androidhive.info/2011/11/android-xml-parsing-tutorial/
		// TODO: Write method to update list of articles.

		try {
			url = new URL("http://updates.phennd.org/feed");
			URLConnection connection = url.openConnection();
			
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				
				// Parse the weather feed.
				Document dom = db.parse(in);
				Element docEle = dom.getDocumentElement();
				NodeList rss = docEle.getElementsByTagName("item");
				if (rss.getLength() == 0) {
					Log.i("PHENND","No RSS element.");
					return false;
				}
				else {
					for (int i = 0; i < rss.getLength(); i++) {
						Node item = rss.item(i);
					//		if (isNewArticle(item)) {
								articles.add(newArticle(item));
						//	}
					}
					return true;
				}
			}
		} catch (Exception e) {
			Log.i("PHENND","URL Exception:" + e);
		}
		return false;
	}
	
	public static boolean isNewArticle(Node item) {
		String url = extractValue((Element)item, "link");
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getUrl() == url) {
				return false;
			}
		}
		return true;
	}
	
	public static ArticleData newArticle(Node item) {
		Element e = (Element)item;
		String pubDate = extractValue(e, "pubDate");
		String contents = extractValue(e, "description");
		String url = extractValue(e, "link");
		String title = extractValue(e, "title");
		favoriteNames.add(title);
		String creator = extractValue(e, "dc:creator");
		String category = extractValue(e, "category");
		/*Log.d("PHENND","pubDate: " + pubDate);
		Log.d("PHENND", "contents: " + contents);
		Log.d("PHENND", "url: " + url);
		Log.d("PHENND", "title: " + title);
		Log.d("PHENND", "creator: " + creator);
		Log.d("PHENND", "category: " + category);*/
		return new ArticleData(url, pubDate, contents, title, creator, category);
	}
	
	public static String extractValue(Element e, String tag) {
		NodeList matches = e.getElementsByTagName(tag);
		if (matches.getLength() != 1)  { return "Err!!!"; }
		return matches.item(0).getTextContent();
	}


	// Methods which need to be added for listviews:
	
	public static List<String> getArticleTitlesForTag(String tagName)
	{
		List<String> titles = new ArrayList<String>();
		for (int i = 0; i < articles.size(); i++) {
			titles.add(articles.get(i).getTitle());
		}
		return titles; // Should return a list of article titles, based on articles with the given tag
	}
	
	public static List<String> getArticleTitlesForCategory(String categoryName)
	{
		List<String> catTitles = new ArrayList<String>();
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getCategory().equals(categoryName)) {
				catTitles.add(articles.get(i).getTitle());
			}
		}
		return catTitles; // Should return a list of article titles, based on articles with the given tag
	}
}