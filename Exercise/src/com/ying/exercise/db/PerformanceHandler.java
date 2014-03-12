package com.ying.exercise.db;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ying.exercise.dto.PerformanceDTO;

public class PerformanceHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "PerformanceHandler.java";

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "Exercise.db";
    
    // table details
    private final String tableName = "tblperformance";
    
    private final String fieldId = "id";
    private final String fieldDescription = "description";
    private final String fieldMachineFK = "machineFK";
    private final String fieldUserFK = "userFK"; 
    private final String fieldCreateDate = "createDate";
    
    
    private final String fieldMachineName = "machineName";
    
    // constructor
    public PerformanceHandler(Context context) {
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
    public boolean create(PerformanceDTO dto) {

        boolean createSuccessful = false;

        if(!checkIfExists(dto)){
                    
            SQLiteDatabase db = this.getWritableDatabase();
            
            ContentValues values = new ContentValues();
            values.put(fieldDescription, dto.getDescription());
            values.put(fieldMachineFK, dto.getMachineFK());
            values.put(fieldUserFK, dto.getUserFK());
            values.put(fieldCreateDate, dto.getCreateDate());
            
            
            createSuccessful = db.insert(tableName, null, values) > 0;
            
            db.close();
            
            if(createSuccessful){ 
                Log.e(TAG, dto.getDescription() + " created.");
            }
        }
        
        return createSuccessful;
    }
    
    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(PerformanceDTO dto){
        
        boolean recordExists = false;
        
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT " + fieldMachineFK + " FROM " + tableName; 
        sql += " WHERE " + fieldMachineFK + " = '"  + dto.getMachineFK() + "'"; 
        sql += " AND " + fieldUserFK + " = " + dto.getUserFK();
        sql += " AND " + fieldCreateDate + " = '" + dto.getCreateDate() + "'";
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

    public PerformanceDTO getPerformance(String id) {
    	String tableMachineName = "tblMachine";
    	
    	PerformanceDTO dto = null;
    	try {
        // select query
        String sql = "";
        sql += "SELECT p.id AS id, p.description AS description, p.machineFK AS machineFK, p.userFK AS userFK, " +
        		"p.createDate AS createDate, m.name AS MachineName FROM " + tableName + " AS p";
        sql += " JOIN " + tableMachineName + " AS m ";
        sql += " ON p.machineFK = m.id" ;
        sql += " WHERE p." + fieldId + " = " + id + "";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String description = cursor.getString(cursor.getColumnIndex(fieldDescription));
            String machineFK = cursor.getString(cursor.getColumnIndex(fieldMachineFK));
            String machineName = cursor.getString(cursor.getColumnIndex(fieldMachineName));
            String userFK = cursor.getString(cursor.getColumnIndex(fieldUserFK));
            String createDate = cursor.getString(cursor.getColumnIndex(fieldCreateDate));
            
            dto = new PerformanceDTO();
            dto.setId(id);
            dto.setDescription (description);
            dto.setMachineFK(machineFK);
            dto.setMachineName(machineName);
            dto.setUserFK(userFK);
            dto.setCreateDate(createDate);
        }

        cursor.close();
        db.close();
    	}catch(Exception e) {
    		String msg = e.getMessage();
    	}
        return dto;
        
    }
    
    public ArrayList<PerformanceDTO> getPerformances(String userFK, String createDate) {
    	String tableMachineName = "tblMachine";
    	
    	ArrayList <PerformanceDTO> dtos = new ArrayList<PerformanceDTO> ();
    	
    	 // select query
        String sql = "";
        sql += "SELECT p.id AS id, p.description AS description, p.machineFK AS machineFK, p.userFK AS userFK, " +
        		"p.createDate AS createDate, m.name AS machineName FROM " + tableName + " AS p";
        sql += " JOIN " + tableMachineName + " AS m ";
        sql += " ON p.machineFK = m.id" ;
        sql += " WHERE p." + fieldUserFK + " = '" + userFK + "'";
        sql += " AND p." + fieldCreateDate + " = '" + createDate + "'";
        sql += " ORDER BY " + fieldId + " DESC";
        
        System.out.println(sql);

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        PerformanceDTO dto = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	 String id = cursor.getString(cursor.getColumnIndex(fieldId));
                 String description = cursor.getString(cursor.getColumnIndex(fieldDescription));
                 String machineFK = cursor.getString(cursor.getColumnIndex(fieldMachineFK));
                 
                 String machineName = cursor.getString(cursor.getColumnIndex(fieldMachineName));
                 
                 dto = new PerformanceDTO();
                 dto.setId(id);
                 dto.setDescription (description);
                 dto.setMachineFK(machineFK);
                 dto.setMachineName(machineName);
                 dto.setUserFK(userFK);
                 dto.setCreateDate(createDate);
                 dtos.add(dto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dtos;
        
    }

	public ArrayList<PerformanceDTO> getPerformancesByMachine(String userFK,String machineFK) {
		String tableMachineName = "tblMachine";
    	
    	ArrayList <PerformanceDTO> dtos = new ArrayList<PerformanceDTO> ();
    	
    	 // select query
        String sql = "";
        sql += "SELECT p.id AS id, p.description AS description, p.machineFK AS machineFK, p.userFK AS userFK, " +
        		"p.createDate AS createDate, m.name AS machineName FROM " + tableName + " AS p";
        sql += " JOIN " + tableMachineName + " AS m ";
        sql += " ON p.machineFK = m.id" ;
        sql += " WHERE p." + fieldUserFK + " = '" + userFK + "'";
        sql += " AND p." + fieldMachineFK + " = '" + machineFK + "'";
        sql += " ORDER BY " + fieldId + " DESC";
        
        System.out.println(sql);

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        PerformanceDTO dto = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	 String id = cursor.getString(cursor.getColumnIndex(fieldId));
                 String description = cursor.getString(cursor.getColumnIndex(fieldDescription));
                 
                 String machineName = cursor.getString(cursor.getColumnIndex(fieldMachineName));
                 String createDate = cursor.getString(cursor.getColumnIndex(fieldCreateDate));

                 
                 dto = new PerformanceDTO();
                 dto.setId(id);
                 dto.setDescription (description);
                 dto.setMachineFK(machineFK);
                 dto.setMachineName(machineName);
                 dto.setUserFK(userFK);
                 dto.setCreateDate(createDate);
                 dtos.add(dto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dtos;
	}
    
}