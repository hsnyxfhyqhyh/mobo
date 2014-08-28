package com.chccc.bible.activity;

import com.chccc.bible.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
 
public class SettingsActivity extends PreferenceActivity {
 
	public static final String PREFS_NAME = "com.chccc.bible";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName(PREFS_NAME);
        prefMgr.setSharedPreferencesMode(MODE_WORLD_READABLE);
        
        addPreferencesFromResource(R.xml.settings);
 
    }
}