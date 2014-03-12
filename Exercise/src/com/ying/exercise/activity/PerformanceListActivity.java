package com.ying.exercise.activity;

import java.util.ArrayList;

import com.ying.exercise.R;
import com.ying.exercise.db.MachineHandler;
import com.ying.exercise.db.PerformanceHandler;
import com.ying.exercise.dto.MachineDTO;
import com.ying.exercise.dto.PerformanceDTO;
import com.ying.exercise.fragment.DayMachineListFragment;
import com.ying.exercise.util.MainActivityPreferences;

import android.os.Bundle;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class PerformanceListActivity extends FragmentActivity {

	final Context context = this;
	EditText txtDayPerformance; 
	Spinner spinnerMachines;
	String createDate;
	String locationFK ;
	String userFK;
	
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
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.activity_performance_add);
		dialog.setTitle(R.string.dialog_title_add_day);

		// set the custom dialog components - text, image and button
		txtDayPerformance = (EditText) dialog.findViewById(R.id.txtDayPerformance);
		spinnerMachines = (Spinner) dialog.findViewById(R.id.spinnerMachines);

		locationFK = preferences.getLocationId();
		createDate = preferences.getDay();
		userFK = preferences.getUserId();
		ArrayList<MachineDTO> machines = new MachineHandler(this).getMachines(locationFK);
		
		if (machines.size() > 0 ) {
			String array_spinner[] = new String[machines.size()];
			
			for (int i=0; i< machines.size(); i++ ) {
				MachineDTO dto = machines.get(i);
				array_spinner[i] = dto.getName();
			}
			
			ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner);

			spinnerMachines.setAdapter(adapter);
			
			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!txtDayPerformance.getText().toString().trim().equals("")) {
						String machineName = spinnerMachines.getSelectedItem().toString();
						
						MachineDTO mDTO = new MachineHandler(context).getMachine(machineName, locationFK);
						
						String machineFK = mDTO.getId();
						
						PerformanceHandler handler = new PerformanceHandler(context);
						PerformanceDTO pDTO = new PerformanceDTO();
						
						pDTO.setCreateDate(createDate);
						pDTO.setMachineFK(machineFK);
						pDTO.setUserFK(userFK);
						pDTO.setDescription(txtDayPerformance.getText().toString());
						handler.create(pDTO);

						Toast.makeText(getBaseContext(), "已添加", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getBaseContext(), "请填入正确信息", Toast.LENGTH_SHORT).show();
					}

					displayList();
					dialog.dismiss();
				}
			});

			dialog.show();
		} else {
			Toast.makeText(getBaseContext(), "请添加设备" , Toast.LENGTH_SHORT).show();
		}
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
