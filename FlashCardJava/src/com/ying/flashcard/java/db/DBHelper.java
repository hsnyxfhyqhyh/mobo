package com.ying.flashcard.java.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ying.flashcard.java.dto.QuestionDTO;
import com.ying.flashcard.java.dto.SetDTO;

/*
 * 
 */
public class DBHelper {
	public static final String DB_NAME = "FlashCard.db";
	public static final String JDBC_CLASS_STRING = "org.sqlite.JDBC"; 
	public static final String JDBC_CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
	
	private static final String TABLE_NAME_USER = "User";
	private static final String TABLE_NAME_CATEGORY = "FlashCardCategory";
	private static final String TABLE_NAME_SET = "FlashCardSet";
	private static final String TABLE_NAME_QUESTION = "FlashCardSetQuestions";
	
	public static void initializeDB() {
		createUserTable();
		addUser("影辉");
		addUser("雪梅");
		addUser("宁宁");
		addUser("小玉");
		
		createCategoryTable();
		createSetTable();
		createQuestionTable();
	}
	
	private static void createCategoryTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_CLASS_STRING);
			c = DriverManager.getConnection(JDBC_CONNECTION_STRING);

			stmt = c.createStatement();


			String fieldId = "id";
			String fieldName = "name";
			String fieldUserFK = "userFK";

			String sql = "";

			sql += "CREATE TABLE " + TABLE_NAME_CATEGORY;
			sql += " ( ";
			sql += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
			sql += fieldName + " TEXT, ";
			sql += fieldUserFK + " INTEGER ";
			sql += " ); ";

			stmt.executeUpdate(sql);
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	private static void createUserTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_CLASS_STRING);
			c = DriverManager.getConnection(JDBC_CONNECTION_STRING);

			stmt = c.createStatement();


			String fieldId = "id";
			String fieldName = "name";

			String sql = "";

			sql += "CREATE TABLE " + TABLE_NAME_USER;
			sql += " ( ";
			sql += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
			sql += fieldName + " TEXT ";
			sql += " ); ";

			stmt.executeUpdate(sql);
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	
	private static void createSetTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_CLASS_STRING);
			c = DriverManager.getConnection(JDBC_CONNECTION_STRING);

			stmt = c.createStatement();

			String fieldId = "id";
			String fieldName = "name";
			String fieldCategoryID = "categoryFK";

			String sql = "";

			sql += "CREATE TABLE " + TABLE_NAME_SET;
			sql += " ( ";
			sql += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
			sql += fieldName + " TEXT, ";
			sql += fieldCategoryID + " INTEGER ";
			sql += " ); ";

			stmt.executeUpdate(sql);
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	private static void createQuestionTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_CLASS_STRING);
			c = DriverManager.getConnection(JDBC_CONNECTION_STRING);

			stmt = c.createStatement();

			String fieldId = "id";
			String fieldTitle = "Title";
			String fieldAnswers = "Answers";
			String fieldSetID = "setFK";

			String sql = "";

			sql += "CREATE TABLE " + TABLE_NAME_QUESTION;
			sql += " ( ";
			sql += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
			sql += fieldTitle + " TEXT, ";
			sql += fieldAnswers + " TEXT, ";
			sql += fieldSetID + " INTEGER ";
			sql += " ); ";

			stmt.executeUpdate(sql);
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public static int getUserID(String userName_) {
		int userId = 0;
		String fieldName = "name";
		String fieldId = "id";

		// select query
		String sql = "";
		sql += "SELECT * FROM " + TABLE_NAME_USER;
		sql += " WHERE " + fieldName + " = '" + userName_ + "'";
		sql += " LIMIT 0,5";

		try {
			Class.forName(DBHelper.JDBC_CLASS_STRING);
			Connection c = DriverManager.getConnection(DBHelper.JDBC_CONNECTION_STRING);
			c.setAutoCommit(false);

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				userId = rs.getInt(fieldId);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		
		System.out.println(userName_ + " has user ID " + userId);
				
		return userId;
	}
	
	
	public static int getCategoryID(String categoryName_, String userFK) {
		int categoryFK = 0;
		String fieldName = "name";
		String fieldId = "id";
		String fieldUserFK = "userFK";

		// select query
		String sql = "";
		sql += "SELECT * FROM " + TABLE_NAME_CATEGORY;
		sql += " WHERE " + fieldName + " = '" + categoryName_ + "' ";
		sql += " AND " + fieldUserFK + " = " + userFK + "";
		sql += " LIMIT 0,5";

		try {
			Class.forName(DBHelper.JDBC_CLASS_STRING);
			Connection c = DriverManager.getConnection(DBHelper.JDBC_CONNECTION_STRING);
			c.setAutoCommit(false);

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				categoryFK = rs.getInt(fieldId);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		
		System.out.println(categoryName_ + " has category ID " + categoryFK);
				
		return categoryFK;
	}
	
	
	public static int getSetFK(String setName_) {
		int setFK = 0;
		String tableName = "FlashCardSet";
		String fieldName = "name";
		String fieldId = "id";

		// select query
		String sql = "";
		sql += "SELECT * FROM " + tableName;
		sql += " WHERE " + fieldName + " = '" + setName_ + "'";
		sql += " LIMIT 0,5";

		try {
			Class.forName(DBHelper.JDBC_CLASS_STRING);
			Connection c = DriverManager.getConnection(DBHelper.JDBC_CONNECTION_STRING);
			c.setAutoCommit(false);

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				setFK = rs.getInt(fieldId);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		System.out.println(setName_ + " has SET ID " + setFK);
		return setFK;
	}
	
	
	public static boolean addUser(String userName) {
		boolean createSuccessful = false;
		
		String fieldId = "id";
		String fieldName = "name";

		Statement stmt = null;
		try {
			Class.forName(DBHelper.JDBC_CLASS_STRING);
			Connection c = DriverManager.getConnection(DBHelper.JDBC_CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO %s (%s) " + "VALUES ('%s');";

			sql = String.format(sql, TABLE_NAME_USER, fieldName, userName);

			System.out.println(sql);
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
			createSuccessful = true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return createSuccessful;
	}
	
	public static boolean addCategory(String categoryName, String userFK) {
		boolean createSuccessful = false;
		
		String fieldId = "id";
		String fieldName = "name";
		String fieldUserFK = "userFK";
		
		Statement stmt = null;
		try {
			Class.forName(DBHelper.JDBC_CLASS_STRING);
			Connection c = DriverManager.getConnection(DBHelper.JDBC_CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO %s (%s, %s) " + "VALUES ('%s', %s);";

			sql = String.format(sql, TABLE_NAME_CATEGORY, fieldName, fieldUserFK, categoryName, userFK);

			System.out.println(sql);
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
			createSuccessful = true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return createSuccessful;
	}

	
	public static void addSet(SetDTO setDTO, int categoryFK) {

		String fieldId = "id";
		String fieldName = "name";
		String fieldCategoryFK = "categoryFK";

		int setFK = setExists(setDTO.getName(), categoryFK);
		if (setFK > 0 ) {
			for (int i = 0; i < setDTO.getQuestions().size(); i++) {
				insertQuestion(setDTO.getQuestions().get(i), setFK);
			}
			
			return;
		} 
		
		Statement stmt = null;
		try {
			Class.forName(DBHelper.JDBC_CLASS_STRING);
			Connection c = DriverManager.getConnection(DBHelper.JDBC_CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO %s (%s, %s) " + "VALUES ('%s', %s);";

			sql = String.format(sql, TABLE_NAME_SET, fieldName, fieldCategoryFK, setDTO.getName(), categoryFK);

			System.out.println(sql);
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();

			setFK = setExists(setDTO.getName(), categoryFK);

			for (int i = 0; i < setDTO.getQuestions().size(); i++) {
				insertQuestion(setDTO.getQuestions().get(i), setFK);
			}
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}
	
	private static int setExists(String setName_, int categoryFK) {
		int setFK = 0;
		String tableName = "FlashCardSet";
		String fieldName = "name";
		String fieldId = "id";
		String fieldCategoryID = "categoryFK";
		
		// select query
		String sql = "";
		sql += "SELECT * FROM " + tableName;
		sql += " WHERE " + fieldName + " = '" + setName_ + "'";
		sql += " AND " + fieldCategoryID + " = " + categoryFK + "";
		sql += " LIMIT 0,5";

		try {
			Class.forName(DBHelper.JDBC_CLASS_STRING);
			Connection c = DriverManager.getConnection(DBHelper.JDBC_CONNECTION_STRING);
			c.setAutoCommit(false);

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				setFK = rs.getInt(fieldId);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			setFK = -1;
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return setFK;
	}

	

	private static boolean insertQuestion(QuestionDTO questionDTO, int setFK) {

		boolean createSuccessful = false;

		String tableName = "FlashCardSetQuestions";

		String fieldId = "id";
		String fieldTitle = "Title";
		String fieldAnswers = "Answers";
		String fieldSetID = "setFK";

		Statement stmt = null;
		try {
			Class.forName(DBHelper.JDBC_CLASS_STRING);
			Connection c = DriverManager.getConnection(DBHelper.JDBC_CONNECTION_STRING);
			c.setAutoCommit(false);
			// System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "INSERT INTO %s (%s, %s, %s) "
					+ "VALUES ('%s', '%s', %s);";

			sql = String.format(sql, tableName, fieldTitle, fieldAnswers,
					fieldSetID, questionDTO.getTitle().replaceAll("'", "''"),
					questionDTO.getAnswer().replaceAll("'", "''"), setFK);

			System.out.println(sql);
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return createSuccessful;
	}
}
