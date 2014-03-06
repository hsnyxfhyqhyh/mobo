package com.ying.flashcard.java.util;

import java.io.File;
import java.util.ArrayList;

import com.ying.flashcard.java.dto.QuestionDTO;
import com.ying.flashcard.java.dto.SetDTO;
import com.ying.flashcard.java.util.FileUtil;

/*
 * Convert a temp file to SetDTO to be processed later into the sqlite.
 */
public class SetLoader {
	
	public static SetDTO loadTxt (String relativeTxtFilePath, String setName) {
		
		ArrayList lines = new ArrayList();
		
		//Read the temp file into memory
		FileUtil.getContents(lines, new File(relativeTxtFilePath) );
		
		SetDTO setDTO = new SetDTO();
		setDTO.setName(setName);
		
		QuestionDTO question = new QuestionDTO();
		
		for (int i=0; i< lines.size(); i++) {
			String line = lines.get(i).toString();
			
			if (line.startsWith(">>")) {
				question = new QuestionDTO();
				String title = line.substring(2);
				question.setTitle(title);
				
				setDTO.addQuestion(question);
				
			} else if (line.startsWith("####")) {
				//This is the place holder for the original txt to xml conversion
				
			} else if (line.trim().equals("")) {
				//DON'T DO ANYTHING
			} else {
				question.addLine(line);
			}
		}
		
		return setDTO;
	}
}

