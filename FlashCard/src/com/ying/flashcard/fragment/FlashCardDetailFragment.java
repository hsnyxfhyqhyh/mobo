package com.ying.flashcard.fragment;

import java.util.ArrayList;

import com.ying.flashcard.R;
import com.ying.flashcard.activity.FlashCardDetailActivity;
import com.ying.flashcard.dto.QuestionDTO;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


//To auto import unimplemented methods
//Right Click class - Source - Override/Implement Methods
//Check what to implement

public class FlashCardDetailFragment extends Fragment {
	
	// Used to pass the current article selected between activities
	
    public final static String ARG_POSITION = "position";
    
    // Used to track the current article in this class
    
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
    	
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflate the layout for this fragment
        
        return inflater.inflate(R.layout.article_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        
        Bundle args = getArguments();
        
        // Check if an article had been selected
        
        if (args != null) {
        	
            // Set article based on argument passed in
            updateCardDetailView(args.getInt(ARG_POSITION));
            
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
        	
            updateCardDetailView(mCurrentPosition);
        }
    }
    
    // Put the text for the selected article in the article TextView

    public void updateCardDetailView(int position) {
    	
    	ArrayList<QuestionDTO> questions = ((FlashCardDetailActivity) this.getActivity()).questions;
    	
        TextView article = (TextView) getActivity().findViewById(R.id.card_detail);
        article.setText(questions.get(position).getAnswer());
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}