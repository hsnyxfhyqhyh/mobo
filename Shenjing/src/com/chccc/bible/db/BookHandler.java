package com.chccc.bible.db;

import java.util.ArrayList;

import com.chccc.bible.dto.BookDTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "BookHandler.java";

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "Bible";

    // table details
    private  final String tableName = "book";
    private  final String fieldId = "id";
    private  final String fieldName = "name";
    private  final String fieldSearchString = "searchString";
    private  final String fieldNumber = "number";
    
    private  final String fieldTestament = "testament";
    private  final String fieldChapterCount = "chapterCount";
    private final String fieldInitialString = "initialString";
    
    public  final static String OLD_TESTAMENT = "OLD";
    public final static String NEW_TESTAMENT = "NEW";
    
    // constructor
    public BookHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating table
    @Override
    public void onCreate(SQLiteDatabase db) {

    	
//        String sql = "";
//
//        sql += "CREATE TABLE " + tableName;
//        sql += " ( ";
//        sql += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
//        sql += fieldName + " TEXT, ";
//        sql += fieldSearchString + " TEXT, ";
//        sql += fieldNumber + " TEXT, ";
//        sql += fieldTestament + " TEXT, ";
//        sql += fieldChapterCount + " TEXT, ";
//        sql += fieldInitialString + " TEXT ";
//        sql += " ) ";
//
//        db.execSQL(sql);
        
//        insertBookData();

    }

    public void insertBookData(){
        // CREATE
    	create( new BookDTO("创世纪", "csj", "01", "OLD", "50", "创"));
    	create( new BookDTO("出埃及记", "cajj", "02", "OLD", "40", "出"));
    	create( new BookDTO("利未记", "lwj", "03", "OLD", "27", "利"));
    	create( new BookDTO("民数记", "msj", "04", "OLD", "36", "民"));
    	create( new BookDTO("申命记", "smj", "05", "OLD", "34", "申"));
    	create( new BookDTO("约书亚记", "ysyj", "06", "OLD", "24", "书"));
    	create( new BookDTO("士师记", "ssj", "07", "OLD", "21", "士"));
    	create( new BookDTO("路得记", "ldj", "08", "OLD", "4", "得"));
    	create( new BookDTO("撒母耳记上", "smejs", "09", "OLD", "31", "撒上"));
    	create( new BookDTO("撒母耳记下", "smejx", "10", "OLD", "24", "撒下"));
    	create( new BookDTO("列王记上", "lwjs", "11", "OLD", "22", "王上"));
    	create( new BookDTO("列王记下", "lwjx", "12", "OLD", "25", "王下"));
    	create( new BookDTO("历代志上", "ldzs", "13", "OLD", "29", "代上"));
    	create( new BookDTO("历代志下", "ldzx", "14", "OLD", "36", "代下"));
    	create( new BookDTO("以斯拉记", "yslj", "15", "OLD", "10", "拉"));
    	create( new BookDTO("尼希米记", "nxmj", "16", "OLD", "13", "尼"));
    	create( new BookDTO("以斯帖记", "ystj", "17", "OLD", "10", "斯"));
    	create( new BookDTO("约伯记", "ybj", "18", "OLD", "42", "伯"));
    	create( new BookDTO("诗篇", "sp", "19", "OLD", "150", "诗"));
    	create( new BookDTO("箴言", "zy", "20", "OLD", "31", "箴"));
    	create( new BookDTO("传道书", "cds", "21", "OLD", "12", "传"));
    	create( new BookDTO("雅歌", "yg", "22", "OLD", "8", "雅"));
    	create( new BookDTO("以赛亚书", "ysys", "23", "OLD", "66", "赛"));
    	create( new BookDTO("耶利米书", "ylms", "24", "OLD", "52", "耶"));
    	create( new BookDTO("耶利米哀歌", "ylmag", "25", "OLD", "5", "哀"));
    	create( new BookDTO("以西结书", "yxjs", "26", "OLD", "48", "结"));
    	create( new BookDTO("但以理书", "dyls", "27", "OLD", "12", "但"));
    	create( new BookDTO("何西阿书", "hxas", "28", "OLD", "14", "何"));
    	create( new BookDTO("约珥书", "yes", "29", "OLD", "3", "珥"));
    	create( new BookDTO("阿摩司书", "amss", "30", "OLD", "9", "摩"));
    	create( new BookDTO("俄巴底亚书", "ebdys", "31", "OLD", "1", "俄"));
    	create( new BookDTO("约拿书", "yns", "32", "OLD", "4", "拿"));
    	create( new BookDTO("弥迦书", "mjs", "33", "OLD", "7", "弥"));
    	create( new BookDTO("那鸿书", "nhs", "34", "OLD", "3", "鸿"));
    	create( new BookDTO("哈巴谷书", "hbgs", "35", "OLD", "3", "哈"));
    	create( new BookDTO("西番雅书", "xfys", "36", "OLD", "3", "番"));
    	create( new BookDTO("哈该书", "hgs", "37", "OLD", "2", "该"));
    	create( new BookDTO("撒迦利亚书", "sjlys", "38", "OLD", "14", "亚"));
    	create( new BookDTO("玛拉基书", "mljs", "39", "OLD", "4", "玛"));
    	
    	create( new BookDTO("马太福音", "mtfy", "40", "NEW", "28", "太"));
    	create( new BookDTO("马可福音", "mkfy", "41", "NEW", "16", "可")); 
    	create( new BookDTO("路加福音", "ljfy", "42", "NEW", "24", "路"));
    	create( new BookDTO("约翰福音", "yhfy", "43", "NEW", "21", "约"));
    	create( new BookDTO("使徒行传", "stxz", "44", "NEW", "28", "徒"));
    	create( new BookDTO("罗马书", "lms", "45", "NEW", "16", "罗"));
    	create( new BookDTO("哥林多前书", "gldqs", "46", "NEW", "16", "林前"));
    	create( new BookDTO("哥林多后书", "gldhs", "47", "NEW", "13", "林后"));
    	create( new BookDTO("加拉太书", "jlts", "48", "NEW", "6", "加"));
    	create( new BookDTO("以弗所书", "yfss", "49", "NEW", "6", "弗"));
    	create( new BookDTO("腓立比书", "flbs", "50", "NEW", "4", "腓"));
    	create( new BookDTO("歌罗西书", "glxs", "51", "NEW", "4", "西"));
    	create( new BookDTO("帖撒罗尼加前书", "tslnjqs", "52", "NEW", "5", "帖前"));
    	create( new BookDTO("帖撒罗尼加后书", "tslnjhs", "53", "NEW", "3", "帖后"));
    	create( new BookDTO("提摩太前书", "tmtqs", "54", "NEW", "6", "提前"));
    	create( new BookDTO("提摩太后书", "tmths", "55", "NEW", "4", "提后"));
    	create( new BookDTO("提多书", "tds", "56", "NEW", "3", "多"));
    	create( new BookDTO("腓立门书", "flbs", "57", "NEW", "1", "门"));
    	create( new BookDTO("希伯来书", "xbls", "58", "NEW", "13", "来"));
    	create( new BookDTO("雅各书", "ygs", "59", "NEW", "5", "雅"));
    	create( new BookDTO("彼得前书", "bdqs", "60", "NEW", "5", "彼前"));
    	create( new BookDTO("彼得后书", "bdhs", "61", "NEW", "3", "彼后"));
    	create( new BookDTO("约翰一书", "yhys", "62", "NEW", "5", "约1"));
    	create( new BookDTO("约翰二书", "yhes", "63", "NEW", "1", "约2"));
    	create( new BookDTO("约翰三书", "yhss", "64", "NEW", "1", "约3"));
    	create( new BookDTO("犹大书", "yds", "65", "NEW", "1", "犹"));
    	create( new BookDTO("启示录", "qsl", "66", "NEW", "22", "启"));
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
    public boolean create(BookDTO book) {

        boolean createSuccessful = false;

        if(!checkIfExists(book.getName())){
                    
            SQLiteDatabase db = this.getWritableDatabase();
            
            ContentValues values = new ContentValues();
            values.put(fieldName, book.getName());
            values.put(fieldSearchString, book.getSearchString());
            values.put(fieldNumber, book.getNumber());
            values.put(fieldTestament, book.getTestament());
            values.put(fieldChapterCount, book.getChapterCount());
            values.put(fieldInitialString, book.getInitialString());
            
            createSuccessful = db.insert(tableName, null, values) > 0;
            
            db.close();
            
            if(createSuccessful){ 
                Log.e(TAG, book.getName() + " created.");
            }
        }
        
        return createSuccessful;
    }
    
    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(String bookName){
        
        boolean recordExists = false;
                
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldName + " FROM " + tableName + " WHERE " + fieldName + " = '" + bookName + "'", null);
        
        if(cursor!=null) {
            
            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();
        
        return recordExists;
    }

    /*
     * Read records related to the search term
     */
    public BookDTO[] read(String searchTerm) {

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldSearchString + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        BookDTO[] books = new BookDTO[recCount];
        int x = 0;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String name = cursor.getString(cursor.getColumnIndex(fieldName));
                String searchString = cursor.getString(cursor.getColumnIndex(fieldSearchString));
                String number = cursor.getString(cursor.getColumnIndex(fieldNumber));
                String testament= cursor.getString(cursor.getColumnIndex(fieldTestament));
                String chapterCount = cursor.getString(cursor.getColumnIndex(fieldChapterCount));
                String initialString = cursor.getString(cursor.getColumnIndex(fieldInitialString));
                
                BookDTO book = new BookDTO(name, searchString, number, testament, chapterCount, initialString);

                books[x] = book;
                
                x++;
                
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return books;
        
    }
    
    public BookDTO getBook(String bookName) {

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldName + " LIKE '%" + bookName + "%'";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        BookDTO book = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(fieldName));
            String searchString = cursor.getString(cursor.getColumnIndex(fieldSearchString));
            String number = cursor.getString(cursor.getColumnIndex(fieldNumber));
            String testament= cursor.getString(cursor.getColumnIndex(fieldTestament));
            String chapterCount = cursor.getString(cursor.getColumnIndex(fieldChapterCount));
            String initialString = cursor.getString(cursor.getColumnIndex(fieldInitialString));
            
            book = new BookDTO(name, searchString, number, testament, chapterCount, initialString);

                
        }

        cursor.close();
        db.close();

        return book;
        
    }
    
    public boolean isIntialized() {

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

      if (cursor ==null || cursor.getCount() == 0) {
    	  db.close();
    	  return false;
      } else {
    	  cursor.close();
    	  db.close();
    	  return true;
      }

    }
    
    public BookDTO getBookByInitialString(String initialString) {

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldInitialString + " = '" + initialString + "'";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        BookDTO book = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(fieldName));
            String searchString = cursor.getString(cursor.getColumnIndex(fieldSearchString));
            String number = cursor.getString(cursor.getColumnIndex(fieldNumber));
            String testament= cursor.getString(cursor.getColumnIndex(fieldTestament));
            String chapterCount = cursor.getString(cursor.getColumnIndex(fieldChapterCount));
            
            
            book = new BookDTO(name, searchString, number, testament, chapterCount, initialString);

                
        }

        cursor.close();
        db.close();

        return book;
        
    }
    
    public ArrayList<BookDTO> getBooks(String testament) {
    	
    	ArrayList <BookDTO> books = new ArrayList<BookDTO> ();
    	
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldTestament + " = '" + testament.toUpperCase() + "'";
        sql += " ORDER BY " + fieldId + " ASC";
//        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        BookDTO book = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
	            String name = cursor.getString(cursor.getColumnIndex(fieldName));
	            String searchString = cursor.getString(cursor.getColumnIndex(fieldSearchString));
	            String number = cursor.getString(cursor.getColumnIndex(fieldNumber));
	//            String testament= cursor.getString(cursor.getColumnIndex(fieldTestament));
	            String chapterCount = cursor.getString(cursor.getColumnIndex(fieldChapterCount));
	            String initialString = cursor.getString(cursor.getColumnIndex(fieldInitialString));
	            
	            book = new BookDTO(name, searchString, number, testament.toUpperCase(), chapterCount, initialString);
	            
	            books.add(book);
            
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return books;
        
    }
    
    public BookDTO getBookByBookNumber(String bookNumber) {

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldNumber + " = '" + bookNumber + "'";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();
        
        BookDTO book = null;
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(fieldName));
            String searchString = cursor.getString(cursor.getColumnIndex(fieldSearchString));
            String number = cursor.getString(cursor.getColumnIndex(fieldNumber));
            String testament= cursor.getString(cursor.getColumnIndex(fieldTestament));
            String chapterCount = cursor.getString(cursor.getColumnIndex(fieldChapterCount));
            String initialString = cursor.getString(cursor.getColumnIndex(fieldInitialString));
            
            
            book = new BookDTO(name, searchString, number, testament, chapterCount, initialString);

                
        }

        cursor.close();
        db.close();

        return book;
        
    }
}