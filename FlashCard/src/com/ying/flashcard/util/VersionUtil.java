package com.ying.flashcard.util;

import com.ying.flashcard.R;

import android.content.Context;

public class VersionUtil {
	public static String getVersionInfo(Context context) {
		return "DEVELOPED BY - Yinghui Hu\n" + context.getString(R.string.version_number);
	}
}
