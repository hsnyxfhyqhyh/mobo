import java.util.ArrayList;

public class XmlToJson {
	private static StringBuffer sb = new StringBuffer();
	
	public static void main(String[] args) {
		ArrayList<String> versions = new ArrayList<String>();
		versions.add("hhb");
//		versions.add("kjv");
		versions.add("niv");
		
		loadDataForVersion(versions);

		System.out.println("DONE");
	}
	
	private static void loadDataForVersion(ArrayList<String> versions) {
		
		String version = "hhb";		//default version to retrieve common info
		
		String lastVersion = versions.get((versions.size()-1));
		
		try{
			for (int i=1; i<=66; i++) {
				String bookNumber = "";
				if (i<10) {
					bookNumber = "0" + i;
				} else {
					bookNumber = "" + i;
				}
				
				StringBuffer sb = new StringBuffer();
				
				int chapterCount = ChapterXmlParser.getChapterCount(version, bookNumber);
				
				ChapterDTO chapter1 = ChapterXmlParser.getChapterContent(version, bookNumber, "1");
				
				sb.append("<script>\n");
				sb.append("var chapterData = {\n");
				
				sb.append("\t\"BookName\": \"" + chapter1.getBookChineseName() + "\",\n");
				sb.append("\t\"BookName_En\": \"" + chapter1.getBookEnglishName() + "\",\n");
				sb.append("\t\"BookNumber\": \"" + bookNumber + "\",\n");
				sb.append("\t\"ChapterCount\": \"" + chapterCount + "\",\n");
				sb.append("\t\"Chapters\": [\n");
				
				for (int j=1; j<=chapterCount; j++) {
					String chapterNumber = "" + j;
					chapter1 = ChapterXmlParser.getChapterContent(version, bookNumber, chapterNumber);
					
					sb.append("\t\t{\n");
//					sb.append("\t\t\"BookNumber\": \"" + bookNumber + "\",\n");
					sb.append("\t\t\t\"BookName\": \"" + chapter1.getBookChineseName() + "\",\n");
					sb.append("\t\t\t\"BookName_En\": \"" + chapter1.getBookEnglishName() + "\",\n");
					sb.append("\t\t\t\"ChapterNumber\": \"" + chapterNumber + "\",\n");
					sb.append("\t\t\t\"ChapterCount\": \"" + chapterCount + "\",\n");
					sb.append("\t\t\t\"VerseCount\": \"" + chapter1.getVerses().size() + "\",\n");
					sb.append("\t\t\t\"Versions\": [\n");
					
					//start versions: 
					for (String v: versions) {
						ChapterDTO chapter = ChapterXmlParser.getChapterContent(v, bookNumber, chapterNumber);
						
						sb.append("\t\t\t\t{\n");
						sb.append("\t\t\t\t\t\"VersionName\": \"" + v.toString() + "\",\n");
						sb.append("\t\t\t\t\t\"Verses\": [\n");
						
						int verseCounter = 0;
						while (verseCounter < chapter.getVerses().size()) {
							sb.append("\t\t\t\t\t\t{\n");
							
							String verseNumber = "" + (verseCounter +1);
							sb.append("\t\t\t\t\t\t\t\"VerseNumber\": \"" + verseNumber + "\",\n");
							sb.append("\t\t\t\t\t\t\t\"VerseContent\": \"" + getChapterVerse (chapter.getVerses(), verseCounter).replace("\"", "'") + "\"\n");
							
							if (verseCounter < chapter.getVerses().size()-1){
								sb.append("\t\t\t\t\t\t},\n");	
							} else {
								sb.append("\t\t\t\t\t\t}\n");
							}
								
							verseCounter++;
						}
						
						sb.append("\t\t\t\t\t]\n");  //end verses
						
						if (v.equalsIgnoreCase(lastVersion)){
							sb.append("\t\t\t\t}\n");	//end current version
						} else {
							sb.append("\t\t\t\t},\n");	//end current version	
						}
						
					}
					
					sb.append("\t\t\t]\n");	//end versions.
					
					if (j==chapterCount){
						sb.append("\t\t}\n");
					} else {
						sb.append("\t\t},\n");
					}
					
					
				}
				sb.append("\t]\n");		//end chapters
				sb.append("}\n");
				String bookFileNamePrefix ="";
				if (i<10) {
					bookFileNamePrefix = "0" + i;
				} else {
					bookFileNamePrefix = "" + i;
				}
				FileUtil.writeFile(sb.toString(), bookFileNamePrefix + ".js");
			}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}

	private static String getChapterVerse (ArrayList<VerseDTO> verses, int verseCounter) {
		return (verses.get(verseCounter).getVerseContent());
		
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
