package com.chccc.bible.db;

import java.util.ArrayList;

import com.chccc.bible.dto.BookDTO;
import com.chccc.bible.dto.VerseDTO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VerseHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "VerseHandler.java";

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "Bible";

    // table details
    private  final String tableName = "verse";
    private  final String fieldId = "id";
    private  final String fieldChapterNumber = "chapterNumber";
    private  final String fieldBookNumber = "bookNumber";
    private  final String fieldVersion = "version";
    private  final String fieldLineNumber = "lineNumber";
    private  final String fieldContent = "content";
    
    
    // constructor
    public VerseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

   
    
    /*
     * When upgrading the database, it will drop the current table and recreate.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    }

   

    /*
     * Read records related to the search term
     */
    public ArrayList<VerseDTO> getChapterVerses(String chapterNumber, String bookNumber, String version) {
    	ArrayList <VerseDTO> verses = new ArrayList<VerseDTO> ();
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldChapterNumber + " = '" + chapterNumber + "'";
        sql += " AND " + fieldBookNumber + " = '" + bookNumber + "'";
        sql += " AND " + fieldVersion + " = '" + version + "'";
        sql += " ORDER BY " + fieldLineNumber + " ASC";
//        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        BookDTO[] books = new BookDTO[recCount];
        int x = 0;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String content = cursor.getString(cursor.getColumnIndex(fieldContent));
                String lineNumber= cursor.getString(cursor.getColumnIndex(fieldLineNumber));
                
                VerseDTO verse = new VerseDTO(content, lineNumber, chapterNumber, bookNumber);

                verses.add(verse);
                
                
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return verses;
        
    }
    
    
    /*
     * Read records related to the search term
     */
    public ArrayList<VerseDTO> searchVerses(ArrayList<String> chapterNumbers, ArrayList<String> bookNumbers, ArrayList<String> searchStrings, String version) {
    	ArrayList <VerseDTO> verses = new ArrayList<VerseDTO> ();
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        
        //handle the search strings -- START 
        if (searchStrings ==null || searchStrings.size() ==0) {
        	return null;
        } 
        
        sql += " WHERE (" + fieldContent + " LIKE '%" + searchStrings.get(0).trim() + "%' ";
        if (searchStrings.size()>1) {
        	for (int i=1; i<searchStrings.size(); i++) {
        		sql += " OR" + fieldContent + " LIKE '%" + searchStrings.get(i).trim() + "%' ";
        	}
        }

        sql += ")";
      //handle the search strings -- END
        
//        
//        sql += " WHERE " + fieldChapterNumber + " = '" + chapterNumber + "'";
//        sql += " AND " + fieldBookNumber + " = '" + bookNumber + "'";
        
        sql += " ORDER BY " + fieldBookNumber + " ASC, " + fieldChapterNumber + " ASC ";
        sql += " LIMIT 0, 100";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

//        int recCount = cursor.getCount();
        
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String content = cursor.getString(cursor.getColumnIndex(fieldContent));
                String lineNumber= cursor.getString(cursor.getColumnIndex(fieldLineNumber));
                String chapterNumber = cursor.getString(cursor.getColumnIndex(fieldChapterNumber));
                String bookNumber = cursor.getString(cursor.getColumnIndex(fieldBookNumber));
                
                
                VerseDTO verse = new VerseDTO(content, lineNumber, chapterNumber, bookNumber);

                verses.add(verse);
                
                
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return verses;
        
    }
     
}