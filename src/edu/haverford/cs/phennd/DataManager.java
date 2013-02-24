package edu.haverford.cs.phennd;

import java.util.Date;
import java.util.List;

public class DataManager {

	private static Date lastUpdate;
	private static List<String> allTags;
	private static List<String> flaggedTags;
	private static List<String> favoriteUIDs;

	private static List<String> getFlaggedTags(){
		return flaggedTags;
	}

}
