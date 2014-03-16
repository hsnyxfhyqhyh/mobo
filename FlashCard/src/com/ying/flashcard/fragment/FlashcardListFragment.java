package com.ying.flashcard.fragment;

import java.util.ArrayList;

import com.ying.flashcard.activity.ApplicationInitializeActivity;
import com.ying.flashcard.activity.FlashCardListActivity;
import com.ying.flashcard.activity.FlashCardListItemActivity;
import com.ying.flashcard.db.QuestionHandler;
import com.ying.flashcard.db.SetHandler;
import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.dto.SetDTO;
import com.ying.flashcard.util.MainActivityPreferences;
import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FlashcardListFragment extends ListFragment {
	FlashCardListActivity parentActivity = null;
	MainActivityPreferences preferences = null;

	QuestionHandler questionHandler = null;
	SetHandler setHandler = null;
	ArrayList<QuestionDTO> questions = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		parentActivity =  (FlashCardListActivity)this.getActivity();
		preferences = ApplicationInitializeActivity.preferences;

		questionHandler = new QuestionHandler(this.getActivity());

		
		setHandler = new SetHandler(parentActivity);
		questionHandler = new QuestionHandler(parentActivity);
		
		String setName = preferences.getSetName();
		SetDTO setDTO = setHandler.getSet(setName);
		int setFK = Integer.parseInt(setDTO.getId());
		
		questions = questionHandler.getQuestions(setFK);
		

		String[] titles = new String[questions.size()];
		for (int i = 0; i < questions.size(); i++) {
			titles[i] = questions.get(i).getTitle();
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
		preferences.setQuestionId(questions.get(position).getId());
		preferences.commit();
		
		Intent intent = new Intent(this.getActivity(), FlashCardListItemActivity.class);
		startActivity(intent);
		this.getActivity().finish();
	}
}