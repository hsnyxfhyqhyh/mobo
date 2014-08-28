package com.chccc.bible.activity;

import com.chccc.bible.R;
import com.chccc.bible.R.id;
import com.chccc.bible.R.layout;
import com.chccc.bible.R.menu;
import com.chccc.bible.db.BookHandler;
import com.chccc.bible.dto.BookDTO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BibleBookChapterChooserActivity extends Activity implements OnClickListener{
	
	private final int BUTTONS_PER_ROW =5;
	
	TableLayout bookChapterChooserContainer;
	
	public BookHandler bookHandler;
	private BookDTO book;
	
	String initialString =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biblebookchapterchooser_activity_main);
		
		Intent intent = getIntent();
		Typeface face = Typeface.createFromAsset(getAssets(), BibleMainActivity.preferences.getFontFamily());

		//book Chinese intial value passed in
		initialString = intent.getStringExtra(BibleBookChooserActivity.EXTRA_MESSAGE);
		
		bookHandler = new BookHandler(BibleBookChapterChooserActivity.this);
		book = bookHandler.getBookByInitialString(initialString);
		
		int chapterCount = Integer.parseInt(book.getChapterCount());
		
		bookChapterChooserContainer = (TableLayout)findViewById(R.id.bookChapterChooserContainer);
		bookChapterChooserContainer.removeAllViews();
		
		TextView  tv= new TextView(this);
		tv.setText(book.getName());
		
		tv.setTextSize(BibleMainActivity.preferences.getFontSizeHeader());

		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = BUTTONS_PER_ROW;
		
		TableRow trt = new TableRow(this);
		trt.addView(tv, 0, params);
		bookChapterChooserContainer.addView(trt);
		
		TableRow tr = new TableRow(this);
		for (int i = 1; i<= chapterCount; i++) {
			
			if (i % BUTTONS_PER_ROW ==1) {
				tr = new TableRow(this);
				bookChapterChooserContainer.addView(tr);
			}
			
			Button btn = new Button(this);
			btn.setText(i + "");
			btn.setTypeface(face);
			btn.setTextSize(BibleMainActivity.preferences.getFontSizeChapterChooser());
			
			btn.setOnClickListener(this);
			
			tr.addView(btn);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_bible, menu);
		
		return true;
	}

	@Override
	public void onBackPressed() {
		Intent intentNew = new Intent(this, BibleMainActivity.class);
		this.startActivity(intentNew);
		this.finish();
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId()){
		
		case R.id.menu_old_testament:
			Intent intentOld = new Intent(this, BibleBookChooserActivity.class);
			intentOld.putExtra(BibleMainActivity.EXTRA_MESSAGE, BookHandler.OLD_TESTAMENT);
			this.startActivity(intentOld);
			
			this.finish();
			
			break;
		case R.id.menu_new_testament:
			Intent intentNew = new Intent(this, BibleBookChooserActivity.class);
			intentNew.putExtra(BibleMainActivity.EXTRA_MESSAGE, BookHandler.NEW_TESTAMENT);
			this.startActivity(intentNew);
			
			this.finish();
			break;
		}
			
		return true;
	}
	
	@Override
    public void onClick(View v) {
		Button btn = (Button) v;
		
		BookDTO book = bookHandler.getBookByInitialString(initialString);
		
        BibleMainActivity.preferences.setBookNumber(book.getNumber());
        BibleMainActivity.preferences.setChapterNumber(btn.getText().toString());
        BibleMainActivity.preferences.commit();
		Intent intent = new Intent(this, BibleMainActivity.class);
		startActivity(intent);
		finish();
    }
}
