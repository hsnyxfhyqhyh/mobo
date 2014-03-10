package com.ying.exercise.activity;

import com.ying.exercise.R;
import com.ying.exercise.fragment.MachineListFragment;
import com.ying.exercise.util.MainActivityPreferences;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MachineListActivity extends FragmentActivity {
	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);

		if (findViewById(R.id.fragment_container) != null) {

			if (savedInstanceState != null) {
				return;
			}

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
			Toast.makeText(getBaseContext(), "Menu add new machine clicked", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(this, MachineListActivity.class);
			this.startActivity(intent);
			this.finish();
			
			break;
		case R.id.action_settings:
			break;
		}

		return true;
	}
}
