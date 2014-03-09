package com.chccc.hymn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.chccc.hymn.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HymnMainActivity extends Activity {

	public final static int MENU_HYMN_NUMBER = 1; 
	
	public final static int MENU_HYMN_NUMBER_MULTIPLE = 2;
	
	private static int fontSize = 18; 
	

	public static void setFontSize(int fontSize) {
		HymnMainActivity.fontSize = fontSize;
	}

	//	TextView textViewHymn;
	LinearLayout hymnContainer ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hymn_activity_main);
		
		hymnContainer = (LinearLayout) findViewById(R.id.hymnContainer );
		readHymn("001", hymnContainer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, this.MENU_HYMN_NUMBER, 0, this.getString(R.string.menu_text_choose_hymn));
		
		menu.add(0, this.MENU_HYMN_NUMBER_MULTIPLE, 0, this.getString(R.string.menu_text_choose_hymn_multiple));
		
		return true;
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == MENU_HYMN_NUMBER) {

			if (resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				hymnContainer.removeAllViews();
				readHymn (result, hymnContainer);
				
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		} else if (requestCode == MENU_HYMN_NUMBER_MULTIPLE) {
			if (resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				hymnContainer.removeAllViews();
				
				StringTokenizer st = new StringTokenizer(result, " ");
				
				String text = "";
				while (st.hasMoreTokens()) {
					readHymn (st.nextToken(), hymnContainer);
				}
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}

	@Override
	public void onBackPressed() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(this.getString(R.string.alert_dialog_title));
	    //builder.setMessage("Are You Sure?");

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
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()){
		case MENU_HYMN_NUMBER:
			Intent intent = new Intent(this, HymnNumberActivity.class);
			this.startActivityForResult(intent, MENU_HYMN_NUMBER);
			break;
		case MENU_HYMN_NUMBER_MULTIPLE:
			Intent intent2 = new Intent(this, HymnNumberMultipleActivity.class);
			this.startActivityForResult(intent2, MENU_HYMN_NUMBER_MULTIPLE);
		}
		
		return true;
	}

	private void readHymn(String hymnNumber, View v) {
		InputStream is = null;
		String inputStreamString = "";

		try {
			int number = Integer.parseInt(hymnNumber);

			if (number > 536) {
				TextView textViewHymn = new TextView(this);
				
				textViewHymn.setTextSize(fontSize);
				textViewHymn.setText(R.string.text_hymn_number_max_number);
				hymnContainer.addView(textViewHymn);
				
			}

		} catch (Exception e) {
			TextView textViewHymn = new TextView(this);
			
			textViewHymn.setTextSize(fontSize);
			textViewHymn.setText(R.string.text_hymn_number_non_number);
			hymnContainer.addView(textViewHymn);
		}

		try {
			while (hymnNumber.length() < 3) {
				hymnNumber = "0" + hymnNumber;
			}
			is = this.getAssets().open("h" + hymnNumber + ".txt");
			
			BufferedReader input = new BufferedReader(new InputStreamReader(is));

			int i = 1;
			try {
				String line = null; 
				while ((line = input.readLine()) != null ) {
					if (i==1) {
						TextView textViewHymnHeader = new TextView(this);
						
						textViewHymnHeader.setTextSize(30);
						textViewHymnHeader.setBackgroundColor(Color.parseColor(this.getString(R.string.color_hymn_header)));
//						textViewHymnHeader.setTypeface(null, Typeface.BOLD);
						textViewHymnHeader.setText(line);
						
						hymnContainer.addView(textViewHymnHeader);
						i++;
					} else if (i==2){
						TextView textViewHymnSubHeader = new TextView(this);
						textViewHymnSubHeader.setTextSize(fontSize);
						textViewHymnSubHeader.setText(line);
						textViewHymnSubHeader.setTypeface(null, Typeface.BOLD);
						hymnContainer.addView(textViewHymnSubHeader);
						i++;
					} else {
						inputStreamString = inputStreamString + line + "\n";
					}
				}
			} catch(Exception ie) {
				
			}

			is.close();

		} catch (Exception em) {
			em.printStackTrace();
		}

		TextView textViewHymn = new TextView(this);
		
		textViewHymn.setTextSize(fontSize +4);
		textViewHymn.setText(inputStreamString);
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/STKAITI.TTF");
		textViewHymn.setTypeface(face);
		hymnContainer.addView(textViewHymn);
		
	}
}
