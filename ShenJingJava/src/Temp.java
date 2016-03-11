import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;


/********************************************************************************************************************************************************************************************************
 * Purpose: 
 * 2016-01-16: Generate empty files
 ********************************************************************************************************************************************************************************************************/

public class Temp {
	
	private static StringBuffer sb = new StringBuffer();
	
	private static int bookStartIndex = 1;
	private static int bookEndIndex = 66;
	
	
	public static void main(String[] args) {
		loadDataForVersion("niv");
		
		

		System.out.println("DONE");
	}
	
	private static void loadDataForVersion(String version) {
		try{
			for (int i=bookStartIndex; i<=bookEndIndex; i++) {
				String bookNumber = "";
				if (i<10) {
					bookNumber = "0" + i;
				} else {
					bookNumber = "" + i;
				}
				
				
				
				FileUtil.writeFile("", i + ".txt");
			}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}

	
	
	private static String getChapterVerses(ArrayList<String> bookCaptions, ArrayList<VerseDTO> verses, String chapterNumber, String bookName) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n\n\n");
		sb.append("Chapter " + chapterNumber );
		sb.append("\n");
		
		int i = 0;
		
		int chapterNum = Integer.parseInt(chapterNumber); 
		ArrayList<String> chapterCaptions = getChapterCaptions(bookCaptions, chapterNum);
		
		for (VerseDTO verse: verses) {
			try {
				i++;
				
//				int verseIndex = Integer.parseInt(verse.getVerseIndex());
				String verseIndex = verse.getVerseIndex();
				
				for (int j=0; j< chapterCaptions.size(); j ++) {
					String caption = chapterCaptions.get(j);
					if (caption.startsWith(verseIndex + ",")) {
						sb.append("\n\n===" + caption) ;
						sb.append("\n");
					}
				}
				
				sb.append("\n" + chapterNumber + ":"  + i + " " + verse.getVerseContent()) ;
				
				
				
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		}
		
		return sb.toString();
	}
	
	private static ArrayList<String> getChapterCaptions(ArrayList<String> lines, int chapterNumber) {
		ArrayList<String> returnList = new ArrayList<String>();
		
		boolean isChapterCaptionStart= false;
		
		for (int i=0; i<lines.size(); i++ ){
			String line = lines.get(i);
			
			if (line.trim().equals(new Integer(chapterNumber).toString())) {
				isChapterCaptionStart = true;
				continue;
			}
			
			if (isChapterCaptionStart) {
				if (line.trim().equalsIgnoreCase("") ) {
					break;
				} else {
					returnList.add(line);
				}
			}
		}
		
		return returnList;
	}

	
}