package com.ying.flashcard.java.main;

import com.ying.flashcard.java.db.DBHelper;
import com.ying.flashcard.java.dto.SetDTO;
import com.ying.flashcard.java.util.XmlParserUtil;

public class LoadData {
	
	private static final String DATA_FOLDER = "datafile" ; 
	
	
	public static void main(String[] args) {
		/******************************************************************
		 *  Call the following function only when initialize the database *
		 ******************************************************************/
		DBHelper.initializeDB();
		
		addSet(String.format("%s/java_amqp.xml", DATA_FOLDER), "电脑");
		addSet(String.format("%s/java_core.xml", DATA_FOLDER), "电脑");
				
		System.out.println("ALL DONE");
	}

	private static void addSet(String fileName, String categoryName) {
		
		int categoryID = DBHelper.getCategoryID(categoryName);
		
		if (categoryID == 0 ) {
			DBHelper.addCategory(categoryName);
			categoryID = DBHelper.getCategoryID(categoryName);
		}
		
		
		addSet(fileName, categoryID);
		

	}
	
	private static void addSet(String fileName, int categoryFK) {

		SetDTO setDTO = XmlParserUtil.getSetContent(fileName);
		
		boolean createSuccessful = false;

		DBHelper.addSet(setDTO, categoryFK);

	}

}
