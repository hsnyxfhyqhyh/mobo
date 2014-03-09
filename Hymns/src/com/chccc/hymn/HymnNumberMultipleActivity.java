package com.chccc.hymn;

import com.chccc.hymn.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HymnNumberMultipleActivity extends Activity {
	Button buttonHymnNumberSave;
	EditText textHymnNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hymn_number_multiple_input);
		
		buttonHymnNumberSave = (Button) findViewById(R.id.buttonHymnNumberSaveMultiple);
		textHymnNumber = (EditText)findViewById(R.id.textHymnNumberMultiple);
		
		buttonHymnNumberSave.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result", textHymnNumber.getText().toString());
				setResult(RESULT_OK, returnIntent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}
