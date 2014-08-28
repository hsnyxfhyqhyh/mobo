package com.chccc.bible.plan;

import java.util.ArrayList;

import android.content.Context;


public interface ReadPlanInterface {

	//Date format should be MM/dd/YYYY
	public ArrayList getChaptersByDate(Context context, String date); 
}
