package com.ying.flashcard.dto;
import java.util.ArrayList;


public class QuestionDTO {
	private String id;
	

	private String title; 
	private String answer;
	private int setFK;
	
	private ArrayList<String> lines = new ArrayList<String>() ;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addLine(String line) {
		lines.add(line);
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSetFK() {
		return setFK;
	}

	public void setSetFK(int setFK) {
		this.setFK = setFK;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nQUESTION TITLE: " + title);
		sb.append("\nAnswer");
		for (int i=0; i<lines.size(); i++){
			String line = lines.get(i);
			sb.append("\n\t" + line); 
		}
		
		return sb.toString();
	}
}
