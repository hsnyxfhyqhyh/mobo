package com.ying.flashcard.java.main;

import com.ying.flashcard.java.db.DBHelper;
import com.ying.flashcard.java.dto.SetDTO;
import com.ying.flashcard.java.util.SetLoader;
import com.ying.flashcard.java.util.XmlParserUtil;

public class LoadData {
	
	private static final String DATA_FOLDER = "tempFile" ; 
	
	
	public static void main(String[] args) {
		/******************************************************************
		 *  Call the following function only when initialize the database *
		 ******************************************************************/
		DBHelper.initializeDB();
		
		addSet(String.format("%s/java_amqp.txt", DATA_FOLDER), "电脑", "JAVA - AMQP");
		addSet(String.format("%s/java_core.txt", DATA_FOLDER), "电脑", "JAVA - CORE");
		addSet(String.format("%s/computer_brainstorm.txt", DATA_FOLDER), "电脑", "电脑 - 头脑风暴");
		addSet(String.format("%s/computer_enterpriseIntegrationPattern.txt", DATA_FOLDER), "电脑", "SOA - Enterprise Pattern");
		
		addSet(String.format("%s/bible_brainstorm.txt", DATA_FOLDER), "圣经","圣经 - 基础");
		addSet(String.format("%s/bible_verses.txt", DATA_FOLDER), "圣经","圣经 - 经文");
				
		
		addSet(String.format("%s/MyDailyLife.txt", DATA_FOLDER), "日常","日常 - 我的日常");
		System.out.println("ALL DONE");
	}

	private static void addSet(String fileName, String categoryName, String setName) {
		
		int categoryID = DBHelper.getCategoryID(categoryName);
		
		if (categoryID == 0 ) {
			DBHelper.addCategory(categoryName);
			categoryID = DBHelper.getCategoryID(categoryName);
		}
		
		
		addSet(fileName, setName, categoryID);
		

	}
	
	private static void addSet(String fileName, String setName, int categoryFK) {

		SetDTO setDTO = SetLoader.loadTxt(fileName, setName);
		
		boolean createSuccessful = false;

		DBHelper.addSet(setDTO, categoryFK);

	}

}
