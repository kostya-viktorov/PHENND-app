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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	private static PHENNDDbOpenHelper phenndDB;

	
	public static List<String> getFlaggedTags(){
		flaggedTags.add("Test");
		return flaggedTags;
	}

	public static List<String> getFavorites(){
		return favoriteNames;
	}

	
	private static String dbClean(String toClean) { // Figure out how to use URLs in a database correctly
		return toClean;
	}
	public static ArticleData getArticle(String title) { // url is the best we can do as far as unique identifiers go
		
		for (int i = 0; i < articles.size(); i++ ) {
			if (articles.get(i).getTitle().equals(title)) {
				return articles.get(i);
			}
		}
		SQLiteDatabase db = phenndDB.getReadableDatabase();
		String[] results_columns = new String[] { 
				phenndDB.COL_URL, phenndDB.COL_PUBDATE, phenndDB.COL_CONTENTS, phenndDB.COL_TITLE,
				phenndDB.COL_CREATOR, phenndDB.COL_CATEGORY, phenndDB.COL_EVENTDATE, 
				phenndDB.COL_EVENTLOCATION, phenndDB.COL_FAVORITED};
		String where = phenndDB.COL_TITLE + "=" + dbClean(title);
		String whereArgs[] = null;
		String groupBy = null;
		String having = null;
		String order = null;
		
		Cursor cursor = db.query(phenndDB.DATABASE_TABLE, results_columns, where, whereArgs, groupBy, having, order);
		if (cursor.getCount() != 1) { return null; }
		else {
			ArticleData article;
			cursor.moveToFirst();
			String _url = extract(cursor, phenndDB.COL_URL);
			String _pubDate = extract(cursor, phenndDB.COL_PUBDATE);
			String _contents = extract(cursor, phenndDB.COL_CONTENTS);
			String _title = extract(cursor, phenndDB.COL_TITLE);
			String _creator = extract(cursor, phenndDB.COL_CREATOR);
			String _category = extract(cursor, phenndDB.COL_CATEGORY);
			String _eventdate = extract(cursor, phenndDB.COL_EVENTDATE);
			String _eventlocation = extract(cursor, phenndDB.COL_EVENTLOCATION);

			article = new ArticleData(_url, _pubDate, _contents, _title, _creator, _category, _eventdate, _eventlocation);
			articles.add(article);
			return article;
		}
	}
	
	private static String extract(Cursor cursor, String col) {
		int idx = cursor.getColumnIndex(col); 
		if ( idx > -1) {
			return cursor.getString(idx);
		}
		else {
			return "";
		}
	}
	
	public DataManager(Context context) {
		phenndDB = new PHENNDDbOpenHelper(context, PHENNDDbOpenHelper.DATABASE_NAME, null, PHENNDDbOpenHelper.DATABASE_VERSION);
	}
	
	public static boolean updateArticles() {

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
							if (isNewArticle(item)) {
								articles.add(newArticle(item));
								//TODO: add to DB
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
	
	public static boolean isNewArticle(Node item) {
		//TODO: Lookup in DB
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
		String creator = extractValue(e, "dc:creator");
		String category = extractValue(e, "category");
		/*Log.d("PHENND","pubDate: " + pubDate);
		Log.d("PHENND", "contents: " + contents);
		Log.d("PHENND", "url: " + url);
		Log.d("PHENND", "title: " + title);
		Log.d("PHENND", "creator: " + creator);
		Log.d("PHENND", "category: " + category);*/
		//TODO: Insert into DB
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
		//TODO: lookup in DB
		List<String> titles = new ArrayList<String>();
		for (int i = 0; i < articles.size(); i++) {
			titles.add(articles.get(i).getTitle());
		}
		return titles; // Should return a list of article titles, based on articles with the given tag
	}
	
	public static List<String> getArticleTitlesForCategory(String categoryName)
	{
		//TODO: lookup in DB
		List<String> catTitles = new ArrayList<String>();
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getCategory().equals(categoryName)) {
				catTitles.add(articles.get(i).getTitle());
			}
		}
		return catTitles; // Should return a list of article titles, based on articles with the given tag
	}
	public static void addFavorite(String title) {
		favoriteNames.add(title);
		//TODO: add to DB
	}
	public static void removeFavorite(String title) {
		favoriteNames.remove(favoriteNames.indexOf(title));
		//TODO: add to DB
	}
}