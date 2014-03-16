package com.ying.flashcard.util;


import com.ying.flashcard.activity.ApplicationInitializeActivity;

import android.content.SharedPreferences;

public class MainActivityPreferences {
	
	public static final String PREFS_NAME = "com.ying.flashcard";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	private String setId = "";
	private String setName = "";
	
	private String questionId = "";
	
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
	
	public String getSetId() {
		return settings.getString("setId", "");
	}

	public void setSetId(String setId) {
		editor.putString("setId", setId);
	}

	public String getSetName() {
		return settings.getString("setName", "");
	}

	public void setSetName(String setName) {
		editor.putString("setName", setName);
	}

	public String getQuestionId() {
		return settings.getString("questionId", "");
	}

	public void setQuestionId(String questionId) {
		editor.putString("questionId", questionId);
	}

	public void commit() {
		editor.commit();
	}
	
	public void reset() {
		commit();
	}
	
	public void setQuestionIdBlank() {
		editor.putString("questionId", "");
		commit();
	}
		
	public void resetPreferenceMessage() {
		preferenceMessage = null;
	}
}
