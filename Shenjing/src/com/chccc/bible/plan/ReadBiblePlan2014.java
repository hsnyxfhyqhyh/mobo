package com.chccc.bible.plan;

import java.util.ArrayList;

import android.content.Context;

import com.chccc.bible.db.BookHandler;
import com.chccc.bible.dto.BookDTO;
import com.chccc.bible.util.BibleDate;

public class ReadBiblePlan2014 implements ReadPlanInterface{

	int oldDaily = 3;
	int newDaily = 1;
	
	@Override
	public ArrayList<String> getChaptersByDate(Context context, String date) {
		ArrayList alResult  = new ArrayList();
		
//		String weekDay = BibleDate.getDayOfWeek(date);
		
//		if (weekDay.equalsIgnoreCase("Saturday") || weekDay.equalsIgnoreCase("Sunday")) {
//			return null;
//		}
		
		int bibleDay = BibleDate.totalDaysToTheDay(date);
		
		BookHandler bookHandler = new BookHandler(context);
		
		ArrayList<BookDTO> oldbooks = bookHandler.getBooks(BookHandler.OLD_TESTAMENT);
		ArrayList<BookDTO> newbooks = bookHandler.getBooks(BookHandler.NEW_TESTAMENT);
		
		ArrayList<String> alChaptersOld = new ArrayList<String>();
		ArrayList<String> alChaptersNew = new ArrayList<String>();
		
		for (BookDTO book: oldbooks) {
			String bookNumber = book.getNumber();
			
			int chapterCount = Integer.parseInt(book.getChapterCount());
			
			for (int i=1; i<= chapterCount; i++){
				alChaptersOld.add(bookNumber + "#" + i);
			}
			
		}
		
		for (BookDTO book: newbooks) {
			String bookNumber = book.getNumber();
			
			int chapterCount = Integer.parseInt(book.getChapterCount());
			
			for (int i=1; i<= chapterCount; i++){
				alChaptersNew.add(bookNumber + "#" + i);
			}
			
		}
		
		
		int startIndexOld =  oldDaily * (bibleDay-1);
		
		if (startIndexOld < alChaptersOld.size()) {
			
			for (int i=0; i< oldDaily; i++) {
				alResult.add(alChaptersOld.get(startIndexOld));
				startIndexOld++;
				
				if (startIndexOld > alChaptersOld.size()) {
					break;
				}
			}
			
		}
		
		int startIndexNew =  newDaily * (bibleDay-1);
		
		if (startIndexNew < alChaptersNew.size()) {
			
			for (int i=0; i< newDaily; i++) {
				alResult.add(alChaptersNew.get(startIndexNew));
				startIndexNew++;
				
				if (startIndexNew > alChaptersNew.size()) {
					break;
				}
			}
		}

		
		
		return alResult;
	}

}
