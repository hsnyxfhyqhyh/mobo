package com.chccc.bible.activity;

import java.util.ArrayList;

import com.chccc.bible.activity.SettingsActivity;
import com.chccc.bible.R;
import com.chccc.bible.R.color;
import com.chccc.bible.R.id;
import com.chccc.bible.R.layout;
import com.chccc.bible.R.menu;
import com.chccc.bible.R.string;
import com.chccc.bible.db.BookHandler;
import com.chccc.bible.db.VerseHandler;
import com.chccc.bible.dto.BookDTO;
import com.chccc.bible.dto.ChapterDTO;
import com.chccc.bible.dto.VerseDTO;
import com.chccc.bible.plan.ReadBiblePlan2014;
import com.chccc.bible.plan.ReadPlanInterface;
import com.chccc.bible.util.BibleMainActivityPreferences;
import com.chccc.bible.util.ChapterXmlParser;
import com.chccc.bible.util.VersionUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BibleMainActivity extends Activity {

	private static final String VERSE_LINENUMBER_SEPARATOR = ": "; 	//The original separator is "\n"
	private static final String VERSE_LINE_SEPARATOR = "\n\n";
	
	private static final int RESULT_SETTINGS = 1;
	
	private GestureDetectorCompat mDetector; 
	public static BibleMainActivityPreferences preferences;
	
	TextView txtChapterHeader = null;
	
	MenuItem hhbMenu = null;
	MenuItem mixMenu = null;
	
	public final static String EXTRA_MESSAGE = "com.chccc.bible.BibleMainActivity.MESSAGE";

	LinearLayout bibleContainer ;
	
	public BookHandler bookHandler;
	
	private BookDTO book;
	
	String bookNumber =null;
	
	TextView textChapterContent;
	
	protected Object mActionMode;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bible_activity_main);
		
//		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		txtChapterHeader = (TextView) findViewById(R.id.txtChapterHeader);
		bibleContainer = (LinearLayout) findViewById(R.id.bibleContainer );
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		hhbMenu = menu.getItem(2);
		mixMenu = menu.getItem(3);
		
		readBible();
		return true;
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId()){
		
		case R.id.menu_chapter_chooser:
			Intent intent = new Intent(this, BibleBookChapterChooserActivity.class);
			
			bookHandler = new BookHandler(BibleMainActivity.this);
			
			String bookNumber = preferences.getBookNumber();
			book = bookHandler.getBookByBookNumber(bookNumber);
			
			// Add the text to the intent
			intent.putExtra(BibleBookChooserActivity.EXTRA_MESSAGE, book.getInitialString());
			
			this.startActivity(intent);
			this.finish();
			break;
		case R.id.menu_previous_chapter:
			
			preferences.moveToPreviousChapter();

			if (preferences.getPreferenceMessage() != null) {
				Toast.makeText(getApplicationContext(), preferences.getPreferenceMessage(), Toast.LENGTH_SHORT).show();
				preferences.resetPreferenceMessage();
			}
			
			readBible();
			
			break;
		case R.id.menu_next_chapter:
			
			preferences.moveToNextChapter();

			if (preferences.getPreferenceMessage() != null) {
				Toast.makeText(getApplicationContext(), preferences.getPreferenceMessage(), Toast.LENGTH_SHORT).show();
				preferences.resetPreferenceMessage();
			}
			
			readBible();
			
			break;
		case R.id.menu_version_hhb:
			preferences.setBibleVersion("hhb");
			preferences.commit();
			readBible();
			break;
		case R.id.menu_version_mix:
			preferences.setBibleVersion("mix");
			preferences.commit();
			readBible();
			break;
		case R.id.menu_version_niv:
			preferences.setBibleVersion("niv");
			preferences.commit();
			readBible();
			break;
		case R.id.menu_old_testament1: 
			Intent intentOld = new Intent(this, BibleBookChooserActivity.class);
			intentOld.putExtra(EXTRA_MESSAGE, BookHandler.OLD_TESTAMENT);
			this.startActivity(intentOld);
			this.finish();
			break;
			
		case R.id.menu_new_testament1: 
			Intent intentNew = new Intent(this, BibleBookChooserActivity.class);
			intentNew.putExtra(EXTRA_MESSAGE, BookHandler.NEW_TESTAMENT);
			this.startActivity(intentNew);
			this.finish();
			break;
		case R.id.menu_settings:
			 Intent intentSettings = new Intent(this, SettingsActivity.class);
	         startActivityForResult(intentSettings, RESULT_SETTINGS);
	         break;
		case R.id.menu_share:
			String selectedText = textChapterContent.getText().toString().substring(textChapterContent.getSelectionStart(), textChapterContent.getSelectionStart());
			 shareIt(selectedText);
	         break;
		case R.id.menu_version:     
			Toast.makeText(getApplicationContext(), VersionUtil.getVersionInfo(this)  , Toast.LENGTH_SHORT).show();
			break;
		}
		
		
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			readBible();
			break;
		}

	}
	
	@Override
	public void onBackPressed() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(this.getString(R.string.alert_dialog_title));

	    builder.setPositiveButton(this.getString(R.string.alert_dialog_ok_button_text), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                    dialog.dismiss();
	                    finish();
	            }
	        });

	    builder.setNegativeButton(this.getString(R.string.alert_dialog_notok_button_text), new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.dismiss();
	        }
	    });
	    AlertDialog alert = builder.create();
	    alert.show();
	}
	
	
	private void readBible() {
		try {
			
	
		bibleContainer.removeAllViews();
		
		preferences = new BibleMainActivityPreferences(this);
		
		String version = preferences.getBibleVersion();
		String bookNumber = preferences.getBookNumber();
		String chapterNumber = preferences.getChapterNumber();
		
		
		bookHandler = new BookHandler(BibleMainActivity.this);
		book = bookHandler.getBookByBookNumber(bookNumber);
		
		VerseHandler verseHandler = new VerseHandler(this);
		
		preferences.setBookTotalChapter(book.getChapterCount());
		preferences.commit();
		
		//validate the chapter choosen
		int bookTotalChapterCount = Integer.parseInt(preferences.getBookTotalChapter());
		
		txtChapterHeader.setTextSize(preferences.getFontSizeHeader());
		txtChapterHeader.setBackgroundColor(Color.parseColor(this.getString(R.color.bible_header_gray)));
		
		if (version.equalsIgnoreCase("hhb")) {
			txtChapterHeader.setText(book.getName() + " - 第" + chapterNumber + "章");	
			hhbMenu.setVisible(false);
			mixMenu.setVisible(true);
			
		} else if (version.equalsIgnoreCase("mix")) {
			txtChapterHeader.setText(book.getName() + " - 第" + chapterNumber + "章");	
			hhbMenu.setVisible(true);
			mixMenu.setVisible(false);
		
		} else if (version.equalsIgnoreCase("niv")) {
			txtChapterHeader.setText(book.getName()+ " - Chapter " + chapterNumber);
			hhbMenu.setVisible(true);
			mixMenu.setVisible(true);
		}
		
//		bibleContainer.addView(textViewBookHeader);
		
		String chapterContent = "";
		
		ArrayList<VerseDTO> versesHhb = verseHandler.getChapterVerses(chapterNumber, bookNumber, "hhb");
		ArrayList<VerseDTO> versesNiv = verseHandler.getChapterVerses(chapterNumber, bookNumber, "niv");;
		
//		for (String verse: verses) {
//			chapterContent = chapterContent + verse;
//		}
		
		for (int i=0; i< versesHhb.size(); i++) {
			if (version.equalsIgnoreCase("hhb")) {
				VerseDTO versehhb = versesHhb.get(i);
				chapterContent = chapterContent + versehhb.getVerseLineNumber() + VERSE_LINENUMBER_SEPARATOR + versehhb.getVerseContent() + VERSE_LINE_SEPARATOR;
				
			} else  if (version.equalsIgnoreCase("niv")) {
				VerseDTO verseniv = versesNiv.get(i);
				chapterContent = chapterContent + verseniv.getVerseLineNumber() + VERSE_LINENUMBER_SEPARATOR + verseniv.getVerseContent() + VERSE_LINE_SEPARATOR;
			} else  if (version.equalsIgnoreCase("mix")) {
				VerseDTO versehhb = versesHhb.get(i);
				VerseDTO verseniv = versesNiv.get(i);
				chapterContent = chapterContent + versehhb.getVerseLineNumber() +  VERSE_LINENUMBER_SEPARATOR  + versehhb.getVerseContent() + "\n";
				chapterContent = chapterContent + verseniv.getVerseContent() + VERSE_LINE_SEPARATOR;
				
			}
		}
		
		textChapterContent = new TextView(this);
		
		textChapterContent.setCustomSelectionActionModeCallback(mActionModeCallback);
		textChapterContent.setFreezesText(false);
		textChapterContent.setTextIsSelectable(true);
		textChapterContent.setTextSize(preferences.getFontSizeText());
		textChapterContent.setText(chapterContent);
		Typeface face = Typeface.createFromAsset(getAssets(), preferences.getFontFamily());
		textChapterContent.setTypeface(face);
		bibleContainer.addView(textChapterContent);
		
		ScrollView scrv = (ScrollView)findViewById(R.id.scrollView1);
		scrv.scrollTo(0, 0);
		
		} catch(Exception e) {
			Toast.makeText(getApplicationContext(), R.string.alert_generic_error_msg, Toast.LENGTH_LONG).show();
			preferences.reset();
		}
	}

	private void shareIt(String selectedText) {
        
           if(selectedText.length() == 0){
             
              String dataToShare = textChapterContent.getText().toString();
              selectedText = dataToShare;
          }

          //Share the text
          Intent sendIntent = new Intent(); 
          sendIntent.setAction(Intent.ACTION_SEND); 
          sendIntent.putExtra(Intent.EXTRA_TEXT, selectedText); 
          sendIntent.setType("text/plain"); 
          startActivity(sendIntent);
	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		 this.mDetector.onTouchEvent(event);
//	        return super.onTouchEvent(event);
//	}
//	
//	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
//        private static final String DEBUG_TAG = "Gestures"; 
//        
//        @Override
//        public boolean onDown(MotionEvent event) { 
//            Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
//            return true;
//        }
//
//        @Override
//        public boolean onFling(MotionEvent event1, MotionEvent event2, 
//                float velocityX, float velocityY) {
//            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
//            return true;
//        }
//        
//        
//    }


	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    // Called when the action mode is created; startActionMode() was called
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	      // inflate a menu resource providing context menu items
	      MenuInflater inflater = mode.getMenuInflater();
	      // assumes that you have "contexual.xml" menu resources
	      inflater.inflate(R.menu.menu_context, menu);
	      return true;
	    }

	    // called each time the action mode is shown. Always called after
	    // onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	      return false; // Return false if nothing is done
	    }

		// called when the user selects a contextual menu item
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.action_contextual_Share:
				String selectedText = textChapterContent.getText().toString().substring(textChapterContent.getSelectionStart(), textChapterContent.getSelectionEnd());

		          //if no text is selected share the entire text area.
		         
				shareIt(selectedText);
				//mode.finish(); // Action picked, so close the CAB
				return true;
			default:
				return false;
			}
		}

	    // called when the user exits the action mode
	    public void onDestroyActionMode(ActionMode mode) {
	      mActionMode = null;
	    }
	  };

}
