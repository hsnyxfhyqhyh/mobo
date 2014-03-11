package com.ying.exercise.activity;

import com.ying.exercise.R;
import com.ying.exercise.db.MachineHandler;
import com.ying.exercise.dto.MachineDTO;
import com.ying.exercise.fragment.MachineListFragment;
import com.ying.exercise.util.MainActivityPreferences;

import android.os.Bundle;
import android.app.Dialog;
import android.content.Context;
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

public class MachineListActivity extends FragmentActivity {
	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	
	final Context context = this;
	EditText txtAddMaince = null;
	EditText txtAddMachineDescription=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);

		displayList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.machine_list, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {

		case R.id.menu_add_machine_to_location:
//			Toast.makeText(getBaseContext(), "Menu add new machine clicked", Toast.LENGTH_LONG).show();
//			
//			Intent intent = new Intent(this, MachineListActivity.class);
//			this.startActivity(intent);
//			this.finish();
			showDialog();
			break;
		case R.id.action_settings:
			break;
		}

		return true;
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, DayListActivity.class);
		this.startActivity(intent);
		this.finish();
	}

	
	public void showDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.activity_machine_edit);
		dialog.setTitle(R.string.dialog_title_add_machine);

		// set the custom dialog components - text, image and button
		TextView txtAddMainceLabel = (TextView) dialog.findViewById(R.id.txtAddMainceLabel);
		TextView txtAddMainceDescription = (TextView) dialog.findViewById(R.id.txtAddMainceDescriptionLabel);
		
		 txtAddMaince = (EditText)dialog.findViewById(R.id.txtAddMaince);
		txtAddMachineDescription = (EditText)dialog.findViewById(R.id.txtAddMachineDescription);
		

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!txtAddMachineDescription.getText().toString().trim().equals("")){
					MachineHandler dbHandler = new MachineHandler(context);
					MachineDTO dto = new MachineDTO();
					dto.setName(txtAddMaince.getText().toString());
					dto.setDescription(txtAddMachineDescription.getText().toString());
					dto.setLocationFK(preferences.getLocationId());
					dbHandler.create(dto);
					
					Toast.makeText(getBaseContext(), txtAddMaince.getText() + " is added" , Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(), "add a valid machine please" , Toast.LENGTH_SHORT).show();
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
			
			if (preferences.getLocationId().equals("")) {
				Intent intent = new Intent(this, UsersListActivity.class);
				this.startActivity(intent);
				this.finish();
			}  else {
				MachineListFragment firstFragment = new MachineListFragment();

				firstFragment.setArguments(getIntent().getExtras());

				getSupportFragmentManager().beginTransaction()
						.add(R.id.fragment_container, firstFragment).commit();	
			}
			
		}
	}
	
}
