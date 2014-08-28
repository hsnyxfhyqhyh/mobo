package com.chccc.bible.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.chccc.bible.R;
import com.chccc.bible.db.BookHandler;
import com.chccc.bible.db.DataBaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ApplicationInitializeActivity extends Activity  implements OnClickListener{
	
	public BookHandler bookHandler;
	Button buttonApplicationInitialize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.application_initialize);
		
		buttonApplicationInitialize = (Button) findViewById(R.id.buttonApplicationInitialize);
		buttonApplicationInitialize.setOnClickListener(this);
		
		try {
			createDatabase();
//		bookHandler = new BookHandler(ApplicationInitializeActivity.this);
//		if (bookHandler.isIntialized()) {
			Intent intentNew = new Intent(this, BibleMainActivity.class);
			this.startActivity(intentNew);
			this.finish();
		//}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.application_initialize, menu);
		return true;
	}
	
	
	@Override
    public void onClick(View v) {
		
		// put data to database
//		bookHandler.insertBookData();
//		
//		Intent intentNew = new Intent(this, BibleMainActivity.class);
//		this.startActivity(intentNew);
//		this.finish();
    }
	
	
	private void createDatabase()throws IOException {
		DataBaseHelper dbHelper = new DataBaseHelper(this);
	}
}
