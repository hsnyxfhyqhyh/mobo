package com.ying.exercise.dto;

public class PerformanceDTO {
	private String id;
	private String description;
	private String machineFK;
	private String machineName;
	private String userFK;
	private String createDate;
	
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
	
	public String getMachineFK() {
		return machineFK;
	}
	public void setMachineFK(String machineFK) {
		this.machineFK = machineFK;
	}
	
	public String getUserFK() {
		return userFK;
	}
	public void setUserFK(String userFK) {
		this.userFK = userFK;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

//	public String toString(){
//		StringBuffer sb = new StringBuffer();
//		
//		sb.append("\nDay: " + name);
//		
//		return sb.toString();
//	}

}
