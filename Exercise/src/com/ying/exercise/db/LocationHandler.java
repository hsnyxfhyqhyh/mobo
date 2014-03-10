package com.ying.exercise.db;

import java.util.ArrayList;

import com.ying.exercise.dto.LocationDTO;
import com.ying.exercise.dto.UserDTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocationHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "LocationHandler.java";

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "Exercise.db";
    
    // table details
    private  final String tableName = "tbllocation";
    private  final String fieldId = "id";
    private  final String fieldName = "name";
    
    // constructor
    public LocationHandler(Context context) {
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
    public boolean create(LocationDTO locationDTO) {

        boolean createSuccessful = false;

        if(!checkIfExists(locationDTO.getName())){
                    
            SQLiteDatabase db = this.getWritableDatabase();
            
            ContentValues values = new ContentValues();
            values.put(fieldName, locationDTO.getName());
            
            createSuccessful = db.insert(tableName, null, values) > 0;
            
            db.close();
            
            if(createSuccessful){ 
                Log.e(TAG, locationDTO.getName() + " created.");
            }
        }
        
        return createSuccessful;
    }
    
    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(String locationName_){
        
        boolean recordExists = false;
                
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldName + " FROM " + tableName + " WHERE " + fieldName + " = '" + locationName_ + "'", null);
        
        if(cursor!=null) {
            
            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();
        
        return recordExists;
    }

    public LocationDTO getLocation(String name) {

    	
    	LocationDTO locationDTO = null;
    	try {
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldName + " = '" + name + "'";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(fieldId));
            
            locationDTO = new LocationDTO();
            locationDTO.setName(name);
            locationDTO.setId(id);
                
        }

        cursor.close();
        db.close();
    	}catch(Exception e) {
    		String msg = e.getMessage();
    	}
        return locationDTO;
        
    }
    
    public ArrayList<LocationDTO> getLocations() {
    	
    	ArrayList <LocationDTO> locations = new ArrayList<LocationDTO> ();
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
//        sql += " WHERE " + fieldTestament + " = '" + testament.toUpperCase() + "'";
        sql += " ORDER BY " + fieldId + " ASC";
//        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        LocationDTO locationDTO = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
	            String name = cursor.getString(cursor.getColumnIndex(fieldName));
	            String id = cursor.getString(cursor.getColumnIndex(fieldId));
	            
	            locationDTO = new LocationDTO();
	            locationDTO.setId(id);
	            locationDTO.setName(name);
	            
	            locations.add(locationDTO);
            
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return locations;
        
    }
    
    public LocationDTO getLocationById(String id) {

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldId + " = '" + id + "'";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        LocationDTO locationDTO = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(fieldName));
            
            
            locationDTO = new LocationDTO();
            locationDTO.setName(name);
            locationDTO.setId(id);
                
        }

        cursor.close();
        db.close();

        return locationDTO;
        
    }
}