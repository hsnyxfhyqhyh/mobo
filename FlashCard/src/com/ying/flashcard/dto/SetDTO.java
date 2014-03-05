package com.ying.flashcard.dto;
import java.util.ArrayList;



public class SetDTO {
	private String name;
	private String id;
	
	private ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addQuestion(QuestionDTO questionDTO ){
		questions.add(questionDTO);
	}
	
	public ArrayList<QuestionDTO> getQuestions() {
		return questions;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nSET NAME: " + name);
		
		for (int i=0; i<questions.size(); i++){
			QuestionDTO questionDTO = questions.get(i);
			sb.append(questionDTO.toString());
		}
		
		return sb.toString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
