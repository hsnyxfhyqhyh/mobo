package com.ying.exercise.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

import com.ying.exercise.R;
import com.ying.exercise.db.DataBaseHelper;
import com.ying.exercise.db.UserHandler;
import com.ying.exercise.dto.UserDTO;
import com.ying.exercise.fragment.LocationListFragment;
import com.ying.exercise.fragment.UserListFragment;
import com.ying.exercise.util.MainActivityPreferences;

import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class UsersListActivity extends FragmentActivity  {

	public UserHandler userHandler;

	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	private ArrayList<UserDTO> users;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);

		if (findViewById(R.id.fragment_container) != null) {

			if (savedInstanceState != null) {
				return;
			}

			if (preferences.getUserName().equals("")) {
				UserListFragment firstFragment = new UserListFragment();

				firstFragment.setArguments(getIntent().getExtras());

				getSupportFragmentManager().beginTransaction()
						.add(R.id.fragment_container, firstFragment).commit();	
			}  else if (preferences.getLocationId().equals("")) {
				LocationListFragment firstFragment = new LocationListFragment();

				firstFragment.setArguments(getIntent().getExtras());

				getSupportFragmentManager().beginTransaction()
						.add(R.id.fragment_container, firstFragment).commit();	
			} else {
				Intent intent = new Intent(this, DayListActivity.class);
				this.startActivity(intent);
				this.finish();
			}
			
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	public void onBackPressed() {

//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setTitle(this.getString(R.string.alert_dialog_title));
//
//		builder.setPositiveButton(
//				this.getString(R.string.alert_dialog_ok_button_text),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						finish();
//					}
//				});
//
//		builder.setNegativeButton(
//				this.getString(R.string.alert_dialog_notok_button_text),
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});
//		AlertDialog alert = builder.create();
//		alert.show();
	}

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {

		case R.id.export_db:
			exportDB();
			break;
		case R.id.action_settings:
			break;
		}

		return true;
	}

	
	// exporting database
	private void exportDB() {
		String PACKAGE_NAME = "com.ying.exercise";

		String currentDBPath = String.format("/data/data/%s/databases/%s",
				PACKAGE_NAME, DataBaseHelper.getDBName());
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {

				String sbackupDB = String.format(
						DataBaseHelper.getDbNameTemplate(), getRandomString());
				String backupDBPath = Environment.getExternalStorageDirectory()
						+ "/Download/Exercise/" + sbackupDB;
				// writeFile("hello", backupDBPath);

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

	
	private String getRandomString() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		// int millis = now.get(Calendar.MILLISECOND);

		return "" + year + padLeadingZero(month) + padLeadingZero(day)
				+ padLeadingZero(hour) + padLeadingZero(minute)
				+ padLeadingZero(second);
	}

	
	private String padLeadingZero(int i) {
		String s = i + "";

		if (s == null)
			return "00";

		s = s.trim();
		while (s.length() < 2) {
			s = "0" + s;
		}

		return s;
	}

}
