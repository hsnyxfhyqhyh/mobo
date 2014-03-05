import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadData {
	/* if isPersistable is false then write the queries to the QUERY_FILE
	 * otherwise save the data in the sqlite.
	 */
	private static boolean isPersistable = true;
	
	private static final String DB_NAME = "Bible1.db";
	private static final String QUERY_FILE = "Query-sqlite.sql";
	
	
	
	private static StringBuffer sb = new StringBuffer();
	
	public static void main(String[] args) {
		createBookTable();
		createVerseTable();
		
		insertBookData();
		
		loadDataForVersion("hhb");
		loadDataForVersion("niv");
		
		if (!isPersistable) {
			FileUtil.writeFile(sb.toString(), QUERY_FILE);
		}
		
		System.out.println("DONE");
	}
	
	private static void loadDataForVersion(String version) {
		try{
			for (int i=1; i<=66; i++) {
				String bookNumber = "";
				if (i<10) {
					bookNumber = "0" + i;
				} else {
					bookNumber = "" + i;
				}
				
				int chapterCount = ChapterXmlParser.getChapterCount(version, bookNumber);
				
				for (int j=1; j<=chapterCount; j++) {
					String chapterNumber = "" + j;
					ChapterDTO chapter = ChapterXmlParser.getChapterContent(version, bookNumber, chapterNumber);
					insertChapterVerse(chapter.getVerses(), version, chapterNumber, bookNumber);
				}
			}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}

	private static void createBookTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

			stmt = c.createStatement();

			String tableName = "book";

			String fieldId = "id";
			String fieldName = "name";
			String fieldNameEN = "nameEN";
			String fieldSearchString = "searchString";
			String fieldNumber = "number";

			String fieldTestament = "testament";
			String fieldChapterCount = "chapterCount";
			String fieldInitialString = "initialString";

			String sql = "";

			sql += "CREATE TABLE " + tableName;
			sql += " ( ";
			sql += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
			sql += fieldName + " TEXT, ";
			sql += fieldNameEN + " TEXT, ";
			sql += fieldSearchString + " TEXT, ";
			sql += fieldNumber + " TEXT, ";
			sql += fieldTestament + " TEXT, ";
			sql += fieldChapterCount + " TEXT, ";
			sql += fieldInitialString + " TEXT ";
			sql += " ); ";

			System.out.println(sql);
			if (isPersistable) {
				stmt.executeUpdate(sql);
			} else {
				sb.append(sql + "\n");
			}
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	private static void createVerseTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

			stmt = c.createStatement();

			// table details
			String tableName = "verse";

			String fieldId = "id";
			String fieldVersion = "version";
			String fieldContent = "content";
			String fieldLineNumber = "lineNumber";
			String fieldChapterNumber = "chapterNumber";
			String fieldBookNumber = "bookNumber";

			String sql = "";

			sql += "CREATE TABLE " + tableName;
			sql += " ( ";
			sql += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
			sql += fieldVersion + " TEXT, ";
			sql += fieldBookNumber + " TEXT, ";
			sql += fieldContent + " TEXT, ";
			sql += fieldLineNumber + " INTEGER, ";
			sql += fieldChapterNumber + " TEXT ";
			sql += " ); ";

			System.out.println(sql);
			if (isPersistable) {
				stmt.executeUpdate(sql);
			} else {
				sb.append(sql + "\n");
			}
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	private static void insertBookData() {
		// CREATE
		create(new BookDTO("创世纪", "Genesis", "csj", "01", "OLD", "50", "创"));
		create(new BookDTO("出埃及记", "Exodus", "cajj", "02", "OLD", "40", "出"));
		create(new BookDTO("利未记", "Leviticus", "lwj", "03", "OLD", "27", "利"));
		create(new BookDTO("民数记", "Numbers", "msj", "04", "OLD", "36", "民"));
		create(new BookDTO("申命记", "Deuteronomy", "smj", "05", "OLD", "34", "申"));
		create(new BookDTO("约书亚记", "Joshua", "ysyj", "06", "OLD", "24", "书"));
		create(new BookDTO("士师记", "Judges", "ssj", "07", "OLD", "21", "士"));
		create(new BookDTO("路得记", "Ruth", "ldj", "08", "OLD", "4", "得"));
		create(new BookDTO("撒母耳记上", "Samuel1", "smejs", "09", "OLD", "31", "撒上"));
		create(new BookDTO("撒母耳记下", "Samuel2", "smejx", "10", "OLD", "24", "撒下"));
		create(new BookDTO("列王记上", "Kings1", "lwjs", "11", "OLD", "22", "王上"));
		create(new BookDTO("列王记下", "Kings2", "lwjx", "12", "OLD", "25", "王下"));
		create(new BookDTO("历代志上", "Chronicles1", "ldzs", "13", "OLD", "29", "代上"));
		create(new BookDTO("历代志下", "Chronicles2", "ldzx", "14", "OLD", "36", "代下"));
		create(new BookDTO("以斯拉记", "Ezra", "yslj", "15", "OLD", "10", "拉"));
		create(new BookDTO("尼希米记", "Nehemiah", "nxmj", "16", "OLD", "13", "尼"));
		create(new BookDTO("以斯帖记", "Esther", "ystj", "17", "OLD", "10", "斯"));
		create(new BookDTO("约伯记", "Job", "ybj", "18", "OLD", "42", "伯"));
		create(new BookDTO("诗篇", "Psalms", "sp", "19", "OLD", "150", "诗"));
		create(new BookDTO("箴言", "Proverbs", "zy", "20", "OLD", "31", "箴"));
		create(new BookDTO("传道书", "Ecclesiastes", "cds", "21", "OLD", "12", "传"));
		create(new BookDTO("雅歌", "Song of Songs", "yg", "22", "OLD", "8", "雅"));
		create(new BookDTO("以赛亚书", "Isaiah", "ysys", "23", "OLD", "66", "赛"));
		create(new BookDTO("耶利米书", "Jeremiah", "ylms", "24", "OLD", "52", "耶"));
		create(new BookDTO("耶利米哀歌", "Lamentations", "ylmag", "25", "OLD", "5", "哀"));
		create(new BookDTO("以西结书", "Ezekiel", "yxjs", "26", "OLD", "48", "结"));
		create(new BookDTO("但以理书", "Daniel", "dyls", "27", "OLD", "12", "但"));
		create(new BookDTO("何西阿书", "Hosea", "hxas", "28", "OLD", "14", "何"));
		create(new BookDTO("约珥书", "Joel", "yes", "29", "OLD", "3", "珥"));
		create(new BookDTO("阿摩司书", "Amos", "amss", "30", "OLD", "9", "摩"));
		create(new BookDTO("俄巴底亚书", "Obadiah", "ebdys", "31", "OLD", "1", "俄"));
		create(new BookDTO("约拿书", "Jonah", "yns", "32", "OLD", "4", "拿"));
		create(new BookDTO("弥迦书", "Micah", "mjs", "33", "OLD", "7", "弥"));
		create(new BookDTO("那鸿书", "Nahum",  "nhs", "34", "OLD", "3", "鸿"));
		create(new BookDTO("哈巴谷书", "Habakkuk", "hbgs", "35", "OLD", "3", "哈"));
		create(new BookDTO("西番雅书", "Zephaniah", "xfys", "36", "OLD", "3", "番"));
		create(new BookDTO("哈该书", "Haggai", "hgs", "37", "OLD", "2", "该"));
		create(new BookDTO("撒迦利亚书", "Zechariah", "sjlys", "38", "OLD", "14", "亚"));
		create(new BookDTO("玛拉基书", "Malachi", "mljs", "39", "OLD", "4", "玛"));

		create(new BookDTO("马太福音", "Matthew", "mtfy", "40", "NEW", "28", "太"));
		create(new BookDTO("马可福音", "Mark", "mkfy", "41", "NEW", "16", "可"));
		create(new BookDTO("路加福音", "Luke", "ljfy", "42", "NEW", "24", "路"));
		create(new BookDTO("约翰福音", "John", "yhfy", "43", "NEW", "21", "约"));
		create(new BookDTO("使徒行传", "Acts", "stxz", "44", "NEW", "28", "徒"));
		create(new BookDTO("罗马书", "Romans", "lms", "45", "NEW", "16", "罗"));
		create(new BookDTO("哥林多前书", "Corinthians1", "gldqs", "46", "NEW", "16", "林前"));
		create(new BookDTO("哥林多后书", "Corinthians2", "gldhs", "47", "NEW", "13", "林后"));
		create(new BookDTO("加拉太书", "Galatians", "jlts", "48", "NEW", "6", "加"));
		create(new BookDTO("以弗所书", "Ephesians", "yfss", "49", "NEW", "6", "弗"));
		create(new BookDTO("腓立比书", "Philippians", "flbs", "50", "NEW", "4", "腓"));
		create(new BookDTO("歌罗西书", "Colossians", "glxs", "51", "NEW", "4", "西"));
		create(new BookDTO("帖撒罗尼加前书", "Thessalonians1", "tslnjqs", "52", "NEW", "5", "帖前"));
		create(new BookDTO("帖撒罗尼加后书", "Thessalonians2", "tslnjhs", "53", "NEW", "3", "帖后"));
		create(new BookDTO("提摩太前书", "Timothy1", "tmtqs", "54", "NEW", "6", "提前"));
		create(new BookDTO("提摩太后书", "Timothy2", "tmths", "55", "NEW", "4", "提后"));
		create(new BookDTO("提多书", "Titus", "tds", "56", "NEW", "3", "多"));
		create(new BookDTO("腓立门书", "Philemon", "flbs", "57", "NEW", "1", "门"));
		create(new BookDTO("希伯来书", "Hebrews", "xbls", "58", "NEW", "13", "来"));
		create(new BookDTO("雅各书", "James", "ygs", "59", "NEW", "5", "雅"));
		create(new BookDTO("彼得前书", "Peter1", "bdqs", "60", "NEW", "5", "彼前"));
		create(new BookDTO("彼得后书", "Peter2", "bdhs", "61", "NEW", "3", "彼后"));
		create(new BookDTO("约翰一书", "John1", "yhys", "62", "NEW", "5", "约1"));
		create(new BookDTO("约翰二书", "John2", "yhes", "63", "NEW", "1", "约2"));
		create(new BookDTO("约翰三书", "John3", "yhss", "64", "NEW", "1", "约3"));
		create(new BookDTO("犹大书", "Jude", "yds", "65", "NEW", "1", "犹"));
		create(new BookDTO("启示录", "Revelation", "qsl", "66", "NEW", "22", "启"));
	}

	
	private static boolean insertChapterVerse(ArrayList<VerseDTO> verses, String version, String chapterNumber, String bookNumber) {
		String fieldVersion = "version";
		String fieldContent = "content";
		String fieldLineNumber = "lineNumber";
		String fieldChapterNumber = "chapterNumber";
		String fieldBookNumber = "bookNumber";
		
		boolean createSuccessful = false;

		String tableName = "verse";

		for (VerseDTO verse: verses) {
			Connection c = null;
			Statement stmt = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
				c.setAutoCommit(false);
//				System.out.println("Opened database successfully");

				stmt = c.createStatement();
				String sql = "INSERT INTO %s (%s, %s, %s, %s, %s) "
						+ "VALUES ('%s', '%s', '%s', %s, '%s' );";

				sql = String.format(sql, tableName, fieldVersion, fieldBookNumber, fieldChapterNumber,  fieldLineNumber, fieldContent, 
						version, bookNumber, chapterNumber, verse.getVerseIndex(), verse.getVerseContent().replaceAll("'", "''"));

				System.out.println(sql);
				if (isPersistable) {
					stmt.executeUpdate(sql);
				} else {
					sb.append(sql + "\n");
				}
				
				stmt.close();
				
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		}
		

		return createSuccessful;
	}

	private static boolean create(BookDTO book) {

		boolean createSuccessful = false;

		String tableName = "book";

		String fieldName = "name";
		String fieldNameEN = "nameEN";
		String fieldSearchString = "searchString";
		String fieldNumber = "number";

		String fieldTestament = "testament";
		String fieldChapterCount = "chapterCount";
		String fieldInitialString = "initialString";

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
			c.setAutoCommit(false);
//			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) "
					+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s' );";

			sql = String.format(sql, tableName, 
					fieldName, fieldNameEN, fieldSearchString, fieldNumber, fieldTestament, fieldChapterCount, fieldInitialString, 
					book.getName(), book.getNameEN(), book.getSearchString(), book.getNumber(), book.getTestament(), book.getChapterCount(), book.getInitialString());
			
			System.out.println(sql);
			if (isPersistable) {
				stmt.executeUpdate(sql);
			} else {
				sb.append(sql + "\n");
			}
			
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
