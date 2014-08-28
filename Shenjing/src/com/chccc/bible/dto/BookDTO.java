package com.chccc.bible.dto;

public class BookDTO {
	private String name;
	private String searchString;
	private String number;
	
	private String testament;
	
	private String chapterCount;
	
	private String initialString;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public BookDTO(String name, String searchString, String number, String testament, String chapterCount, String initialString) {
		this.name= name;
		this.searchString = searchString;
		this.number = number;
		this.testament = testament;
		this.chapterCount = chapterCount;
		this.initialString = initialString;
	}
	public String getTestament() {
		return testament;
	}
	public void setTestament(String testament) {
		this.testament = testament;
	}
	public String getChapterCount() {
		return chapterCount;
	}
	public void setChapterCount(String chapterCount) {
		this.chapterCount = chapterCount;
	}
	public String getInitialString() {
		return initialString;
	}
	public void setInitialString(String initialString) {
		this.initialString = initialString;
	}
}
