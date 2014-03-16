package com.ying.flashcard.db;

import java.util.ArrayList;

import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.dto.SetDTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuestionHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "QuestionHandler.java";

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "FlashCard.db";
    
    // table details
    private  final String tableName = "FlashCardSetQuestions";
    
    String fieldId = "id";
	String fieldTitle = "Title";
	String fieldAnswers = "Answers";
	String fieldSetID = "setFK";
    
    // constructor
    public QuestionHandler(Context context) {
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

//        String sql = "DROP TABLE IF EXISTS " + tableName;
//        db.execSQL(sql);
//
//        onCreate(db);
    }

    /*
     * create new record
     * @param myObj contains details to be added as single row.
     */
    public boolean create(QuestionDTO questionDTO, int setFK) {

        boolean createSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();

        values.put(fieldTitle, questionDTO.getTitle());
        values.put(fieldAnswers, questionDTO.getAnswer());
        values.put(fieldSetID, setFK);
        
        createSuccessful = db.insert(tableName, null, values) > 0;
        
        db.close();
        
        if(createSuccessful){ 
            Log.e(TAG, questionDTO.getTitle() + " created.");
        }
        
        
        return createSuccessful;
    }
    
    public boolean update(QuestionDTO questionDTO) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();

        values.put(fieldTitle, questionDTO.getTitle());
        values.put(fieldAnswers, questionDTO.getAnswer());
        
        int result = db.update(tableName, values, fieldId + " = ?", new String[] { String.valueOf(questionDTO.getId()) });
       
        db.close();
        
        if(result == 1){ 
            Log.e(TAG, questionDTO.getTitle() + " updated.");
            return true;
        } else {
        	return false;
        }
    }
    
    // Deleting single contact
    public void delete(String questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName,  fieldId + " = ?", new String[] { questionId });
        db.close();
    }
    
    public QuestionDTO getQuestion(String title) {

    	QuestionDTO questionDTO = null;
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldTitle + " = '" + title + "'";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	
            String id = cursor.getString(cursor.getColumnIndex(fieldId));
            String answer = cursor.getString(cursor.getColumnIndex(fieldAnswers));
            int setFK = cursor.getInt(cursor.getColumnIndex(fieldSetID));
            
            questionDTO = new QuestionDTO();
            questionDTO.setId(id);
            questionDTO.setTitle(title);
            questionDTO.setAnswer(answer);
            questionDTO.setSetFK(setFK);
                
        }

        cursor.close();
        db.close();

        return questionDTO;
        
    }
    
    public ArrayList<QuestionDTO> getQuestions(int setFK) {
    	
    	ArrayList <QuestionDTO> questions = new ArrayList<QuestionDTO> ();
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldSetID + " = " + setFK + "";
        sql += " ORDER BY " + fieldId + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        QuestionDTO questionDTO = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
	            
	            String id = cursor.getString(cursor.getColumnIndex(fieldId));
	            String title = cursor.getString(cursor.getColumnIndex(fieldTitle));
	            String answer = cursor.getString(cursor.getColumnIndex(fieldAnswers));
	            
	            questionDTO = new QuestionDTO();
	            questionDTO.setId(id);
	            questionDTO.setTitle(title);
	            questionDTO.setAnswer(answer);
	            questionDTO.setSetFK(setFK);
	            
	            questions.add(questionDTO);
            
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return questions;
        
    }
    
    public QuestionDTO getQuestionById(String id) {

    	QuestionDTO questionDTO = null;
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldId + " = " + id + "";
//        sql += " ORDER BY " + fieldId + " DESC";
//        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	
            String title = cursor.getString(cursor.getColumnIndex(fieldTitle));
            String answer = cursor.getString(cursor.getColumnIndex(fieldAnswers));
            int setFK = cursor.getInt(cursor.getColumnIndex(fieldSetID));
            
            questionDTO = new QuestionDTO();
            questionDTO.setId(id);
            questionDTO.setTitle(title);
            questionDTO.setAnswer(answer);
            questionDTO.setSetFK(setFK);
                
        }

        cursor.close();
        db.close();

        return questionDTO;
        
    }
    
}