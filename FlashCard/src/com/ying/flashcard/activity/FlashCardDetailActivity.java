package com.ying.flashcard.activity;

import java.util.ArrayList;

import com.ying.flashcard.R;
import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.fragment.SwipeFragment;
import com.ying.flashcard.util.MainActivityPreferences;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import android.support.v4.app.FragmentActivity;

public class FlashCardDetailActivity extends FragmentActivity {

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
		case R.id.menu_back_to_set_list:
			backToSet();
			break;
		case R.id.menu_add_question:
			Intent intentAddQuestion = new Intent(this, FlashCardAddUpdateActivity.class);
			this.startActivity(intentAddQuestion);
			this.finish();
			break;
		case R.id.menu_edit_question:
			Intent intentEditQuestion = new Intent(this, FlashCardAddUpdateActivity.class);
			this.startActivity(intentEditQuestion);
			break;
		case R.id.menu_about:
			Toast.makeText(getBaseContext(), "DEVELOPED BY - Yinghui Hu\n" + this.getString(R.string.version_number)  , Toast.LENGTH_SHORT).show();
			break;
		}
		
		return true;
	}
	
	@Override
	public void onBackPressed() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(this.getString(R.string.alert_dialog_title_back));

	    builder.setPositiveButton(this.getString(R.string.alert_dialog_ok_button_text), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                    dialog.dismiss();
	                    
	                    backToSet();
	                    finish();
	            }
	        });

	    builder.setNegativeButton(this.getString(R.string.alert_dialog_notok_button_text), new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.dismiss();
	        }
	    });
	    AlertDialog alert = builder.create();
	    alert.show();
	    
	    
	}
	
	public void backToSet() {
		Intent intentNew = new Intent(this, SetsListActivity.class);
		this.startActivity(intentNew);
	}
	
	public void doneSet() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(this.getString(R.string.alert_dialog_title_done_set));

	    builder.setPositiveButton(this.getString(R.string.alert_dialog_ok_button_text), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                    dialog.dismiss();
	                    
	                    backToSet();
	                    finish();
	            }
	        });

	    builder.setNegativeButton(this.getString(R.string.alert_dialog_notok_button_text), new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.dismiss();
	        }
	    });
	    AlertDialog alert = builder.create();
	    alert.show();
	    
	    
	}
}
