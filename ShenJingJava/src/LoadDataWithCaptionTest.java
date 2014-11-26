import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;


/********************************************************************************************************************************************************************************************************
 * Purpose: 
 * 2014-09-29: to write out the book content. 
 ********************************************************************************************************************************************************************************************************/

public class LoadDataWithCaptionTest {
	
	private static StringBuffer sb = new StringBuffer();
	
	private static int chapterStartIndex = 33;
	private static int chapterEndIndex = 37;
	
	
	public static void main(String[] args) {
		loadDataForVersion("hhb");
		
		

		System.out.println("DONE");
	}
	
	private static void loadDataForVersion(String version) {
		try{
			for (int i=chapterStartIndex; i<=chapterEndIndex; i++) {
				String bookNumber = "";
				if (i<10) {
					bookNumber = "0" + i;
				} else {
					bookNumber = "" + i;
				}
				
				//lines below will have the caption content
				File fCaption = new File("caption/hhb/" + bookNumber + ".txt");
				ArrayList<String> bookCaptions = new ArrayList<String>();
				FileUtil.getContents(bookCaptions, fCaption);
				
				StringBuffer sb = new StringBuffer();
				
				int chapterCount = ChapterXmlParser.getChapterCount(version, bookNumber);
				
				for (int j=1; j<=chapterCount; j++) {
					String chapterNumber = "" + j;
					ChapterDTO chapter = ChapterXmlParser.getChapterContent(version, bookNumber, chapterNumber);
					sb.append(getChapterVerses(bookCaptions, chapter.getVerses(), chapterNumber, ChapterXmlParser.getBookName(bookNumber)));
				}
				
				FileUtil.writeFile(sb.toString(), i + ".txt");
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
		sb.append(bookName + " - 第" + chapterNumber + "章" );
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
				
				sb.append("\n" + i + ": " + verse.getVerseContent()) ;
				
				
				
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
