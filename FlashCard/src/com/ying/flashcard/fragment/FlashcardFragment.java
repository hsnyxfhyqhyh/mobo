package com.ying.flashcard.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.ying.flashcard.R;
import com.ying.flashcard.activity.ApplicationInitializeActivity;
import com.ying.flashcard.activity.FlashCardDetailActivity;
import com.ying.flashcard.db.QuestionHandler;
import com.ying.flashcard.db.SetHandler;
import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.dto.SetDTO;
import com.ying.flashcard.util.MainActivityPreferences;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// A ListFragment is used to display a list of items

public class FlashcardFragment extends ListFragment {

	// Will monitor if a headline is clicked on

	OnCardSelectedListener mCallback;
	FlashCardDetailActivity parentActivity = null;
	MainActivityPreferences preferences = null;
	
	SetHandler setHandler = null;
	QuestionHandler questionHandler = null;
	

	// The container Activity must implement this interface so the
	// fragment can deliver messages

	public interface OnCardSelectedListener {

		// This function is called when a list item is selected

		public void onCardSelected(int position);

	}

	// Initializes the Fragment

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		parentActivity = (FlashCardDetailActivity) this.getActivity();
		preferences = ApplicationInitializeActivity.preferences;
		
		setHandler = new SetHandler(parentActivity);
		questionHandler = new QuestionHandler(parentActivity);
		
		String setName = preferences.getSetName();
		SetDTO setDTO = setHandler.getSet(setName);
		int setFK = Integer.parseInt(setDTO.getId());
		
		ArrayList<QuestionDTO> questions = questionHandler.getQuestions(setFK);
		
		parentActivity.questions = questions;
		
		long seed = System.nanoTime();
		Collections.shuffle(questions, new Random(seed));
		
		String [] titles = new String[questions.size()];
		for (int i=0; i< questions.size(); i++) {
			titles[i] = questions.get(i).getTitle();
		}
		
		
		int layout = android.R.layout.simple_list_item_1;

		// A ListAdapter populates the ListView with data in ipsum arrays
		// An ArrayAdapter specifically deals with arrays
		// getActivity() gets an Intent to start a new activity
		// layout is the list items layout
		

		setListAdapter(new ArrayAdapter<String>(getActivity(), layout,
				titles));
	}

	// Called when the Fragment is visible on the screen

	@Override
	public void onStart() {

		super.onStart();

		
	}

	// Called when a Fragment is attached to an Activity

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.

		try {
			mCallback = (OnCardSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Notify the parent activity of selected item
		mCallback.onCardSelected(position);

		// Set the item as checked to be highlighted when in two-pane layout
		getListView().setItemChecked(position, true);
	}
}