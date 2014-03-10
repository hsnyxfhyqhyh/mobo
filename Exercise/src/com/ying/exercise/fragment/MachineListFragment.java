package com.ying.exercise.fragment;

import java.util.ArrayList;
import com.ying.exercise.util.MainActivityPreferences;
import com.ying.exercise.activity.ApplicationInitializeActivity;
import com.ying.exercise.activity.MachineListActivity;
import com.ying.exercise.db.MachineHandler;
import com.ying.exercise.dto.MachineDTO;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MachineListFragment extends ListFragment {

	// Will monitor if a headline is clicked on
	OnChoiceSelectedListener mCallback;
	
	MachineListActivity parentActivity = null;
	MainActivityPreferences preferences = null;

	MachineHandler machineHandler = null;
	
	ArrayList<MachineDTO> machines = null;

	/*
	 *  The container Activity must implement this interface so the fragment can deliver messages
	 */
	public interface OnChoiceSelectedListener {
		// This function is called when a list item is selected
		public void OnChoiceSelected(int position);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferences = ApplicationInitializeActivity.preferences;
		String locationFK = preferences.getLocationId();
		
		String userName = preferences.getUserName();

		machineHandler = new MachineHandler(this.getActivity());

		machines = machineHandler.getMachines(locationFK);

		String[] titles = new String[machines.size()];
		for (int i = 0; i < machines.size(); i++) {
			titles[i] = machines.get(i).getName() + "\n\t" + machines.get(i).getDescription();
		}

		int layout = android.R.layout.simple_list_item_1;

		setListAdapter(new ArrayAdapter<String>(getActivity(), layout, titles));
	}

	@Override
	public void onStart() {

		super.onStart();

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
//			mCallback = (OnChoiceSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
//		preferences.setLocationId(days.get(position).getId());
//		preferences.commit();

	}
}