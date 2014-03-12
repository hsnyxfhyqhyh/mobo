package com.ying.exercise.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

import com.ying.exercise.R;
import com.ying.exercise.db.DataBaseHelper;
import com.ying.exercise.db.MachineHandler;
import com.ying.exercise.db.UserHandler;
import com.ying.exercise.dto.MachineDTO;
import com.ying.exercise.dto.UserDTO;
import com.ying.exercise.fragment.LocationListFragment;
import com.ying.exercise.fragment.MachineListFragment;
import com.ying.exercise.fragment.UserListFragment;
import com.ying.exercise.util.MainActivityPreferences;

import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class UsersListActivity extends FragmentActivity  {
	final Context context = this;
	public UserHandler userHandler;

	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	private ArrayList<UserDTO> users;
	
	EditText txt_dialog_add_user;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);

		if (findViewById(R.id.fragment_container) != null) {
			displayList();
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.user_list, menu);
		return true;
	}

	
	@Override
	public void onBackPressed() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getString(R.string.dialog_title_user_list_exit));

		builder.setPositiveButton(
				this.getString(R.string.alert_dialog_ok_button_text),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});

		builder.setNegativeButton(
				this.getString(R.string.alert_dialog_notok_button_text),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {

		case R.id.menu_export_db:
			exportDB();
			break;
		case R.id.menu_add_user:
			showAddUserDialog();
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
	
	public void showAddUserDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_user_add);
		dialog.setTitle(R.string.dialog_title_add_user);

		// set the custom dialog components - text, image and button
		txt_dialog_add_user = (EditText)dialog.findViewById(R.id.txt_dialog_add_user);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!txt_dialog_add_user.getText().toString().trim().equals("")){
					UserHandler dbHandler = new UserHandler(context);
					UserDTO dto = new UserDTO();
					dto.setName(txt_dialog_add_user.getText().toString());
					dbHandler.create(dto);
					
					Toast.makeText(getBaseContext(), txt_dialog_add_user.getText() + " is added" , Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(), "add a valid user name please" , Toast.LENGTH_SHORT).show();
				}
				
				displayList();
				dialog.dismiss();
			}
		});

		dialog.show();
	 }

	private void displayList() {
		FrameLayout container = (FrameLayout)findViewById(R.id.fragment_container);
		if ( container != null) {

			container.removeAllViews();
			
			
			if (preferences.getUserName().equals("")) {
				UserListFragment firstFragment = new UserListFragment();

				firstFragment.setArguments(getIntent().getExtras());

				getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();	
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
}
