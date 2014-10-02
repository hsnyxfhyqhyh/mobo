package com.chccc.bible.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.chccc.bible.R;
import com.chccc.bible.db.BookHandler;
import com.chccc.bible.util.FileUtility;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class BibleReferenceChooserActivity extends Activity {
public final static String EXTRA_MESSAGE = "com.chccc.bible.BibleReferenceChooser.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bible_webview_activity_main);
		
		WebView myWebView = (WebView) findViewById(R.id.webview);
		
		Intent intent = getIntent();
		
		String bookNumber = BibleMainActivity.preferences.getBookNumber();
		
		String htmlString = getReferenceInHTML(bookNumber);
//		htmlString = new String(htmlString, )
		
//		myWebView.loadData(htmlString, "text/html", "GBK");		
		myWebView.loadDataWithBaseURL (null, htmlString,  "text/html", "utf-8", null);
		
//		String webViewFithPath = intent.getStringExtra(EXTRA_MESSAGE);
		
//		myWebView.loadUrl(webViewFithPath);
		myWebView.setBackgroundColor(Color.TRANSPARENT);
		
	}
	
	@Override
	public void onBackPressed() {
		Intent intentNew = new Intent(this, BibleMainActivity.class);
		this.startActivity(intentNew);
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_reference, menu);
		
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId()){
		
		case R.id.menu_webview:
			Intent intentOld = new Intent(this, BibleMainActivity.class);
			intentOld.putExtra(BibleMainActivity.EXTRA_MESSAGE, BookHandler.OLD_TESTAMENT);
			this.startActivity(intentOld);
			
			this.finish();
			
			break;
		case R.id.menu_rotate:
			int currentOrientation = getResources().getConfiguration().orientation;
			if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
			}
			else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
			   
			}
			break;
	
		}
			
		return true;
	}
	
	private String getReferenceInHTML(String bookNumber) {
		String html = "";
		
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>");
		sb.append("<HEAD>" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
				"</HEAD>");
		sb.append("<BODY>");
		
		try {
			InputStream is = null;
//			is = this.getAssets().open(String.format("references/studyGuide.xml"));
			is = getReferenceConfigureXMLFromStorage();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(is));
			doc.getDocumentElement().normalize();

			NodeList bookNodeList = doc.getElementsByTagName("Book");
			
			if (bookNodeList !=null) {
				
				for (int j=0; j < bookNodeList.getLength(); j++) {
					Element bookElement = (Element) bookNodeList.item(j);
					String bNumber =	bookElement.getAttribute("index");
					
					if (bNumber.equalsIgnoreCase(bookNumber)){
						String hrefTemplate = "<a href=\"%s\">%s</a><br>&nbsp;<br></a>"; 
						NodeList studyNodeList = bookElement.getElementsByTagName("Study");
				
						for (int i = 0; i < studyNodeList.getLength(); i++) {
							Element studyElement = (Element) studyNodeList.item(i);
							
							Element studyName = (Element) studyElement.getElementsByTagName("Name").item(0);
							Element studyAbbreviation = (Element) studyElement.getElementsByTagName("Abbreviation").item(0); 
							Element studyRoot = (Element) studyElement.getElementsByTagName("Root").item(0);
							
							if (studyName!=null && studyName.getTextContent()!=null && !studyName.getTextContent().equals("")) {
								sb.append(String.format(hrefTemplate, studyRoot.getTextContent(), studyName.getTextContent()));
							} else {
								sb.append("没有参考书");
							}
							
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
		sb.append("</BODY>");
		sb.append("</HTML>");
		
		html = sb.toString();
		return html;
	}
	
//	private String getReferenceConfigureXMLFromStorage () {
//		int ch;
//		StringBuffer fileContent = new StringBuffer("");
//		FileInputStream fis;
//		try {
//			String filename = Environment.getExternalStorageDirectory() + FileUtility.REFERENCE_FILE_PATH;
//		    fis =new FileInputStream ( new File(filename));
//		    try {
//		        while( (ch = fis.read()) != -1)
//		            fileContent.append((char)ch);
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}
//
//		return new String(fileContent);
//	}
	
	private InputStream getReferenceConfigureXMLFromStorage () {
		StringBuffer fileContent = new StringBuffer("");
		FileInputStream fis = null;
		try {
			String filename = Environment.getExternalStorageDirectory() + FileUtility.REFERENCE_FILE_PATH;
		    fis =new FileInputStream ( new File(filename));
		   
		} catch (Exception e) {
		    e.printStackTrace();
		}
	
		return fis;
	}
}
