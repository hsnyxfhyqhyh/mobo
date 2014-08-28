package com.chccc.bible.activity;

import java.util.ArrayList;

import com.chccc.bible.R;
import com.chccc.bible.R.id;
import com.chccc.bible.R.layout;
import com.chccc.bible.R.menu;
import com.chccc.bible.R.string;
import com.chccc.bible.db.BookHandler;
import com.chccc.bible.dto.BookDTO;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class BibleBookChooserActivity extends Activity implements OnClickListener{
	
	private final int BUTTONS_PER_ROW =5;
	
	String testament = "";
	
	TableLayout bookChooserContainer;
	
	public BookHandler bookHandler;
	
	private ArrayList<BookDTO> books;
	
	public final static String EXTRA_MESSAGE = "com.chccc.bible.BookChapterChooser.MESSAGE";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biblebookchooser_activity_main);
		
		Intent intent = getIntent();

		testament = intent.getStringExtra(BibleMainActivity.EXTRA_MESSAGE);
		
		Typeface face = Typeface.createFromAsset(getAssets(), BibleMainActivity.preferences.getFontFamily());
		
		bookHandler = new BookHandler(BibleBookChooserActivity.this);
		books = bookHandler.getBooks(testament);	
		
		bookChooserContainer = (TableLayout)findViewById(R.id.bookChooserContainer);
		bookChooserContainer.removeAllViews();
		
		//add old testament
		TextView  tv= new TextView(this);
		
		if (testament.equals(BookHandler.OLD_TESTAMENT)) {
			tv.setText(R.string.text_old_testament);
		} else {
			tv.setText(R.string.text_new_testament);
		}
		
		tv.setTextSize(BibleMainActivity.preferences.getFontSizeHeader());
		
		TableRow trt = new TableRow(this);
		trt.addView(tv);
		bookChooserContainer.addView(trt);
		
		int bookCount = 0;
		
		TableRow tr = new TableRow(this);
		for (BookDTO book: books) {
			
			if (bookCount % BUTTONS_PER_ROW ==0) {
				tr = new TableRow(this);
				bookChooserContainer.addView(tr);
			}
			
			Button btn = new Button(this);
			
			btn.setText(book.getInitialString());
			
			btn.setTypeface(face);
			btn.setTextSize(BibleMainActivity.preferences.getFontSizeChapterChooser());
			btn.setOnClickListener(this);
			
			tr.addView(btn);
			
			bookCount++;
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent intentNew = new Intent(this, BibleMainActivity.class);
		this.startActivity(intentNew);
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_bible, menu);
		
		if (testament.equals(BookHandler.OLD_TESTAMENT)) {
			MenuItem oldMenu = menu.getItem(0);
			oldMenu.setVisible(false);
		} else {
			MenuItem newMenu = menu.getItem(1);
			newMenu.setVisible(false);
		}
		
		return true;
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
		
		String initialString = btn.getText().toString();
		
		Intent intent = new Intent(this, BibleBookChapterChooserActivity.class);

		// Add the text to the intent
		intent.putExtra(EXTRA_MESSAGE, initialString);

		// startActivity causes the Activity to start
		startActivity(intent);
		
		finish();
    }
}
