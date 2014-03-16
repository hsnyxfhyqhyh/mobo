package com.ying.flashcard.activity;

import java.util.ArrayList;

import com.ying.flashcard.R;
import com.ying.flashcard.R.layout;
import com.ying.flashcard.R.menu;
import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.fragment.FlashcardListFragment;
import com.ying.flashcard.fragment.FlashcardSetListFragment;
import com.ying.flashcard.fragment.SwipeFragment;
import com.ying.flashcard.util.MainActivityPreferences;
import com.ying.flashcard.util.VersionUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class FlashCardListActivity extends FragmentActivity {

	final Context context = this;
	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	
	public ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>() ; 

	String setName = "";
	SwipeFragment firstFragment =null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);
		displayList();
	}

	
	private void displayList() {
		FrameLayout container = (FrameLayout)findViewById(R.id.fragment_container);
		if ( container != null) {
			container.removeAllViews();
		}
		
		FlashcardListFragment firstFragment = new FlashcardListFragment();

		firstFragment.setArguments(getIntent().getExtras());

		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_card_list, menu);
		return true;
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {
		case R.id.menu_back_to_set_list:
			backToSet();
			break;
		case R.id.menu_exam:
			jumpToExam();
			break;
		case R.id.menu_add_question:
			preferences.setQuestionIdBlank();
			jumpToAddCard();
			break;
		case R.id.menu_about:
			Toast.makeText(getBaseContext(), VersionUtil.getVersionInfo(this)  , Toast.LENGTH_SHORT).show();
			break;
		}

		return true;
	}
	


	@Override
	public void onBackPressed() {
		backToSet();
	}
	
	private void backToSet() {
		Intent intentNew = new Intent(this, SetsListActivity.class);
		this.startActivity(intentNew);
		this.finish();
	}
	
	private void jumpToAddCard() {
		Intent intentAddQuestion = new Intent(this, FlashCardAddUpdateActivity.class);
		this.startActivity(intentAddQuestion);
		this.finish();
	}
	

	private void jumpToExam() {
		Intent intent = new Intent(this, FlashCardDetailActivity.class);
		this.startActivity(intent);
		this.finish();		
	}
}
