import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ChapterXmlParser {
	
	public static ChapterDTO getChapterContent(String bookVersion, String bookNumber, String chapterNumber) {
		ArrayList<VerseDTO> lines = new ArrayList();
		
		ChapterDTO chapter = new ChapterDTO();
		
		try {
			InputStream is = null;
			is = new FileInputStream (String.format("data/%s/%s%s.xml", bookVersion, bookVersion, bookNumber));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(is));
			doc.getDocumentElement().normalize();

			NodeList bookNodeList = doc.getElementsByTagName("Book");
			
			if (bookNodeList !=null) {
				Element bookElement = (Element) bookNodeList.item(0);
				
				chapter.setBookChineseName(bookElement.getAttribute("name"));
				chapter.setBookEnglishName(bookElement.getAttribute("englishName"));
				chapter.setChapterCount(bookElement.getAttribute("chapters"));

				NodeList chapterNodeList = bookElement.getElementsByTagName("Chapter");
				
				for (int i = 0; i < chapterNodeList.getLength(); i++) {
					Element chapterElement = (Element) chapterNodeList.item(i);
					String chapterNumberT = chapterElement.getAttribute("number");
					if (chapterNumberT.equals(chapterNumber)) {
						NodeList lineNodeList = chapterElement.getElementsByTagName("Line");
						for (int j = 0; j < lineNodeList.getLength(); j++) {
							Element LineElement = (Element) lineNodeList.item(j);
							String lineNumber = LineElement.getAttribute("number");
							String lineContent = lineNodeList.item(j).getChildNodes().item(0).getNodeValue();
							VerseDTO verse = new VerseDTO(lineContent, lineNumber);
							lines.add(verse);
						}
						
						break;
					}
				}
			}
			
			chapter.setVerses(lines);
			

		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
		return chapter;
	}
	
	public static int getChapterCount(String bookVersion, String bookNumber) {
		ArrayList<VerseDTO> lines = new ArrayList();
		
		int chapterCount = 0;
		
		ChapterDTO chapter = new ChapterDTO();
		
		try {
			InputStream is = null;
			is = new FileInputStream (String.format("data/%s/%s%s.xml", bookVersion, bookVersion, bookNumber));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(is));
			doc.getDocumentElement().normalize();

			NodeList bookNodeList = doc.getElementsByTagName("Book");
			
			if (bookNodeList !=null) {
				Element bookElement = (Element) bookNodeList.item(0);
				
				chapter.setBookChineseName(bookElement.getAttribute("name"));
				chapter.setBookEnglishName(bookElement.getAttribute("englishName"));
				chapterCount = Integer.parseInt(bookElement.getAttribute("chapters"));
				
			}
			
			chapter.setVerses(lines);
			

		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
		return chapterCount;
	}
}
