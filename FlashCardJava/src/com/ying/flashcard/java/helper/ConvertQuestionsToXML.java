package com.ying.flashcard.java.helper;

import java.io.File;
import java.util.ArrayList;
import com.ying.flashcard.java.util.FileUtil;

/*
 * Convert a temp file to data file to be processed later into the sqlite.
 */
public class ConvertQuestionsToXML {
	private static final String STRING_LINE_BREAK = "\n";
	private static final int INTEGER_INITIAL_LINE_COUNTER = 1;
	
	/**********************************************/
	/*THE FOLLOWING NEED TO BE MODIFIED - START	  */
	/**********************************************/
//	private static final String TEMP_FILE_NAME = "tempFile\\computer_brainstorm.txt";
//	private static final String DATA_FILE_NAME = "datafile\\computer_brainstorm.xml";
	
	private static final String TEMP_FILE_NAME = "tempFile\\bible_brainstorm.txt";
	private static final String DATA_FILE_NAME = "datafile\\bible_brainstorm.xml";
	
	private static final String SET_NAME = "computer - brainstorm";
	/**********************************************/
	/*THE FOLLOWING NEED TO BE MODIFIED - END	  */
	/**********************************************/
	
	public static void main (String args[]) {
		
		//initial counter
		int lineCounter = INTEGER_INITIAL_LINE_COUNTER;
		
		StringBuffer sb = new StringBuffer();
		
		ArrayList lines = new ArrayList();
		
		//Read the temp file into memory
		FileUtil.getContents(lines, new File(TEMP_FILE_NAME) );
		
		sb.append(String.format("<Sets>%s", STRING_LINE_BREAK));
		sb.append(String.format("<Set name=\"%s\">%s", SET_NAME, STRING_LINE_BREAK));
		for (int i=0; i< lines.size(); i++) {
			String line = lines.get(i).toString();
			
			if (line.startsWith(">>")) {
				//reset the counter
				lineCounter = INTEGER_INITIAL_LINE_COUNTER;
				
				sb.append(String.format("<Question>%s", STRING_LINE_BREAK));
				sb.append(String.format("<Title>%s</Title>%s<Answer>%s", line.substring(2), STRING_LINE_BREAK, STRING_LINE_BREAK));
				
			} else if (line.startsWith("####")) {
				sb.append(String.format("</Answer>%s</Question>%s", STRING_LINE_BREAK, STRING_LINE_BREAK ));
			} else if (line.trim().equals("")) {
				//DON'T DO ANYTHING
			} else {
				sb.append(String.format("<Line number=\"%d\">%s</Line>%s", lineCounter, line, STRING_LINE_BREAK));
				lineCounter++;
			}
		}
		
		sb.append(String.format("</Set>%s", STRING_LINE_BREAK));
		sb.append(String.format("</Sets>%s", STRING_LINE_BREAK));
		
		//Write the data file as xml.
		FileUtil.writeFile(sb.toString(), DATA_FILE_NAME);
		
		System.out.println("DONE");
	}
}

