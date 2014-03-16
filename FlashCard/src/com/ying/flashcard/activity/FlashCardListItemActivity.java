package com.ying.flashcard.activity;

import java.util.ArrayList;

import com.ying.flashcard.R;
import com.ying.flashcard.db.QuestionHandler;
import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.util.MainActivityPreferences;
import com.ying.flashcard.util.VersionUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FlashCardListItemActivity extends Activity {
	
	final Context context = this;
	MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	
	public ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>() ; 
	
	TextView txtCardHeader;
	TextView textCard;
	
	QuestionHandler questionHandler = null;
	
	String questionId = ""; 
	
	private int FONT_SIZE = 20;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_card_list_item);
		
		questionId = preferences.getQuestionId() ;
		questionHandler = new QuestionHandler(context);
		
		if (questionId == null || questionId.trim().equals("")) {
			Toast.makeText(getBaseContext(), "Please choose a valid card"  , Toast.LENGTH_SHORT).show();
			jumpToCardList();
		} else {
			QuestionDTO question = questionHandler.getQuestionById(questionId);
			
			txtCardHeader = (TextView) this.findViewById(R.id.txtCardHeader);
			textCard =  new TextView(this);
			
			txtCardHeader.setText( question.getTitle());
			txtCardHeader.setTextSize(FONT_SIZE + 4);
			txtCardHeader.setTypeface(null, Typeface.BOLD);
			
			textCard.setText( question.getAnswer());
			textCard.setTextSize(FONT_SIZE);
			
			LinearLayout container = (LinearLayout)this.findViewById(R.id.card_container);
			container.addView(textCard);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_card_list_item, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		jumpToCardList();
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {
		case R.id.menu_back_to_set_list:
			jumpToSetList();
			break;
		case R.id.menu_delete_question:
			deleteQuestion();
			break;
		case R.id.menu_edit_question:
			jumpToEditCard();
			break;
		case R.id.menu_about:
			Toast.makeText(getBaseContext(), VersionUtil.getVersionInfo(this)  , Toast.LENGTH_SHORT).show();
			break;
		}

		return true;
	}

	

	private void jumpToCardList() {
		Intent intent= new Intent(this, FlashCardListActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	
	private void jumpToSetList() {
		Intent intent= new Intent(this, SetsListActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	
	private void jumpToEditCard() {
		Intent intent= new Intent(this, FlashCardAddUpdateActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	
	private void deleteQuestion() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("删除卡片?");

		builder.setPositiveButton(
				this.getString(R.string.alert_dialog_ok_button_text),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						questionHandler.delete(questionId);
						dialog.dismiss();
						jumpToCardList();
					}
				});

		builder.setNegativeButton(
				this.getString(R.string.alert_dialog_notok_button_text),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
		
	}
}
