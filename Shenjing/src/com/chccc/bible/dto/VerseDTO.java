package com.chccc.bible.dto;

public class VerseDTO {
	
	private String verseContent;
	private String verseLineNumber;
	
	private String chapterNumber;
	
	private String bookNumber;
	
	
	
	public VerseDTO(String verseContent, String verseLineNumber, String chapterNumber, String bookNumber) {
		this.verseContent = verseContent;
		this.verseLineNumber = verseLineNumber;
		this.chapterNumber = chapterNumber;
		this.bookNumber = bookNumber;
	}
	
	public String getVerseContent() {
		return verseContent;
	}
	public void setVerseContent(String verseContent) {
		this.verseContent = verseContent;
	}

	public String getVerseLineNumber() {
		return verseLineNumber;
	}

	public void setVerseLineNumber(String verseLineNumber) {
		this.verseLineNumber = verseLineNumber;
	}

	public String getChapterNumber() {
		return chapterNumber;
	}

	public void setChapterNumber(String chapterNumber) {
		this.chapterNumber = chapterNumber;
	}

	public String getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}
	
	
}
