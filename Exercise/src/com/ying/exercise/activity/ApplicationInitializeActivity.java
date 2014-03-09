package com.ying.exercise.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ying.exercise.R;
import com.ying.exercise.db.DataBaseHelper;
import com.ying.exercise.util.MainActivityPreferences;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ApplicationInitializeActivity extends Activity  implements OnClickListener{
	
	Button buttonApplicationInitialize;
	public static MainActivityPreferences preferences;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.application_initialize);
		
		File direct = new File(Environment.getExternalStorageDirectory() + "/Download/"
				+ "/Exercise");

		if (!direct.exists()) {
			if (direct.mkdir()) {
				// directory is created;
			}

		}
		
		buttonApplicationInitialize = (Button) findViewById(R.id.buttonApplicationInitialize);
		buttonApplicationInitialize.setOnClickListener(this);
		
		preferences = new MainActivityPreferences(this);
		
		try {
			createDatabase();
//		bookHandler = new BookHandler(ApplicationInitializeActivity.this);
//		if (bookHandler.isIntialized()) {
			Intent intentNew = new Intent(this, UsersListActivity.class);
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
    }
	
	
	private void createDatabase()throws IOException {
		DataBaseHelper dbHelper = new DataBaseHelper(this);
	}
}
