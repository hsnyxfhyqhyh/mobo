package com.ying.exercise.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ying.exercise.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.ying.exercise.db.DayHandler;
import com.ying.exercise.db.MachineHandler;
import com.ying.exercise.dto.DayDTO;
import com.ying.exercise.dto.MachineDTO;
import com.ying.exercise.fragment.DayListFragment;
import com.ying.exercise.fragment.MachineListFragment;
import com.ying.exercise.util.MainActivityPreferences;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class DayListActivity extends FragmentActivity {
	public DayHandler dayHandler;
	final Context context = this;
	EditText txtAddDay = null;
	
	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);
		
		String userName = preferences.getUserName();
		String title = userName  + " - " +this.getTitle();
		this.setTitle(title);
		
		displayList();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.day, menu);
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

		case R.id.menu_add_today:
			dayHandler = new DayHandler(this);
			DayDTO today =new DayDTO();
			today.setName(getToday());
			today.setUserFK(preferences.getUserId());
			dayHandler.create(today);
			
			Intent intent = new Intent(this, UsersListActivity.class);
			this.startActivity(intent);
			this.finish();
			
			break;
		
		case R.id.menu_machines:
			Intent intentMachines = new Intent(this, MachineListActivity.class);
			this.startActivity(intentMachines);
			this.finish();
			break;
		case R.id.menu_add_day:
			showDialog();
			break;
			
			
		case R.id.menu_logout:
			preferences.logout();
			Intent intentLogout = new Intent(this, UsersListActivity.class);
			this.startActivity(intentLogout);
			this.finish();
			break;
		}

		return true;
	}
	
	public void showDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.activity_day_edit);
		dialog.setTitle(R.string.dialog_title_add_day);

		// set the custom dialog components - text, image and button
		txtAddDay = (EditText) dialog.findViewById(R.id.txtAddDate);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!txtAddDay.getText().toString().trim().equals("")){
					dayHandler = new DayHandler(context);
					DayDTO dto =new DayDTO();
					dto.setName(txtAddDay.getText().toString());
					dto.setUserFK(preferences.getUserId());
					dayHandler.create(dto);
					
					Toast.makeText(getBaseContext(), txtAddDay.getText() + " is added" , Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(), "add a valid date please" , Toast.LENGTH_SHORT).show();
				}
				
				displayList();
				dialog.dismiss();
			}
		});

		dialog.show();
	 }
	
	private String getToday(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		return format1.format(cal.getTime());
	}
	
	private void displayList() {
		FrameLayout container = (FrameLayout)findViewById(R.id.fragment_container);
		if ( container != null) {

			container.removeAllViews();
			
			if (preferences.getUserName().equals("")) {
				Intent intent = new Intent(this, UsersListActivity.class);
				this.startActivity(intent);
				this.finish();
			}  else {
				DayListFragment firstFragment = new DayListFragment();

				firstFragment.setArguments(getIntent().getExtras());

				getSupportFragmentManager().beginTransaction()
						.add(R.id.fragment_container, firstFragment).commit();	
			}
			
			
		}
	}
	
}
