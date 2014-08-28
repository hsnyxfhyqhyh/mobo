package com.chccc.bible.dto;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.Context;


public class ChapterDTO {
	
	private String chapterCount;
	
	private String bookChineseName;
	
	private String bookEnglishName;
	
	private ArrayList<VerseDTO> verses;

	public String getChapterCount() {
		return chapterCount;
	}

	public void setChapterCount(String chapterCount) {
		this.chapterCount = chapterCount;
	}

	public String getBookChineseName() {
		return bookChineseName;
	}

	public void setBookChineseName(String bookChineseName) {
		this.bookChineseName = bookChineseName;
	}

	public String getBookEnglishName() {
		return bookEnglishName;
	}

	public void setBookEnglishName(String bookEnglishName) {
		this.bookEnglishName = bookEnglishName;
	}

	public ArrayList<VerseDTO> getVerses() {
		return verses;
	}

	public void setVerses(ArrayList<VerseDTO> verses) {
		this.verses = verses;
	}
}
