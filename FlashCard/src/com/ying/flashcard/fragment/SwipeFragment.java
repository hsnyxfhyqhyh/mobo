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
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SwipeFragment extends Fragment {

	FlashCardDetailActivity parentActivity = null;
	MainActivityPreferences preferences = null;
	
	SetHandler setHandler = null;
	QuestionHandler questionHandler = null;
	
	ArrayList<QuestionDTO> questions = null;
	
	private final String CARD_MODE_QUESTION = "QUESTION";
	private final String CARD_MODE_ANSWER = "ANSWER";
	private final String CARD_MODE_DONE = "DONE";
	
	private String mode = CARD_MODE_QUESTION;
	
	private int count = 0;
	private TextView textCard = null;
	
	private String QUESTION_ANSWER_LINE_BREAK = "\n\n";
	private int FONT_SIZE = 20;
	

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
		
		questions = questionHandler.getQuestions(setFK);
		
		long seed = System.nanoTime();
		Collections.shuffle(questions, new Random(seed));
		
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 return inflater.inflate(R.layout.swipe_view, container, false);
	}


	@Override
	public void onStart() {
		super.onStart();
		Button btnNext = (Button) getActivity().findViewById(R.id.buttonSwipe);
		
		btnNext.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        doNext(v);
		    }
		});
		
		updateCardDetailView();
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	
	public void updateCardDetailView() {
    	LinearLayout container = (LinearLayout)getActivity().findViewById(R.id.swip_card_container);
    	container.removeAllViewsInLayout();	//reset

    	if (!isSetEmpty()) {
    		String cardText = determineText();
    		
    		if (cardText.equals(CARD_MODE_DONE)) {
    			resetTextView();
    	    	textCard.setText(CARD_MODE_DONE);
    	    	
    	    	container.addView(textCard);
    	    	
    	    	Button btnNext = (Button) getActivity().findViewById(R.id.buttonSwipe);
    	    	btnNext.setEnabled(false);
    	    	return;
    		} else {
    			resetTextView();
    	    	textCard.setText(cardText);
    	    	container.addView(textCard);
    		}
    		
	    	
    	} else {
    		resetTextView();
	    	textCard.setText("Empty Set");
	    	
	    	container.addView(textCard);
	    	
	    	Button btnNext = (Button) getActivity().findViewById(R.id.buttonSwipe);
	    	btnNext.setEnabled(false);
    	}
        
    }
	
	
	private void doNext(View v) {
		updateCardDetailView();
			
	}
	
	
	private boolean isSetEmpty () {
		if ((questions == null) || questions.size()==0) {
			return true;
		}
		
		return false;
	}
	
	
	private String determineText() {
		String cardText = "";
		
		if (count == questions.size()){
			cardText = CARD_MODE_DONE;
		} else {
		
			if (mode.equals(CARD_MODE_QUESTION)) {
				mode = CARD_MODE_ANSWER;
				cardText = questions.get(count).getTitle();
			} else {
				cardText = questions.get(count).getTitle() + QUESTION_ANSWER_LINE_BREAK + questions.get(count).getAnswer();
				count++;
				mode = CARD_MODE_QUESTION;
			}
		}
		return cardText;
	}
	
	private void resetTextView() {
		textCard = new TextView(this.getActivity());
		textCard.setTextSize(FONT_SIZE);
		
//		textCard.setCustomSelectionActionModeCallback(mActionModeCallback);
//		textCard.setFreezesText(false);
//		textCard.setTextIsSelectable(true);
//		
//		textCard.setText(chapterContent);
//		Typeface face = Typeface.createFromAsset(this.getActivity().getAssets(), preferences.getFontFamily());
//		textCard.setTypeface(face);
	}
}