package com.ying.exercise.util;

import com.ying.exercise.activity.ApplicationInitializeActivity;

import android.content.SharedPreferences;

public class MainActivityPreferences {
	
	public static final String PREFS_NAME = "com.ying.exercise";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	private String userId = "";
	private String userName = "";
	private String locationId = "1";
	
	private String day = "";
	


	private String fontFamily = "fonts/STKAITI.TTF";
	
	
	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	private int fontSizeHeader = 30;
	
	public int getFontSizeHeader() {
		return Integer.parseInt(settings.getString("prefFontSizeHeader", "30"));
	}

	private String preferenceMessage = null;
	
	private String bookTotalChapter = "";
	
	private String theme = "";
	
	private ApplicationInitializeActivity actionInitializeActivity;
	
	public MainActivityPreferences(ApplicationInitializeActivity actionInitializeActivity) {
		this.actionInitializeActivity = actionInitializeActivity;
		
		settings = actionInitializeActivity.getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		
		if (!settings.getBoolean("preference_setted", false)) {
			editor.putString("setId", "1");
			editor.putString("setName", "");
			editor.putBoolean("preference_setted", true);
		}
	}
	
	public String getPreferenceMessage() {
		return preferenceMessage;
	}

	public String getTheme() {
		return settings.getString("theme", "");
	}

	public void setTheme(String theme) {
		editor.putString("theme", theme);
	}
	
	public String getUserId() {
		return settings.getString("userId", "");
	}

	public void setUserId(String userId) {
		editor.putString("userId", userId);
	}

	public String getUserName() {
		return settings.getString("userName", "");
	}

	public void setUserName(String userName) {
		editor.putString("userName", userName);
	}

	public String getLocationId() {
		return settings.getString("locationId", "");
	}

	public void setLocationId(String locationId) {
		
		editor.putString("locationId", locationId);
	}
	
	
	public String getDay() {
		return settings.getString("day", "");
		
	}

	public void setDay(String day) {
		editor.putString("day", day);
	}
	
	public void commit() {
		editor.commit();
	}
	
	public void reset() {
		commit();
	}
		
	public void resetPreferenceMessage() {
		preferenceMessage = null;
	}
	
	public void logout() {
		editor.putString("userId", "");
		editor.putString("userName", "");
		editor.putString("locationId", "");
		commit();
	}
}
