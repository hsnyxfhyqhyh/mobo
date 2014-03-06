package com.ying.flashcard.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DataBaseHelper extends SQLiteOpenHelper {
	private Context mycontext;

	//This is the package defined in the manifest.xml file? 
	private static final String PACKAGE_NAME = "com.ying.flashcard";
	
	private String DB_PATH = String.format("/data/data/%s/databases/", PACKAGE_NAME);
	
	// private String DB_PATH =
	// mycontext.getApplicationContext().getPackageName()+"/databases/";
	private static final String DB_NAME_TEMPLATE = "FlashCard%s.db";
	
	private static final String DB_NAME = String.format(DB_NAME_TEMPLATE , "");
	public SQLiteDatabase myDataBase;

	/*
	 * private String DB_PATH = "/data/data/" +
	 * mycontext.getApplicationContext().getPackageName() + "/databases/";
	 */

	public DataBaseHelper(Context context) throws IOException {
		super(context, DB_NAME, null, 1);
		this.mycontext = context;
		boolean dbexist = checkdatabase();
		if (dbexist) {
			// System.out.println("Database exists");
			opendatabase();
		} else {
			System.out.println("Database doesn't exist");
			createdatabase();
		}

	}

	public void createdatabase() throws IOException {
		boolean dbexist = checkdatabase();
		if (dbexist) {
			// System.out.println(" Database exists.");
		} else {
			this.getReadableDatabase();
			try {
				copydatabase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private boolean checkdatabase() {
		// SQLiteDatabase checkdb = null;
		boolean checkdb = false;
		try {
			String myPath = DB_PATH + DB_NAME;
			File dbfile = new File(myPath);
			// checkdb =
			// SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
			checkdb = dbfile.exists();
		} catch (SQLiteException e) {
			System.out.println("Database doesn't exist");
		}

		return checkdb;
	}

	private void copydatabase() throws IOException {

		// Open your local db as the input stream
//		InputStream myinput = mycontext.getAssets().open(DB_NAME);
		String backupDBName = DB_NAME;
		String subBackupDBPath = String.format("/Download/flashcard/%s" , DB_NAME);
		
		InputStream myinput  = new FileInputStream(new File(Environment.getExternalStorageDirectory() + subBackupDBPath));

		// Path to the just created empty db
		String outfilename = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myoutput = new FileOutputStream(outfilename);

		// transfer byte to inputfile to outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myinput.read(buffer)) > 0) {
			myoutput.write(buffer, 0, length);
		}

		// Close the streams
		myoutput.flush();
		myoutput.close();
		myinput.close();

	}

	public void opendatabase() throws SQLException {
		// Open the database
		String mypath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

	}

	public synchronized void close() {
		if (myDataBase != null) {
			myDataBase.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public static String getDbNameTemplate() {
		return DB_NAME_TEMPLATE;
	}
	
	public static String getDBName(){
		return DB_NAME;
	}
}