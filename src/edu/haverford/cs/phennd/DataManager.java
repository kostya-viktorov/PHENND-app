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

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/* test */
public class DataManager {
	private static Date lastUpdate = new Date(0);
	private static List<String> allTags = new ArrayList<String>();
	private static List<String> flaggedTags = new ArrayList<String>();
	private static List<String> favoriteUIDs = new ArrayList<String>();
	private static List<ArticleData> articles = new ArrayList<ArticleData>();
	private static List<String> favoriteNames = new ArrayList<String>();
	private static String[] categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
	private static String[] tags = {"Education","Health","Environment","Service-learning","Higher Education","Arts","Nonprofit","Nutrition","Poverty","Civic Engagement","Community Service/Volunteer","Technology","AmeriCorps","Community Development","West","North","Northeast","Northwest","South","Center City","New Jersey","Older adult","Youth","Women","LGBT","Immigrant"};
	private static URL url; 
	private static PHENNDDbOpenHelper phenndDB;
	private static DataManager self = null;
	private static int updatedCount = 0;
	private static List<String> updatedTitles = new ArrayList<String>();
	private static Context appContext;
	
	public static DataManager getDataManager(Context context) {
		if (self == null) {
			self = new DataManager(context);
		}
		return self;
	}
	
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
		// Check if it's preloaded
		// TODO: refactor everything to use URL as the argument here
		for (int i = 0; i < articles.size(); i++ ) {
			if (articles.get(i).getTitle().equals(title)) {
				return articles.get(i);
			}
		}
		
		// Else, pull it from the DB.
		
		
		SQLiteDatabase db = phenndDB.getReadableDatabase();
		String[] results_columns = new String[] { 
				PHENNDDbOpenHelper.COL_URL, PHENNDDbOpenHelper.COL_PUBDATE, PHENNDDbOpenHelper.COL_CONTENTS, PHENNDDbOpenHelper.COL_TITLE,
				PHENNDDbOpenHelper.COL_CREATOR, PHENNDDbOpenHelper.COL_CATEGORY, PHENNDDbOpenHelper.COL_EVENTDATE, 
				PHENNDDbOpenHelper.COL_EVENTLOCATION, PHENNDDbOpenHelper.COL_FAVORITED};
		
		String where = PHENNDDbOpenHelper.COL_TITLE + "=" + dbClean(title);
		String whereArgs[] = null;
		String groupBy = null;
		String having = null;
		String order = null;
		
		Cursor cursor = db.query(PHENNDDbOpenHelper.DATABASE_TABLE, results_columns, where, whereArgs, groupBy, having, order);
		if (cursor.getCount() != 1) { return null;  } // No article in DB  
		else { // Build and cache in memory
			ArticleData article;
			cursor.moveToFirst();
			String _url = extract(cursor, PHENNDDbOpenHelper.COL_URL);
			String _pubDate = extract(cursor, PHENNDDbOpenHelper.COL_PUBDATE);
			String _contents = extract(cursor, PHENNDDbOpenHelper.COL_CONTENTS);
			String _title = extract(cursor, PHENNDDbOpenHelper.COL_TITLE);
			String _creator = extract(cursor, PHENNDDbOpenHelper.COL_CREATOR);
			String _category = extract(cursor, PHENNDDbOpenHelper.COL_CATEGORY);
			String _eventdate = extract(cursor, PHENNDDbOpenHelper.COL_EVENTDATE);
			String _eventlocation = extract(cursor, PHENNDDbOpenHelper.COL_EVENTLOCATION);

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
		DataManager.appContext = context;
		phenndDB = new PHENNDDbOpenHelper(context, PHENNDDbOpenHelper.DATABASE_NAME, null, PHENNDDbOpenHelper.DATABASE_VERSION);
	}
	
	public static void updateCategories() {
		NodeList catsXml =  pullData("http://walnut.fig.haverford.edu/phennd/categories.xml");
		List<String> newCats = new ArrayList<String>();
		if (catsXml!= null && catsXml.getLength() != 0) {
			for (int i = 0; i < catsXml.getLength(); i++) {
				if (!has(categories, catsXml.item(i).getTextContent())) {
					newCats.add(catsXml.item(i).getTextContent());
				}
			}
			for (int i = 0; i < categories.length; i++) {
				newCats.add(categories[i]);
			}
			categories = (String[]) newCats.toArray();
		}
	}
	
	public static void updateTags() {
		NodeList tagsXml = pullData("http://walnut.fig.haverford.edu/phennd/tags.xml");
		List<String> newTags = new ArrayList<String>();
		if (tagsXml != null && tagsXml.getLength() != 0) {
			for (int i = 0; i < tagsXml.getLength(); i++) {
				if (!has(tags, tagsXml.item(i).getTextContent())) {
					newTags.add(tagsXml.item(i).getTextContent());
				}	
			}
			for (int i = 0; i < tags.length; i++) {
				newTags.add(tags[i]);
			}
			tags = (String[]) newTags.toArray();
		}
	}

	public static NodeList pullData() {
		return pullData("http://updates.phennd.org/feed");
	}
	public static NodeList pullData(String target) {
		
		try {
			url = new URL(target);
			URLConnection connection = url.openConnection();
			
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				
				// Parse the feed.
				Document dom = db.parse(in);
				Element docEle = dom.getDocumentElement();
				NodeList rss = docEle.getElementsByTagName("item");
				lastUpdate = new Date();
				return rss;
			}
		}
		catch (Exception e) {
			Log.i("PHENND","URL Exception:" + e);
		}
		return null;
	}
		

	public static boolean buildArticles(NodeList rss) {
		if (rss == null) {
			return false;
		}
		if (rss.getLength() == 0) {
			Log.i("PHENND","No RSS element.");
			return false;
		}
		else {
			boolean changed = false;
			for (int i = 0; i < rss.getLength(); i++) {
				Node item = rss.item(i);
					if (isNewArticle(item)) {

						ArticleData article =newArticle(item);
						if (article.getTitle() != "") {
							changed = true;
							updatedCount++;
							updatedTitles.add(article.getTitle());
							articles.add(article);
						}
					}
			}
			
			NotificationManager notificationManager;
			notificationManager = (NotificationManager)appContext.getSystemService(Context.NOTIFICATION_SERVICE);
			int icon = R.drawable.ic_launcher;
			Integer countUpdated = Integer.valueOf(updatedCount);
			String tickerText = "New PHENND Update Articles";
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, tickerText, when); // This is deprecated, but the alternative isn't available in most of the API versions we target
			notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
			// MAKE A PENDING INTENT AND PASS IT IN HERE
			notification.setLatestEventInfo(appContext, "PHENND Update", countUpdated.toString() + " New Articles Posted", null);
			notificationManager.notify(1, notification);
			return changed;
		}
	}
	
	public static boolean updateArticles() {

				NodeList rss = pullData();
				updateCategories();
				updateTags();
				return buildArticles(rss);
	}
	
	public static boolean isNewArticle(Node item) {
		Element e = (Element)item;
		String title = extractValue(e, "title");
		
		if (getArticle(title) == null ) {
			return false;
		}		
		return true;
	}
	
	private static boolean has(String[] space, String target) {
		for (int k = 0; k < space.length; k++) {
			if ( space[k] == target) {
				return true;
			}
		}
		return false;
			
	}
	
	public static String getCategory(List<String> potentials) {
		for (int i = 0; i < potentials.size(); i++) {
			if ( has(categories, potentials.get(i)) ) {
				return potentials.get(i);
			}
		}
		return "";
	}
	public static List<String> getTags(List<String> potentials) {
		List<String> results = new ArrayList<String>();
		for (int i = 0; i < potentials.size(); i ++) {
			if ( has(tags, potentials.get(i))) {
				results.add(potentials.get(i));
			}
		}
		return results;
	}
	public static ArticleData newArticle(Node item) {
		Element e = (Element)item;
		String pubDate = extractValue(e, "pubDate");
		String contents = extractValue(e, "description");
		String url = extractValue(e, "link");
		String title = extractValue(e, "title");
		String creator = extractValue(e, "dc:creator");
		List<String> categories = extractValues(e, "category");
		String eventDate = extractValue(e, "dc:date");
		String eventLocation = extractValue(e, "location");
		
		String category = getCategory(categories);
		List<String> tags = getTags(categories);
		String tags_joined = android.text.TextUtils.join(",", tags);
		
		/*Log.d("PHENND","pubDate: " + pubDate);
		Log.d("PHENND", "contents: " + contents);
		Log.d("PHENND", "url: " + url);
		Log.d("PHENND", "title: " + title);
		Log.d("PHENND", "creator: " + creator);
		Log.d("PHENND", "category: " + category);*/
		
		ContentValues rowVals = new ContentValues();
		rowVals.put(PHENNDDbOpenHelper.COL_PUBDATE, pubDate);
		rowVals.put(PHENNDDbOpenHelper.COL_CONTENTS, contents);
		rowVals.put(PHENNDDbOpenHelper.COL_URL, url);
		rowVals.put(PHENNDDbOpenHelper.COL_TITLE, title);
		rowVals.put(PHENNDDbOpenHelper.COL_CREATOR, creator);
		rowVals.put(PHENNDDbOpenHelper.COL_CATEGORY, category);
		rowVals.put(PHENNDDbOpenHelper.COL_TAGS, tags_joined);
		if (eventDate != "") { rowVals.put(PHENNDDbOpenHelper.COL_EVENTDATE, eventDate); }
		if (eventLocation != "") { rowVals.put(PHENNDDbOpenHelper.COL_EVENTLOCATION, eventLocation); } 
		
		SQLiteDatabase db = phenndDB.getWritableDatabase();
		db.insert(PHENNDDbOpenHelper.DATABASE_TABLE, null, rowVals);
		
		ArticleData output = new ArticleData(url, pubDate, contents, title, creator, category);
		output.setTags(tags_joined);
		return output;
	}
	
	public static String extractValue(Element e, String tag) {
		NodeList matches = e.getElementsByTagName(tag);
		if (matches.getLength() < 1)  { return ""; }
		return matches.item(0).getTextContent();
	}
	
	public static List<String> extractValues(Element e, String tag) {
		NodeList matches = e.getElementsByTagName(tag);
		List<String> vals = new ArrayList<String>();
		for (int i = 0; i < matches.getLength(); i++) {
			vals.add(matches.item(1).getTextContent());
		}
		return vals;
	}

	public static List<String> getArticleTitlesForTag(String tagName)
	{
		List<String> titles = new ArrayList<String>();
		String[] resultsCol = {"title"};
		String queryString = PHENNDDbOpenHelper.COL_TAGS + " LIKE '%" + tagName + "%'";
		SQLiteDatabase db = phenndDB.getReadableDatabase();
		Cursor cursor = db.query(PHENNDDbOpenHelper.DATABASE_NAME, resultsCol, queryString, null, null, null, null); 
		
		for (int i = 0; i < cursor.getCount(); i++) {
			String title = extract(cursor, PHENNDDbOpenHelper.COL_TAGS);
			if ( title != "") {
				titles.add(title);
			}
		}
		return titles; // Should return a list of article titles, based on articles with the given tag
	}
	
	public static List<String> getArticleTitlesForCategory(String categoryName)
	{
		List<String> titles = new ArrayList<String>();
		String[] resultsCol = {"title"};
		String where = PHENNDDbOpenHelper.COL_CATEGORY + "=" + categoryName;
		
		SQLiteDatabase db = phenndDB.getReadableDatabase();
		Cursor cursor = db.query(PHENNDDbOpenHelper.DATABASE_TABLE, resultsCol, where, null, null, null, null);
		
		for (int i = 0; i < cursor.getCount(); i++) {
			String title = extract(cursor, PHENNDDbOpenHelper.COL_CATEGORY);
			if ( title != "") {
				titles.add(title);
			}
		}
		return titles; // Should return a list of article titles, based on articles with the given tag
	}
	public static void addFavorite(String title) {
		ArticleData article = getArticle(title);
		if (article != null) { 
			article.setFavorited(true);
		}
		ContentValues updatedValues = new ContentValues();
		updatedValues.put(PHENNDDbOpenHelper.COL_FAVORITED, "1");
		String where = PHENNDDbOpenHelper.COL_TITLE + "=" + dbClean(title);
		String whereArgs[] = null;
		SQLiteDatabase db = phenndDB.getWritableDatabase();
		db.update(PHENNDDbOpenHelper.DATABASE_TABLE, updatedValues, where, whereArgs);
		favoriteNames.add(title);
	}
	public static void removeFavorite(String title) {
		ArticleData article = getArticle(title);
		if (article != null) { 
			article.setFavorited(false);
		}
		ContentValues updatedValues = new ContentValues();
		updatedValues.put(PHENNDDbOpenHelper.COL_FAVORITED, "0");
		String where = PHENNDDbOpenHelper.COL_TITLE + "=" + dbClean(title);
		String whereArgs[] = null;
		SQLiteDatabase db = phenndDB.getWritableDatabase();
		db.update(PHENNDDbOpenHelper.DATABASE_TABLE, updatedValues, where, whereArgs);
		favoriteNames.remove(favoriteNames.indexOf(title));
	}
	
	public static int getUpdatedCount() {
		return updatedCount;
	}
	public static List<String> getUpdatedTitles() {
		return updatedTitles;
	}
	public static void clearUpdated() {
		updatedCount = 0;
		updatedTitles.clear();
	}

	
	/* Quote from info passed along to Dan
	 * I added the background service, which updates the list of articles. 
	 * Also, updating articles now also records how many articles have been acquired as a static field "updatedCount", 
	 * and I am providing the method "static int getupdatedCount()" and "List<String> getupdatedTitles()", 
	 * as well as "clearUpdated()" to clear it once they've loaded. This should probably be called in "onResume".
	 * 
	 * Also, we need to modify onResume to schedule properly. 
	 * That needs to be taken from the preferences, which I believe is Kostya's job.
	 */
}
