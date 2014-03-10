package com.ying.exercise.fragment;

import java.util.ArrayList;
import com.ying.exercise.util.MainActivityPreferences;
import com.ying.exercise.activity.ApplicationInitializeActivity;
import com.ying.exercise.activity.DayListActivity;
import com.ying.exercise.activity.UsersListActivity;
import com.ying.exercise.db.LocationHandler;
import com.ying.exercise.dto.LocationDTO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LocationListFragment extends ListFragment {

	// Will monitor if a headline is clicked on
	OnChoiceSelectedListener mCallback;
	
	UsersListActivity parentActivity = null;
	MainActivityPreferences preferences = null;

	LocationHandler locationHandler = null;
	
	ArrayList<LocationDTO> locations = null;

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
		
		String userName = preferences.getUserName();

		locationHandler = new LocationHandler(this.getActivity());

		locations = locationHandler.getLocations();

		String[] titles = new String[locations.size()];
		for (int i = 0; i < locations.size(); i++) {
			titles[i] = locations.get(i).getName();
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
		preferences.setLocationId(locations.get(position).getId());
		preferences.commit();

		Intent intent = new Intent(this.getActivity(), DayListActivity.class);
		this.startActivity(intent);
		this.getActivity().finish();
	}
}