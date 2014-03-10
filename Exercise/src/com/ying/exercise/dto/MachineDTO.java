package com.ying.exercise.dto;

public class MachineDTO {
	private String name;
	private String id;
	private String description;
	
	private String locationFK;
	
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocationFK() {
		return locationFK;
	}
	public void setLocationFK(String locationFK) {
		this.locationFK = locationFK;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nDay: " + name);
		
		return sb.toString();
	}

}
