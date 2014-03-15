package com.ying.flashcard.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

import com.ying.flashcard.R;
import com.ying.flashcard.db.DataBaseHelper;
import com.ying.flashcard.db.SetHandler;
import com.ying.flashcard.dto.SetDTO;
import com.ying.flashcard.util.MainActivityPreferences;
import com.ying.flashcard.fragment.FlashcardSetListFragment;
import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


public class SetsListActivity extends FragmentActivity implements
		FlashcardSetListFragment.OnChoiceSelectedListener {

	final Context context = this;
	
	public SetHandler setHandler;
	EditText txtAddSet ;

	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	private ArrayList<SetDTO> sets;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);

		displayList();
	}

	
	private void displayList() {
		FrameLayout container = (FrameLayout)findViewById(R.id.fragment_container);
		if ( container != null) {
			container.removeAllViews();
		}
		
		FlashcardSetListFragment firstFragment = new FlashcardSetListFragment();

		firstFragment.setArguments(getIntent().getExtras());

		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_set_list, menu);
		return true;
	}

	
	@Override
	public void onBackPressed() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getString(R.string.alert_dialog_title));

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
		case R.id.menu_add_set:
			showDialog();
			break;
		case R.id.action_settings:
			break;
		}

		return true;
	}

	
	// exporting database
	private void exportDB() {
		String PACKAGE_NAME = "com.ying.flashcard";

		String currentDBPath = String.format("/data/data/%s/databases/%s",
				PACKAGE_NAME, DataBaseHelper.getDBName());
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {

				String sbackupDB = String.format(
						DataBaseHelper.getDbNameTemplate(), getRandomString());
				String backupDBPath = Environment.getExternalStorageDirectory()
						+ "/Download/flashcard/" + sbackupDB;
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

	@Override
	public void OnChoiceSelected(int position) {

	}
	
	public void showDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_set_add);
		dialog.setTitle(R.string.dialog_set_add);

		// set the custom dialog components - text, image and button
		txtAddSet = (EditText) dialog.findViewById(R.id.txtAddSet);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!txtAddSet.getText().toString().trim().equals("")){
					setHandler = new SetHandler(context);
					SetDTO dto =new SetDTO();
					dto.setName(txtAddSet.getText().toString());
					setHandler.create(dto);
					
					Toast.makeText(getBaseContext(), txtAddSet.getText() + " 已添加" , Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(), "请添加正确类名" , Toast.LENGTH_SHORT).show();
				}
				
				displayList();
				dialog.dismiss();
			}
		});

		dialog.show();
	 }
}
