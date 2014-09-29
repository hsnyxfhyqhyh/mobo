import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;


/********************************************************************************************************************************************************************************************************
 * Purpose: 
 * 2014-09-29: to write out the book content. 
 ********************************************************************************************************************************************************************************************************/

public class LoadDataTest {
	/* if isPersistable is false then write the queries to the QUERY_FILE
	 * otherwise save the data in the sqlite.
	 */
	private static boolean isPersistable = true;
	
	private static StringBuffer sb = new StringBuffer();
	
	public static void main(String[] args) {
		loadDataForVersion("hhb");

		System.out.println("DONE");
	}
	
	private static void loadDataForVersion(String version) {
		try{
			for (int i=11; i<=66; i++) {
				String bookNumber = "";
				if (i<10) {
					bookNumber = "0" + i;
				} else {
					bookNumber = "" + i;
				}
				
				StringBuffer sb = new StringBuffer();
				
				int chapterCount = ChapterXmlParser.getChapterCount(version, bookNumber);
				
				for (int j=1; j<=chapterCount; j++) {
					String chapterNumber = "" + j;
					ChapterDTO chapter = ChapterXmlParser.getChapterContent(version, bookNumber, chapterNumber);
					sb.append(getChapterVerses(chapter.getVerses(), chapterNumber, ChapterXmlParser.getBookName(bookNumber)));
				}
				
				FileUtil.writeFile(sb.toString(), i + ".txt");
			}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}

	
	
	private static String getChapterVerses(ArrayList<VerseDTO> verses, String chapterNumber, String bookName) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n");
		sb.append(bookName + " - 第" + chapterNumber + "章" );
		sb.append("\n");
		
		int i = 0;
		for (VerseDTO verse: verses) {
			try {
				i++;
				
				sb.append("\n" + i + ": " + verse.getVerseContent()) ;
				
				
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		}
		
		return sb.toString();
	}

	
}
