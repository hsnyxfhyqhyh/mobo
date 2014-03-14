package com.ying.exercise.db;

import java.util.ArrayList;

import com.ying.exercise.dto.MachineDTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MachineHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "MachineHandler.java";

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "Exercise.db";
    
    // table details
    private  final String tableName = "tblMachine";
    private  final String fieldId = "id";
    private  final String fieldName = "name";
    private final String fieldLocationFK = "locationFK";
    private final String fieldDescription = "description";
    
    // constructor
    public MachineHandler(Context context) {
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
    public boolean create(MachineDTO machineDTO) {

        boolean createSuccessful = false;

        if(!checkIfExists(machineDTO)){
                    
            SQLiteDatabase db = this.getWritableDatabase();
            
            ContentValues values = new ContentValues();
            values.put(fieldName, machineDTO.getName());
            values.put(fieldDescription, machineDTO.getDescription());
            values.put(fieldLocationFK, machineDTO.getLocationFK());
            
            createSuccessful = db.insert(tableName, null, values) > 0;
            
            db.close();
            
            if(createSuccessful){ 
                Log.e(TAG, machineDTO.getName() + " created.");
            }
        }
        
        return createSuccessful;
    }
    
    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(MachineDTO machineDTO){
        
        boolean recordExists = false;
                
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT " + fieldName + " FROM " + tableName + " WHERE " + fieldName + " = '" + machineDTO.getName() + "' AND " + fieldLocationFK + " = " + machineDTO.getLocationFK();
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

    public MachineDTO getMachine(String name, String locationFK) {

    	
    	MachineDTO machineDTO = null;
    	try {
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldName + " = '" + name + "'";
        sql += " AND " + fieldLocationFK + " = " + locationFK + "";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(fieldId));
            String description = cursor.getString(cursor.getColumnIndex(fieldDescription));
            
            machineDTO = new MachineDTO();
            machineDTO.setName(name);
            machineDTO.setDescription (description);
            machineDTO.setLocationFK(locationFK);
            machineDTO.setId(id);
                
        }

        cursor.close();
        db.close();
    	}catch(Exception e) {
    		String msg = e.getMessage();
    	}
        return machineDTO;
        
    }
    
    public ArrayList<MachineDTO> getMachines(String locationFK) {
    	
    	ArrayList <MachineDTO> days = new ArrayList<MachineDTO> ();
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldLocationFK + " = " + locationFK + "";
        sql += " ORDER BY " + fieldName + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        MachineDTO machineDTO = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
	            String name = cursor.getString(cursor.getColumnIndex(fieldName));
	            String id = cursor.getString(cursor.getColumnIndex(fieldId));
	            String description = cursor.getString(cursor.getColumnIndex(fieldDescription));
	            
	            machineDTO = new MachineDTO();
	            machineDTO.setId(id);
	            machineDTO.setName(name);
	            machineDTO.setDescription(description);
	            machineDTO.setLocationFK(locationFK);
	            
	            days.add(machineDTO);
            
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return days;
        
    }
    
    
}