

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



public class VerseDTO {
	
	private String verseContent;
	private String verseIndex;
	
	
	public VerseDTO(String verseContent, String verseIndex) {
		this.verseContent = verseContent;
		this.verseIndex = verseIndex;
	}
	
	public String getVerseContent() {
		return verseContent;
	}
	public void setVerseContent(String verseContent) {
		this.verseContent = verseContent;
	}
	public String getVerseIndex() {
		return verseIndex;
	}
	public void setVerseIndex(String verseIndex) {
		this.verseIndex = verseIndex;
	}
	
	
}
