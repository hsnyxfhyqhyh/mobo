package com.ying.exercise.dto;

public class UserDTO {
	private String name;
	private String id;
	
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
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nUSER NAME: " + name);
		
		return sb.toString();
	}

}
