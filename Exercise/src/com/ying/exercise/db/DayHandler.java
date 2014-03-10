package com.ying.exercise.db;

import java.util.ArrayList;

import com.ying.exercise.dto.DayDTO;
import com.ying.exercise.dto.UserDTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DayHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "DayHandler.java";

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "Exercise.db";
    
    // table details
    private  final String tableName = "tblDay";
    private  final String fieldId = "id";
    private  final String fieldName = "day";
    private final String fieldUserFK = "userFK";
    
    // constructor
    public DayHandler(Context context) {
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
    public boolean create(DayDTO dayDTO) {

        boolean createSuccessful = false;

        if(!checkIfExists(dayDTO)){
                    
            SQLiteDatabase db = this.getWritableDatabase();
            
            ContentValues values = new ContentValues();
            values.put(fieldName, dayDTO.getName());
            values.put(fieldUserFK, dayDTO.getUserFK());
            
            createSuccessful = db.insert(tableName, null, values) > 0;
            
            db.close();
            
            if(createSuccessful){ 
                Log.e(TAG, dayDTO.getName() + " created.");
            }
        }
        
        return createSuccessful;
    }
    
    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(DayDTO dayDTO){
        
        boolean recordExists = false;
                
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT " + fieldName + " FROM " + tableName + " WHERE " + fieldName + " = '" + dayDTO.getName() + "' AND " + fieldUserFK + " = " + dayDTO.getUserFK();
        Cursor cursor = db.rawQuery(sql , null);
        
        if(cursor!=null) {
            
            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();
        
        return recordExists;
    }

    public DayDTO getDay(String name, String userFK) {

    	
    	DayDTO dayDTO = null;
    	try {
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldName + " = '" + name + "'";
        sql += " AND " + fieldUserFK + " = " + userFK + "";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(fieldId));
            
            dayDTO = new DayDTO();
            dayDTO.setName(name);
            dayDTO.setUserFK(userFK);
            dayDTO.setId(id);
                
        }

        cursor.close();
        db.close();
    	}catch(Exception e) {
    		String msg = e.getMessage();
    	}
        return dayDTO;
        
    }
    
    public ArrayList<DayDTO> getDays(String userFK) {
    	
    	ArrayList <DayDTO> days = new ArrayList<DayDTO> ();
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldUserFK + " = " + userFK + "";
        sql += " ORDER BY " + fieldId + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        DayDTO dayDTO = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
	            String name = cursor.getString(cursor.getColumnIndex(fieldName));
	            String id = cursor.getString(cursor.getColumnIndex(fieldId));
	            
	            dayDTO = new DayDTO();
	            dayDTO.setId(id);
	            dayDTO.setName(name);
	            dayDTO.setUserFK(userFK);
	            
	            days.add(dayDTO);
            
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return days;
        
    }
    
    
}