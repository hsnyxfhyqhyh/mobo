package com.ying.flashcard.activity;

import java.util.ArrayList;

import com.ying.flashcard.R;
import com.ying.flashcard.R.layout;
import com.ying.flashcard.R.menu;
import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.fragment.FlashCardDetailFragment;
import com.ying.flashcard.fragment.FlashcardFragment;
import com.ying.flashcard.fragment.SwipeFragment;
import com.ying.flashcard.util.MainActivityPreferences;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

public class FlashCardDetailActivity extends FragmentActivity implements
		FlashcardFragment.OnCardSelectedListener {

	public MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	
	public ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>() ; 

	String setName = "";
	SwipeFragment firstFragment =null;

	// Called when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_card_container);

		// Check whether the activity is using the layout version with
		// the fragment_container FrameLayout. If so, we must add the first
		// fragment

		if (findViewById(R.id.flashcard_container) != null) {

			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.

			if (savedInstanceState != null) {
				return;
			}

			// Create an instance of the Fragment that holds the titles

//			FlashcardFragment firstFragment = new FlashcardFragment();
			firstFragment = new SwipeFragment();

			// In case this activity was started with special instructions from
			// an Intent,
			// pass the Intent's extras to the fragment as arguments

			firstFragment.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			// beginTransaction() is used to begin any edits of Fragments

			getSupportFragmentManager().beginTransaction().add(R.id.flashcard_container, firstFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.flash_card_detail, menu);
		
		return true;
	}
	// Required if the OnHeadlineSelectedListener interface is implemented
	// This method is called when a headline is clicked on

	public void onCardSelected(int position) {

		

			// If the fragment is not available, use the one pane layout and
			// swap between the article and headline fragments

			// Create fragment and give it an argument for the selected article

			FlashCardDetailFragment newFragment = new FlashCardDetailFragment();

			// The Bundle contains information passed between activities

			Bundle args = new Bundle();

			// Save the current article value

			args.putInt(FlashCardDetailFragment.ARG_POSITION, position);

			// Add the article value to the new Fragment

			newFragment.setArguments(args);

			// The FragmentTransaction adds, removes, replaces and
			// defines animations for Fragments
			// The FragmentManager provides methods for interacting
			// beginTransaction() is used to begin any edits of Fragments

			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack so the user can
			// navigate back

			transaction.replace(R.id.flashcard_container, newFragment);

			// addToBackStack() causes the transaction to be remembered.
			// It will reverse this operation when it is later popped off
			// the stack.

			transaction.addToBackStack(null);

			// Schedules for the addition of the Fragment to occur

			transaction.commit();
		}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId()){
		
		case R.id.menu_previous_card:
			firstFragment.backToQuestion();
//			this.startActivity(intent);
			break;
		case R.id.menu_next_card:
			firstFragment.updateCardDetailView();
			break;
		}
		
		
		return true;
	}
}
