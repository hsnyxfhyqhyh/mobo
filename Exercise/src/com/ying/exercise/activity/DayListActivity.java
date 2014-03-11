package com.ying.exercise.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ying.exercise.R;
import android.os.Bundle;
import android.view.Menu;
import com.ying.exercise.db.DayHandler;
import com.ying.exercise.dto.DayDTO;
import com.ying.exercise.fragment.DayListFragment;
import com.ying.exercise.util.MainActivityPreferences;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;


public class DayListActivity extends FragmentActivity {
	public DayHandler dayHandler;

	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);

		if (findViewById(R.id.fragment_container) != null) {

			if (savedInstanceState != null) {
				return;
			}

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
		case R.id.action_settings:
			break;
		case R.id.menu_machines:
			Intent intentMachines = new Intent(this, MachineListActivity.class);
			this.startActivity(intentMachines);
			this.finish();
			break;
		}

		return true;
	}
	
	private String getToday(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		return format1.format(cal.getTime());
	}
	
}
