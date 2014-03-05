package com.ying.flashcard.activity;

import java.util.ArrayList;

import com.ying.flashcard.R;
import com.ying.flashcard.db.SetHandler;
import com.ying.flashcard.dto.SetDTO;
import com.ying.flashcard.util.MainActivityPreferences;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SetsListActivity extends Activity {

	LinearLayout SetListContainer ;
	public SetHandler setHandler;
	
	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	
	private ArrayList<SetDTO> sets;
	TextView textSet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_list);
		
		SetListContainer = (LinearLayout) findViewById(R.id.SetListContainer );
		
		readSets();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private void readSets() {
		SetListContainer.removeAllViews();
		setHandler = new SetHandler(SetsListActivity.this);
		
		sets = setHandler.getSets();
		
		for (int i=0; i< sets.size(); i++) {
				SetDTO setDTO = sets.get(i);
				
				Button btn = new Button(this);
				btn.setText(setDTO.getName());
				
				btn.setOnClickListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v) {
				        doFlashCard(v);
				    }
				});
				
				SetListContainer.addView(btn);
		}
	}
	
	
	@Override
	public void onBackPressed() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(this.getString(R.string.alert_dialog_title));

	    builder.setPositiveButton(this.getString(R.string.alert_dialog_ok_button_text), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                    dialog.dismiss();
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		/*
		switch (requestCode) {
		case RESULT_SETTINGS:
			readBible();
			break;
		}
		*/

	}
	
	private void doFlashCard(View v) {
		Button btn = (Button) v;
		
		String setName_ = btn.getText().toString();
		
		
		preferences.setSetName(setName_);
		preferences.commit();
		
		Intent intent = new Intent(this, FlashCardDetailActivity.class);
		startActivity(intent);
		finish();
	}
	
}
