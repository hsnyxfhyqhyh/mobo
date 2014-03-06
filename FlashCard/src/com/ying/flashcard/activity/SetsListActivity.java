package com.ying.flashcard.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

import com.ying.flashcard.R;
import com.ying.flashcard.db.DataBaseHelper;
import com.ying.flashcard.db.SetHandler;
import com.ying.flashcard.dto.SetDTO;
import com.ying.flashcard.util.MainActivityPreferences;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetsListActivity extends Activity {

	LinearLayout SetListContainer ;
	public SetHandler setHandler;
	
	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	
	private ArrayList<SetDTO> sets;
	TextView textSet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_list);
		
		SetListContainer = (LinearLayout) findViewById(R.id.SetListContainer );
		
		readSets();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private void readSets() {
		SetListContainer.removeAllViews();
		setHandler = new SetHandler(SetsListActivity.this);
		
		sets = setHandler.getSets();
		
		for (int i=0; i< sets.size(); i++) {
				SetDTO setDTO = sets.get(i);
				
				Button btn = new Button(this);
				btn.setText(setDTO.getName());
				
				btn.setOnClickListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v) {
				        doFlashCard(v);
				    }
				});
				
				SetListContainer.addView(btn);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		/*
		switch (requestCode) {
		case RESULT_SETTINGS:
			readBible();
			break;
		}
		*/

	}
	
	private void doFlashCard(View v) {
		Button btn = (Button) v;
		
		String setName_ = btn.getText().toString();
		
		
		preferences.setSetName(setName_);
		preferences.commit();
		
		Intent intent = new Intent(this, FlashCardDetailActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId()){
		
		case R.id.export_db:
			exportDB();
//			firstFragment.backToQuestion();
//			this.startActivity(intent);
			break;
		case R.id.action_settings:
//			firstFragment.backToQuestion();
//			this.startActivity(intent);
			break;
		}
		
		
		return true;
	}
	
	// exporting database
		private void exportDB() {
			// TODO Auto-generated method stub
			//This is the package defined in the manifest.xml file? 
			String PACKAGE_NAME = "com.ying.flashcard";
			
			String currentDBPath = String.format("/data/data/%s/databases/%s", PACKAGE_NAME, DataBaseHelper.getDBName());
			try {
				File sd = Environment.getExternalStorageDirectory();
				File data = Environment.getDataDirectory();

				if (sd.canWrite()) {
					
					String sbackupDB = String.format(DataBaseHelper.getDbNameTemplate(), getRandomString());
					String backupDBPath = Environment.getExternalStorageDirectory() + "/Download/flashcard/" + sbackupDB;
//					writeFile("hello", backupDBPath);
					
					File currentDB = new File(currentDBPath);
					File backupDB = new File(backupDBPath);

					FileChannel src = new FileInputStream(currentDB).getChannel();
					FileChannel dst = new FileOutputStream(backupDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
					Toast.makeText(getBaseContext(), backupDB.toString(),
							Toast.LENGTH_LONG).show();

	                
					Toast.makeText(getBaseContext(), backupDB.toString(),
							Toast.LENGTH_LONG).show();

				}
			} catch (Exception e) {

				Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
						.show();

			}
		}
		
//		public  void writeFile(String content, String filename) {
//			try {
//				PrintWriter out = new PrintWriter(new FileOutputStream(filename));
//
//				out.print(content);
//				out.flush();
//				out.close();
//				out = null;
//			} catch (Exception e) {
//			}
//		}
		private String getRandomString() {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1; 
			int day = now.get(Calendar.DAY_OF_MONTH);
			int hour = now.get(Calendar.HOUR_OF_DAY);
			int minute = now.get(Calendar.MINUTE);
			int second = now.get(Calendar.SECOND);
//			int millis = now.get(Calendar.MILLISECOND);
			
			return "" + year + padLeadingZero(month) + padLeadingZero(day) + padLeadingZero(hour) + padLeadingZero(minute) + padLeadingZero(second);
		}
		
		private String padLeadingZero(int i) {
			String s = i + "";
			
			if (s ==null ) return "00";
			
			s = s.trim();
			while (s.length()<2) {
				s = "0" +s;
			}
			
			return s;
		}
		
}
