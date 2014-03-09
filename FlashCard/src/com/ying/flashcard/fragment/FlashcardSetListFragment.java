package com.ying.flashcard.fragment;

import java.util.ArrayList;

import com.ying.flashcard.activity.ApplicationInitializeActivity;
import com.ying.flashcard.activity.FlashCardDetailActivity;
import com.ying.flashcard.activity.SetsListActivity;
import com.ying.flashcard.db.QuestionHandler;
import com.ying.flashcard.db.SetHandler;
import com.ying.flashcard.dto.SetDTO;
import com.ying.flashcard.util.MainActivityPreferences;
import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FlashcardSetListFragment extends ListFragment {

	// Will monitor if a headline is clicked on
	OnChoiceSelectedListener mCallback;
	
	SetsListActivity parentActivity = null;
	MainActivityPreferences preferences = null;

	SetHandler setHandler = null;
	QuestionHandler questionHandler = null;
	
	ArrayList<SetDTO> sets = null;

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

		setHandler = new SetHandler(this.getActivity());

		sets = setHandler.getSets();

		String[] titles = new String[sets.size()];
		for (int i = 0; i < sets.size(); i++) {
			titles[i] = sets.get(i).getName();
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
			mCallback = (OnChoiceSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		preferences.setSetName(sets.get(position).getName());
		preferences.commit();
		
		Intent intent = new Intent(this.getActivity(), FlashCardDetailActivity.class);
		startActivity(intent);
		this.getActivity().finish();
	}
}