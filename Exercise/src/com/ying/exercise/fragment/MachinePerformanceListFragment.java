package com.ying.exercise.fragment;

import java.util.ArrayList;
import com.ying.exercise.util.MainActivityPreferences;
import com.ying.exercise.activity.ApplicationInitializeActivity;
import com.ying.exercise.activity.DayListActivity;
import com.ying.exercise.db.PerformanceHandler;
import com.ying.exercise.dto.PerformanceDTO;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MachinePerformanceListFragment extends ListFragment {

	DayListActivity parentActivity = null;
	MainActivityPreferences preferences = null;

	PerformanceHandler handler = null;
	
	ArrayList<PerformanceDTO> dtos = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferences = ApplicationInitializeActivity.preferences;
		String userFk = preferences.getUserId();
		String machineFK = preferences.getMachineFK();

		handler = new PerformanceHandler(this.getActivity());

		dtos = handler.getPerformancesByMachine(userFk, machineFK);

		String[] titles = new String[dtos.size()];
		for (int i = 0; i < dtos.size(); i++) {
			titles[i] = dtos.get(i).getCreateDate() + "\n\t" + dtos.get(i).getDescription();
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