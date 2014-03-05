import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlParserUtil {
	
	public static SetDTO getSetContent(String fileName) {
		SetDTO setDTO = new SetDTO();
		
		try {
			InputStream is = null;
			is = new FileInputStream (fileName);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(is));
			doc.getDocumentElement().normalize();

			NodeList setNodeList = doc.getElementsByTagName("Set");
			
			if (setNodeList !=null) {
				
				for (int i=0; i< setNodeList.getLength(); i++ ){
					Element setElement = (Element) setNodeList.item(i);
					
					String setName_ = setElement.getAttribute("name");
					setDTO.setName(setName_);
					
					NodeList questionNodeList = setElement.getElementsByTagName("Question");
					
					for (int j=0; j< questionNodeList.getLength(); j++ ){
						
						QuestionDTO questionDTO = new QuestionDTO();
						Element questionElement = (Element) questionNodeList.item(j);
												
						Element titleElement =(Element)questionElement.getElementsByTagName("Title").item(0);
						String title = titleElement.getChildNodes().item(0).getNodeValue();
						questionDTO.setTitle( title);
						
						Element AnswerElement =(Element)questionElement.getElementsByTagName("Answer").item(0);
						
						NodeList answerLineNodeList = AnswerElement.getElementsByTagName("Line");
						for (int k=0; k< answerLineNodeList.getLength(); k++ ){
							Element answerLineElement = (Element) answerLineNodeList.item(k);
							String line = answerLineElement.getChildNodes().item(0).getNodeValue();
							questionDTO.addLine(line);
							
						}
						
						setDTO.addQuestion(questionDTO);
					}

				}
				
			}
			
			

		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
		return setDTO;
	}
}
