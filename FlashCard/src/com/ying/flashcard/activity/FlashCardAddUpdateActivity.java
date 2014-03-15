package com.ying.flashcard.activity;

import com.ying.flashcard.R;
import com.ying.flashcard.R.layout;
import com.ying.flashcard.R.menu;
import com.ying.flashcard.db.QuestionHandler;
import com.ying.flashcard.db.SetHandler;
import com.ying.flashcard.dto.SetDTO;
import com.ying.flashcard.util.MainActivityPreferences;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class FlashCardAddUpdateActivity extends Activity {

	public MainActivityPreferences preferences = ApplicationInitializeActivity.preferences;
	final Context context = this;
	
	SetHandler setHandler = new SetHandler(context);
	QuestionHandler questionHandler = new QuestionHandler(context);
	
	String setName = preferences.getSetName();
	int setFK ;
	
	String  questionId = preferences.getQuestionId();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_card_add_update);
		
		SetDTO setDTO = setHandler.getSet(setName);
		int setFK = Integer.parseInt(setDTO.getId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_card_add_update, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {
		case R.id.menu_about:
			Toast.makeText(getBaseContext(), "DEVELOPED BY - Yinghui Hu\n" + this.getString(R.string.version_number)  , Toast.LENGTH_SHORT).show();
			break;
		}

		return true;
	}
	
	@Override
	public void onBackPressed() {
		this.finish();
		
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//	    builder.setTitle(this.getString(R.string.alert_dialog_title_back));
//
//	    builder.setPositiveButton(this.getString(R.string.alert_dialog_ok_button_text), new DialogInterface.OnClickListener() {
//	            public void onClick(DialogInterface dialog, int which) {
//	                    dialog.dismiss();
//	                    
//	                    backToSet();
//	                    finish();
//	            }
//	        });
//
//	    builder.setNegativeButton(this.getString(R.string.alert_dialog_notok_button_text), new DialogInterface.OnClickListener() {
//	        @Override
//	        public void onClick(DialogInterface dialog, int which) {
//	            dialog.dismiss();
//	        }
//	    });
//	    AlertDialog alert = builder.create();
//	    alert.show();
//	    
//	    
	}
}
