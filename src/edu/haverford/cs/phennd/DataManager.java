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

import android.util.Log;

public class DataManager {

	private static Date lastUpdate;
	private static List<String> allTags;
	private static List<String> flaggedTags;
	private static List<String> favoriteUIDs;
	private static List<ArticleData> articles;
	private static URL url; 

	private static List<String> getFlaggedTags(){
		return flaggedTags;
	}
	
	public ArticleData getArticle(String url) { // url is the best we can do as far as unique identifiers go
		for (int i = 0; i < articles.size(); i++ ) {
			if (articles.get(i).getUrl().equals(url)) {
				if (articles.get(i).getContent() == "") {
					getArticleContents(articles.get(i));
					
				}
				return articles.get(i);
			}
		}
		return null;
	}
	
	public boolean updateArticles() {
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
				NodeList rss = docEle.getElementsByTagName("rss");
				if (rss.getLength() == 0) {
					Log.i("PHENND","No RSS element.");
					return false;
				}
				else if (rss.getLength() > 1) {
					Log.i("PHENND", "More than one RSS node");
					return false;
				}
				else {
					Node rssNode = rss.item(0);
					for (Node item = rssNode.getFirstChild(); item != null; item = item.getNextSibling() ) {
						if (isNewArticle(item)) {
							articles.add(newArticle(item));
						}
					}
					return true;
				}
			}
		} catch (Exception e) {
			Log.i("PHENND","URL Exception:" + e);
		}
		return false;
	}
	
	public boolean isNewArticle(Node item) {
		String url = extractValue(item, "link");
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getUrl() == url) {
				return false;
			}
		}
		return true;
	}
	
	public ArticleData newArticle(Node item) {
		String pubDate = extractValue(item, "pubDate");
		String description = extractValue(item, "description");
		String url = extractValue(item, "link");
		String title = extractValue(item, "title");
		String creator = extractValue(item, "dc:creator");
		String category = extractValue(item, "category");
		
		return new ArticleData(url, pubDate, description, title, creator, category);
	}
	
	public String extractValue(Node node, String tag) {
		return "Test!"; // TODO: Write
	}
	
	public void getArticleContents(ArticleData article) {
		String contents = "Content!";
		try {
			URL articleUrl = new URL(article.getUrl());
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
		}
		// TODO: acquire content of the article. 
		article.setContent(contents);
		} catch (Exception e) {
		Log.i("PHENND", "Exception getting contents:" + e);
		}
	}

}
