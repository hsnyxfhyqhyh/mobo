package com.ying.flashcard.activity;

import com.ying.flashcard.R;
import com.ying.flashcard.db.QuestionHandler;
import com.ying.flashcard.db.SetHandler;
import com.ying.flashcard.dto.QuestionDTO;
import com.ying.flashcard.dto.SetDTO;
import com.ying.flashcard.util.MainActivityPreferences;
import com.ying.flashcard.util.VersionUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class FlashCardAddUpdateActivity extends Activity {

	public MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	final Context context = this;
	
	SetHandler setHandler = new SetHandler(context);
	QuestionHandler questionHandler = new QuestionHandler(context);
	
	String setName = preferences.getSetName();
	int setFK ;
	
	String  questionId = preferences.getQuestionId();
	
	EditText txtQuestionTitle;
	EditText txtQuestionsContent;
	
	
	QuestionDTO question ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_card_add_update);
		
		SetDTO setDTO = setHandler.getSet(setName);
		setFK = Integer.parseInt(setDTO.getId());
		
		txtQuestionTitle = (EditText) this.findViewById(R.id.txtQuestionTitle);
		txtQuestionsContent =  (EditText)this.findViewById(R.id.txtQuestionsContent);
		
		if (!questionId.equals("")) {
			question = questionHandler.getQuestionById(questionId);
			
			txtQuestionTitle.setText(question.getTitle());
			txtQuestionsContent.setText(question.getAnswer());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.flash_card_add_update, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {
		case R.id.menu_save_card:
			saveCard();
			break;
		case R.id.menu_about:
			Toast.makeText(getBaseContext(), VersionUtil.getVersionInfo(this)  , Toast.LENGTH_SHORT).show();
			break;
		}

		return true;
	}
	
	
	@Override
	public void onBackPressed() {
		String title = txtQuestionTitle.getText().toString().trim();
		String content = txtQuestionsContent.getText().toString().trim();
		
		if (!title.equals("") || !content.equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle(this.getString(R.string.alert_dialog_title_add_update_card));

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
		} else {
			jumpToCardList();
		}
		
	}

	private void jumpToCardList() {
		Intent intent = new Intent(this, FlashCardListActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	
	
	private void saveCard() {
		String title = txtQuestionTitle.getText().toString().trim();
		String content = txtQuestionsContent.getText().toString().trim();
		
		if (!title.equals("") && !content.equals("")) {
			if (!questionId.equals("")) {
				question.setAnswer(content);
				question.setTitle(title);

				questionHandler.update(question);
				
				preferences.setQuestionId("");
				preferences.commit();
				Intent parent = new Intent(this, FlashCardListActivity.class);
				this.startActivity(parent);
								
				this.finish();
				
			} else {
				//add
				question = new QuestionDTO();
				question.setTitle(title);
				question.setAnswer(content);
				
				questionHandler.create(question, setFK);
				Intent parent = new Intent(this, FlashCardListActivity.class);
				this.startActivity(parent);
				
				this.finish();
			}
			
		} else {
			Toast.makeText(getBaseContext(), "Please input valid question"  , Toast.LENGTH_SHORT).show();
		}
		
	}

}
