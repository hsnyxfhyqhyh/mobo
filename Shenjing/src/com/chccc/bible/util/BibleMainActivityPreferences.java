package com.chccc.bible.util;

import com.chccc.bible.activity.BibleMainActivity;
import com.chccc.bible.db.BookHandler;
import com.chccc.bible.dto.BookDTO;

import android.content.SharedPreferences;

public class BibleMainActivityPreferences {
	
	public static final String PREFS_NAME = "com.chccc.bible";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	private String bibleVersion = "";
	private String bookNumber = "";
	private String chapterNumber = "";
	
	private int fontSizeText = 20;
	
	private int fontSizeChapterChooser = 18;
	
	public int getFontSizeChapterChooser() {
		return Integer.parseInt(settings.getString("prefFontSizeChapterChooser", "18"));
	}

	public int getFontSizeText() {
		return Integer.parseInt(settings.getString("prefFontSizeTextView", "20"));
	}

//	public void setFontSizeText(int fontSizeText) {
//		this.fontSizeText = fontSizeText;
//	}

	private String fontFamily = "fonts/STKAITI.TTF";
	
	
	
	
	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	private int fontSizeHeader = 30;
	
	public int getFontSizeHeader() {
		return Integer.parseInt(settings.getString("prefFontSizeHeader", "30"));
	}

//	public void setFontSizeHeader(int fontSizeHeader) {
//		this.fontSizeHeader = fontSizeHeader;
//	}

	private String preferenceMessage = null;
	
	private String bookTotalChapter = "";
	
	private String theme = "";
	
	private BibleMainActivity bibleMainActivity;
	
	public BibleMainActivityPreferences(BibleMainActivity bibleMainActivity) {
		this.bibleMainActivity = bibleMainActivity;
		
		settings = bibleMainActivity.getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		
		if (!settings.getBoolean("preference_setted", false)) {
			editor.putString("BibleVersion", "hhb");
			editor.putString("bookNumber", "40");
			editor.putString("chapterNumber", "1");
			editor.putBoolean("preference_setted", true);
		}
	}
	
	public String getPreferenceMessage() {
		return preferenceMessage;
	}

	public String getBibleVersion() {
		return settings.getString("BibleVersion", "hhb");
	}

	public void setBibleVersion(String bibleVersion) {
		editor.putString("BibleVersion", bibleVersion);
	}

	public String getBookNumber() {
		return settings.getString("bookNumber", "40");
	}

	public void setBookNumber(String bookNumber) {
		editor.putString("bookNumber", bookNumber);
	}

	public String getChapterNumber() {
		return settings.getString("chapterNumber", "1");
	}

	public void setChapterNumber(String chapterNumber) {
		editor.putString("chapterNumber", chapterNumber);
	}

	public String getTheme() {
		return settings.getString("theme", "");
	}

	public void setTheme(String theme) {
		editor.putString("theme", theme);
	}
	
	public String getBookTotalChapter() {
		return settings.getString("bookTotalChapter", bookTotalChapter);
	}

	public void setBookTotalChapter(String bookTotalChapter) {
		editor.putString("bookTotalChapter", bookTotalChapter);
	}

	public void commit() {
		editor.commit();
	}
	
	public void reset() {
		setChapterNumber("1");
		setBibleVersion("hhb");
        setBookNumber("01");
        
		commit();
	}
	
	public void moveToPreviousChapter() {
		BookHandler bookHandler = new BookHandler(bibleMainActivity);
		
		String currentBookNumber = getBookNumber();
		String currentBookChapterNumber = getChapterNumber();
		
		if (currentBookNumber.equals("01") && currentBookChapterNumber.equals("1")) {
			//Situation: Genesis chapter 1 
			//Solution: do nothing
			preferenceMessage = "前面没有任何章节";
		} else {
			int iChapter = Integer.parseInt(currentBookChapterNumber);
			
			if (iChapter!=1) {
				//Situation: chapter other than the 1st chapter of the current book 
				//Solution: simply move the chapter number to the previous one.
				iChapter--;
				setChapterNumber("" + iChapter);
				commit();
			} else {
				//Situation: first chapter of a book other then booknumber="01". 
				//Solution: move currentBookNumber to the previous book && then get the maximum chapter number to the previous book 
				
				int iBookNumber = getCurrentBookNumber(currentBookNumber);
				
				//previous booknumber
				String newBookNumber = paddingLeadingZeroToBookNumber(--iBookNumber);
				setBookNumber(newBookNumber);
				
				BookDTO book = bookHandler.getBookByBookNumber(newBookNumber);
				
				setChapterNumber(book.getChapterCount());
				
				preferenceMessage = "进入前一本书";
				commit();
			}
		}
	}
	
	public void moveToNextChapter() {
		BookHandler bookHandler = new BookHandler(bibleMainActivity);
		
		String currentBookNumber = getBookNumber();
		String currentBookChapterNumber = getChapterNumber();
		
		if (currentBookNumber.equals("66") && currentBookChapterNumber.equals("22")) {
			//Situation: Revelation chapter 22, which is the last chapter of revelation
			//Solution: do nothing
			preferenceMessage = "后面没有任何章节";
		} else {
			int iChapter = Integer.parseInt(currentBookChapterNumber);
			
			BookDTO currentbook = bookHandler.getBookByBookNumber(currentBookNumber);
			if (iChapter < Integer.parseInt(currentbook.getChapterCount())) {
				//Situation: chapter is not the last chapter of the current book 
				//Solution: simply move the chapter number to the next chapter.
				iChapter++;
				setChapterNumber("" + iChapter);
				commit();
			} else {
				//Situation: chapter is the last chapter of the current book 
				//Solution: simply move the chapter number to chapter 1 and move the book to the next book.
				setChapterNumber("1");
				
				int iBookNumber = getCurrentBookNumber(currentBookNumber);
				String newBookNumber = paddingLeadingZeroToBookNumber(++iBookNumber);
				setBookNumber(newBookNumber);
				preferenceMessage = "进入后一本书";
				commit();
			}
			
		}
	}
	
	private int getCurrentBookNumber (String currentBookNumber) {
		if (currentBookNumber.startsWith("0")){
			currentBookNumber = currentBookNumber.substring(1);
		}
		
		return Integer.parseInt(currentBookNumber);
	}
	
	private String paddingLeadingZeroToBookNumber(int currentBookNumber){
		if (currentBookNumber < 10 ){
			return "0" + currentBookNumber;
		} else {
			return "" + currentBookNumber;
		}
	}
	
	public void resetPreferenceMessage() {
		preferenceMessage = null;
	}
}
