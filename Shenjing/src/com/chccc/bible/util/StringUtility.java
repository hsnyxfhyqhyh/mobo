package com.chccc.bible.util;

public class StringUtility {
	public static String paddingLeadingzeros(int length, int number) { 
		
		 String n = number + "";
		 while (n.length() < length) {
			 n = "0" + n;
		 }
		
		 return n;
	}
}
