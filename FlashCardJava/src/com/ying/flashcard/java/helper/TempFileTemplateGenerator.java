package com.ying.flashcard.java.helper;

import com.ying.flashcard.java.util.FileUtil;

public class TempFileTemplateGenerator {
	private static final int QUESTION_COUNT = 100;
	private static final String QUESTION_START_MARKER = ">>";
	private static final String QUESTION_END_MARKER = "####";
	private static final String TEMP_FILE_NAME = "tempFile\\_template.txt";
	
	public static void main(String[] args) {
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < QUESTION_COUNT; i++ ) {
			sb.append(QUESTION_START_MARKER + "\n");
			sb.append(QUESTION_END_MARKER + "\n\n");
		}
		
		FileUtil.writeFile(sb.toString(), TEMP_FILE_NAME);
		System.out.println("DONE");
	}
}
