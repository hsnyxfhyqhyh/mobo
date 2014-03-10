package com.ying.exercise.dto;

public class DayDTO {
	private String name;
	private String id;
	private String userFK;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserFK() {
		return userFK;
	}
	public void setUserFK(String userFK) {
		this.userFK = userFK;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nDay: " + name);
		
		return sb.toString();
	}

}
