package com.ying.exercise.activity;

import com.ying.exercise.R;
import com.ying.exercise.fragment.DayMachineListFragment;
import com.ying.exercise.util.MainActivityPreferences;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class PerformanceListActivity extends FragmentActivity {

	final Context context = this;
	
	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);

		displayList();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.performance_list, menu);
		return true;
	}

	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, DayListActivity.class);
		this.startActivity(intent);
		this.finish();
	}

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {

		case R.id.menu_add_day_performance:
			showDialog();
			break;
		
		}

		return true;
	}
	
	public void showDialog() {
//		// custom dialog
//		final Dialog dialog = new Dialog(context);
//		dialog.setContentView(R.layout.activity_day_edit);
//		dialog.setTitle(R.string.dialog_title_add_day);
//
//		// set the custom dialog components - text, image and button
//		txtAddDay = (EditText) dialog.findViewById(R.id.txtAddDate);
//		
//		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//		// if button is clicked, close the custom dialog
//		dialogButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(!txtAddDay.getText().toString().trim().equals("")){
//					dayHandler = new DayHandler(context);
//					DayDTO dto =new DayDTO();
//					dto.setName(txtAddDay.getText().toString());
//					dto.setUserFK(preferences.getUserId());
//					dayHandler.create(dto);
//					
					Toast.makeText(getBaseContext(), "  testis added" , Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(getBaseContext(), "add a valid date please" , Toast.LENGTH_SHORT).show();
//				}
//				
//				displayList();
//				dialog.dismiss();
//			}
//		});
//
//		dialog.show();
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
				DayMachineListFragment firstFragment = new DayMachineListFragment();

				firstFragment.setArguments(getIntent().getExtras());

				getSupportFragmentManager().beginTransaction()
						.add(R.id.fragment_container, firstFragment).commit();	
			}
			
			
		}
	}
	
}
