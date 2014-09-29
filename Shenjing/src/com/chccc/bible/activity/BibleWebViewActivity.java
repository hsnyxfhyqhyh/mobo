package com.chccc.bible.activity;

import java.io.File;
import java.util.ArrayList;

import com.chccc.bible.R;
import com.chccc.bible.R.id;
import com.chccc.bible.R.layout;
import com.chccc.bible.R.menu;
import com.chccc.bible.R.string;
import com.chccc.bible.db.BookHandler;
import com.chccc.bible.dto.BookDTO;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BibleWebViewActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.chccc.bible.BibleWebView.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bible_webview_activity_main);
		
		WebView myWebView = (WebView) findViewById(R.id.webview);
		
		Intent intent = getIntent();
		
		String webViewFithPath = intent.getStringExtra(EXTRA_MESSAGE);
		
		myWebView.loadUrl(webViewFithPath);
		myWebView.setBackgroundColor(Color.TRANSPARENT);
		
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
		getMenuInflater().inflate(R.menu.menu_webview, menu);
		
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId()){
		
		case R.id.menu_webview:
			Intent intentOld = new Intent(this, BibleMainActivity.class);
			intentOld.putExtra(BibleMainActivity.EXTRA_MESSAGE, BookHandler.OLD_TESTAMENT);
			this.startActivity(intentOld);
			
			this.finish();
			
			break;
	
		}
			
		return true;
	}
}
