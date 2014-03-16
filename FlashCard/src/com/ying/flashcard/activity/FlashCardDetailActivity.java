package com.ying.flashcard.activity;

import java.util.ArrayList;

import com.ying.flashcard.R;
import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.fragment.SwipeFragment;
import com.ying.flashcard.util.MainActivityPreferences;
import com.ying.flashcard.util.VersionUtil;

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

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_card_container);

		if (findViewById(R.id.flashcard_container) != null) {

			if (savedInstanceState != null) {
				return;
			}

			firstFragment = new SwipeFragment();
			firstFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction().add(R.id.flashcard_container, firstFragment).commit();
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_flash_card_exam, menu);
		
		return true;
	}

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId()){
		
		case R.id.menu_previous_card:
			firstFragment.backToQuestion();
			break;
		case R.id.menu_next_card:
			firstFragment.updateCardDetailView();
			break;
		case R.id.menu_back_to_set_list:
			backToSet();
			break;
		case R.id.menu_about:
			Toast.makeText(getBaseContext(), VersionUtil.getVersionInfo(this)  , Toast.LENGTH_SHORT).show();
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
