package com.ying.bible.kjv;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import com.ying.bible.utility.*;

/********************************************************************************************************************************************************************************************************
 * Purpose: 
 * 2016-01-16: to write out the kjv bible for Qian Jun 
 * 
 * This file should be refered to only when generating data files for new version
 ********************************************************************************************************************************************************************************************************/

public class GenerateKJVXmlFiles {
	
	private static StringBuffer sb = new StringBuffer();
	
	private static int bookStartIndex = 1;
	private static int bookEndIndex = 66;
	private static String version = "kjv";
	
	
	public static void main(String[] args) {
		loadDataForVersion(version);

		System.out.println("DONE");
	}
	
	private static void loadDataForVersion(String version) {
		try{
			for (int i=bookStartIndex; i<=bookEndIndex; i++) {
				String bookNumber = "";
				if (i<10) {
					bookNumber = "0" + i;
				} else {
					bookNumber = "" + i;
				}
				
				//lines below will have the caption content
				File fOriginalTxtFile = new File("kjvtemp/" + bookNumber + ".txt");
				ArrayList<String> bookFileLines = new ArrayList<String>();
				FileUtil.getContents(bookFileLines, fOriginalTxtFile);
				
				StringBuffer sb = new StringBuffer();
				
				sb.append(parseBookFileForContent(bookFileLines, i));
				
				FileUtil.writeFile(sb.toString(), "kjv" + i + ".xml");
			}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}
	
	private static String parseBookFileForContent(ArrayList<String> bookFileLines, int bookIndex) {
		
		int chapterNumber = 0;
		
		String chapterMark = "Chapter ";
		
		StringBuffer sb = new StringBuffer();
		
		//add book header
		sb.append(getBookHeader(bookIndex) + "\n");
		
		for (int j=0; j< bookFileLines.size(); j ++) {
			String line = bookFileLines.get(j).toString();
			
			if (line.startsWith("Bible, King James Version")) {
				//ignore
			} else if (line.equals("")) {
				//ignore blank lines
			} else if (line.startsWith(chapterMark)){
				if (chapterNumber != 0 ){
					//if a chapter has been started, need to end it
					sb.append("</Chapter>" + "\n");
				}
				
				chapterNumber = Integer.parseInt(line.substring(chapterMark.length()));
				sb.append(String.format("<Chapter number=\"%s\">", chapterNumber) + "\n");
				
			} else if (line.startsWith("[")){
				//it is a verse line
				String lineNumber = line.substring(1, line.indexOf("]"));
				String content = line.substring(line.indexOf("]") +1);
				
				String verseFormat = "<Line number=\"%s\">%s</Line>";
				sb.append(String.format(verseFormat, lineNumber, content) + "\n");
			}
		}
		
		//end the  last chapter  
		sb.append("</Chapter>"+ "\n");
		
		//add book footer
		sb.append("</Book>"+ "\n");
		
		return sb.toString();
	}
	
	private static String getBookHeader (int bookIndex) {
		ArrayList<String> alBookHeader = new ArrayList<String>();
		
		alBookHeader.add("");
		alBookHeader.add("<Book number=\"01\" name=\"创世纪\" englishName=\"Genesis\" version=\"kjv\" chapters=\"50\">");
		alBookHeader.add("<Book number=\"02\" name=\"出埃及记\" englishName=\"Exodus\" version=\"kjv\" chapters=\"40\">");
		alBookHeader.add("<Book number=\"03\" name=\"利未记\" englishName=\"Leviticus\" version=\"kjv\" chapters=\"27\">");
		alBookHeader.add("<Book number=\"04\" name=\"民数记\" englishName=\"Numbers\" version=\"kjv\" chapters=\"36\">");
		alBookHeader.add("<Book number=\"05\" name=\"申命记\" englishName=\"Deuteronomy\" version=\"kjv\" chapters=\"34\">");
		
		alBookHeader.add("<Book number=\"06\" name=\"约书亚记\" englishName=\"Joshua\" version=\"kjv\" chapters=\"24\">");
		alBookHeader.add("<Book number=\"07\" name=\"士师记\" englishName=\"Judges\" version=\"kjv\" chapters=\"21\">");
		alBookHeader.add("<Book number=\"08\" name=\"路得记\" englishName=\"Ruth\" version=\"kjv\" chapters=\"4\">");
		alBookHeader.add("<Book number=\"09\" name=\"撒母耳记上\" englishName=\"Samuel1\" version=\"kjv\" chapters=\"31\">");
		alBookHeader.add("<Book number=\"10\" name=\"撒母耳记下\" englishName=\"Samuel2\" version=\"kjv\" chapters=\"24\">");
		
		alBookHeader.add("<Book number=\"11\" name=\"列王纪上\" englishName=\"Kings1\" version=\"kjv\" chapters=\"22\">");
		alBookHeader.add("<Book number=\"12\" name=\"列王纪下\" englishName=\"Kings2\" version=\"kjv\" chapters=\"25\">");
		alBookHeader.add("<Book number=\"13\" name=\"历代志上\" englishName=\"Chronicles1\" version=\"kjv\" chapters=\"29\">");
		alBookHeader.add("<Book number=\"14\" name=\"历代志下\" englishName=\"Chronicles2\" version=\"kjv\" chapters=\"36\">");
		alBookHeader.add("<Book number=\"15\" name=\"以斯拉记\" englishName=\"Ezra\" version=\"kjv\" chapters=\"10\">");
		
		alBookHeader.add("<Book number=\"16\" name=\"尼希米记\" englishName=\"Nehemiah\" version=\"kjv\" chapters=\"13\">");
		alBookHeader.add("<Book number=\"17\" name=\"以斯帖记\" englishName=\"Esther\" version=\"kjv\" chapters=\"10\">");
		alBookHeader.add("<Book number=\"18\" name=\"约伯记\" englishName=\"Job\" version=\"kjv\" chapters=\"42\">");
		alBookHeader.add("<Book number=\"19\" name=\"诗篇\" englishName=\"Psalms\" version=\"kjv\" chapters=\"150\">");
		alBookHeader.add("<Book number=\"20\" name=\"箴言\" englishName=\"Proverbs\" version=\"kjv\" chapters=\"31\">");
		
		alBookHeader.add("<Book number=\"21\" name=\"传道书\" englishName=\"Ecclesiastes\" version=\"kjv\" chapters=\"12\">");
		alBookHeader.add("<Book number=\"22\" name=\"雅歌\" englishName=\"Song of Songs\" version=\"kjv\" chapters=\"8\">");
		alBookHeader.add("<Book number=\"23\" name=\"以赛亚书\" englishName=\"Isaiah\" version=\"kjv\" chapters=\"66\">");
		alBookHeader.add("<Book number=\"24\" name=\"耶利米书\" englishName=\"Jeremiah\" version=\"kjv\" chapters=\"52\">");
		alBookHeader.add("<Book number=\"25\" name=\"耶利米哀歌\" englishName=\"Lamentations\" version=\"kjv\" chapters=\"5\">");
		
		alBookHeader.add("<Book number=\"26\" name=\"以西结书\" englishName=\"Ezekiel\" version=\"kjv\" chapters=\"48\">");
		alBookHeader.add("<Book number=\"27\" name=\"但以理书\" englishName=\"Daniel\" version=\"kjv\" chapters=\"12\">");
		alBookHeader.add("<Book number=\"28\" name=\"何西阿书\" englishName=\"Hosea\" version=\"kjv\" chapters=\"14\">");
		alBookHeader.add("<Book number=\"29\" name=\"约珥书\" englishName=\"Joel\" version=\"kjv\" chapters=\"3\">");
		alBookHeader.add("<Book number=\"30\" name=\"阿摩司书\" englishName=\"Amos\" version=\"kjv\" chapters=\"9\">");
		
		alBookHeader.add("<Book number=\"31\" name=\"俄巴底亚书\" englishName=\"Obadiah\" version=\"kjv\" chapters=\"1\">");
		alBookHeader.add("<Book number=\"32\" name=\"约拿书\" englishName=\"Jonah\" version=\"kjv\" chapters=\"4\">");
		alBookHeader.add("<Book number=\"33\" name=\"弥迦书\" englishName=\"Micah\" version=\"kjv\" chapters=\"7\">");
		alBookHeader.add("<Book number=\"34\" name=\"那鸿书\" englishName=\"Nahum\" version=\"kjv\" chapters=\"3\">");
		alBookHeader.add("<Book number=\"35\" name=\"哈巴谷书\" englishName=\"Habakkuk\" version=\"kjv\" chapters=\"3\">");
		
		alBookHeader.add("<Book number=\"36\" name=\"西番雅书\" englishName=\"Zephaniah\" version=\"kjv\" chapters=\"3\">");
		alBookHeader.add("<Book number=\"37\" name=\"哈该书\" englishName=\"Haggai\" version=\"kjv\" chapters=\"2\">");
		alBookHeader.add("<Book number=\"38\" name=\"撒迦利亚书\" englishName=\"Zechariah\" version=\"kjv\" chapters=\"14\">");
		alBookHeader.add("<Book number=\"39\" name=\"玛拉基书\" englishName=\"Malachi\" version=\"kjv\" chapters=\"4\">");
		alBookHeader.add("<Book number=\"40\" name=\"马太福音\" englishName=\"Matthew\" version=\"kjv\" chapters=\"28\">");
		
		alBookHeader.add("<Book number=\"41\" name=\"马可福音\" englishName=\"Mark\" version=\"kjv\" chapters=\"16\">");
		alBookHeader.add("<Book number=\"42\" name=\"路加福音\" englishName=\"Luke\" version=\"kjv\" chapters=\"24\">");
		alBookHeader.add("<Book number=\"43\" name=\"约翰福音\" englishName=\"John\" version=\"kjv\" chapters=\"21\">");
		alBookHeader.add("<Book number=\"44\" name=\"使徒行传\" englishName=\"Acts\" version=\"kjv\" chapters=\"28\">");
		alBookHeader.add("<Book number=\"45\" name=\"罗马书\" englishName=\"Romans\" version=\"kjv\" chapters=\"16\">");
		
		alBookHeader.add("<Book number=\"46\" name=\"哥林多前书\" englishName=\"Corinthians1\" version=\"kjv\" chapters=\"16\">");
		alBookHeader.add("<Book number=\"47\" name=\"哥林多后书\" englishName=\"Corinthians2\" version=\"kjv\" chapters=\"13\">");
		alBookHeader.add("<Book number=\"48\" name=\"加拉太书\" englishName=\"Galatians\" version=\"kjv\" chapters=\"6\">");
		alBookHeader.add("<Book number=\"49\" name=\"以弗所书\" englishName=\"Ephesians\" version=\"kjv\" chapters=\"6\">");
		alBookHeader.add("<Book number=\"50\" name=\"腓力比书\" englishName=\"Philippians\" version=\"kjv\" chapters=\"4\">");
		
		alBookHeader.add("<Book number=\"51\" name=\"歌罗西书\" englishName=\"Colossians\" version=\"kjv\" chapters=\"4\">");
		alBookHeader.add("<Book number=\"52\" name=\"帖撒罗尼迦前书\" englishName=\"Thessalonians1\" version=\"kjv\" chapters=\"5\">");
		alBookHeader.add("<Book number=\"53\" name=\"帖撒罗尼迦后书\" englishName=\"Thessalonians2\" version=\"kjv\" chapters=\"3\">");
		alBookHeader.add("<Book number=\"54\" name=\"提摩太前书\" englishName=\"Timothy1\" version=\"kjv\" chapters=\"6\">");
		alBookHeader.add("<Book number=\"55\" name=\"提摩太后书\" englishName=\"Timothy2\" version=\"kjv\" chapters=\"4\">");
		
		alBookHeader.add("<Book number=\"56\" name=\"提多书\" englishName=\"Titus\" version=\"kjv\" chapters=\"3\">");
		alBookHeader.add("<Book number=\"57\" name=\"腓利门书\" englishName=\"Philemon\" version=\"kjv\" chapters=\"1\">");
		alBookHeader.add("<Book number=\"58\" name=\"希伯来书\" englishName=\"Hebrews\" version=\"kjv\" chapters=\"13\">");
		alBookHeader.add("<Book number=\"59\" name=\"雅各书\" englishName=\"James\" version=\"kjv\" chapters=\"5\">");
		alBookHeader.add("<Book number=\"60\" name=\"彼得前书\" englishName=\"Peter1\" version=\"kjv\" chapters=\"5\">");
		
		alBookHeader.add("<Book number=\"61\" name=\"彼得后书\" englishName=\"Peter2\" version=\"kjv\" chapters=\"3\">");
		alBookHeader.add("<Book number=\"62\" name=\"约翰一书\" englishName=\"John1\" version=\"kjv\" chapters=\"5\">");
		alBookHeader.add("<Book number=\"63\" name=\"约翰二书\" englishName=\"John2\" version=\"kjv\" chapters=\"1\">");
		alBookHeader.add("<Book number=\"64\" name=\"约翰三书\" englishName=\"John3\" version=\"kjv\" chapters=\"1\">");
		alBookHeader.add("<Book number=\"65\" name=\"犹大书\" englishName=\"Jude\" version=\"kjv\" chapters=\"1\">");
		alBookHeader.add("<Book number=\"66\" name=\"启示录\" englishName=\"Revelation\" version=\"kjv\" chapters=\"22\">");
		
 		return alBookHeader.get(bookIndex);
	}
	
	
/*
 



<Book number="01" name="创世纪" englishName="Genesis" version="kjv" chapters="50">
<Book number="02" name="出埃及记" englishName="Exodus" version="kjv" chapters="40">
<Book number="03" name="利未记" englishName="Leviticus" version="kjv" chapters="27">
<Book number="04" name="民数记" englishName="Numbers" version="kjv" chapters="36">
<Book number="05" name="申命记" englishName="Deuteronomy" version="kjv" chapters="34">
<Book number="06" name="约书亚记" englishName="Joshua" version="kjv" chapters="24">
<Book number="07" name="士师记" englishName="Judges" version="kjv" chapters="21">
<Book number="08" name="路得记" englishName="Ruth" version="kjv" chapters="4">
<Book number="09" name="撒母耳记上" englishName="Samuel1" version="kjv" chapters="31">
<Book number="10" name="撒母耳记下" englishName="Samuel2" version="kjv" chapters="24">
<Book number="11" name="列王纪上" englishName="Kings1" version="kjv" chapters="22">
<Book number="12" name="列王纪下" englishName="Kings2" version="kjv" chapters="25">
<Book number="13" name="历代志上" englishName="Chronicles1" version="kjv" chapters="29">
<Book number="14" name="历代志下" englishName="Chronicles2" version="kjv" chapters="36">
<Book number="15" name="以斯拉记" englishName="Ezra" version="kjv" chapters="10">
<Book number="16" name="尼希米记" englishName="Nehemiah" version="kjv" chapters="13">
<Book number="17" name="以斯帖记" englishName="Esther" version="kjv" chapters="10">
<Book number="18" name="约伯记" englishName="Job" version="kjv" chapters="42">
<Book number="19" name="诗篇" englishName="Psalms" version="kjv" chapters="150">
<Book number="20" name="箴言" englishName="Proverbs" version="kjv" chapters="31">
<Book number="21" name="传道书" englishName="Ecclesiastes" version="kjv" chapters="12">
<Book number="22" name="雅歌" englishName="Song of Songs" version="kjv" chapters="8">
<Book number="23" name="以赛亚书" englishName="Isaiah" version="kjv" chapters="66">
<Book number="24" name="耶利米书" englishName="Jeremiah" version="kjv" chapters="52">
<Book number="25" name="耶利米哀歌" englishName="Lamentations" version="kjv" chapters="5">
<Book number="26" name="以西结书" englishName="Ezekiel" version="kjv" chapters="48">
<Book number="27" name="但以理书" englishName="Daniel" version="kjv" chapters="12">
<Book number="28" name="何西阿书" englishName="Hosea" version="kjv" chapters="14">
<Book number="29" name="约珥书" englishName="Joel" version="kjv" chapters="3">
<Book number="30" name="阿摩司书" englishName="Amos" version="kjv" chapters="9">
<Book number="31" name="俄巴底亚书" englishName="Obadiah" version="kjv" chapters="1">
<Book number="32" name="约拿书" englishName="Jonah" version="kjv" chapters="4">
<Book number="33" name="弥迦书" englishName="Micah" version="kjv" chapters="7">
<Book number="34" name="那鸿书" englishName="Nahum" version="kjv" chapters="3">
<Book number="35" name="哈巴谷书" englishName="Habakkuk" version="kjv" chapters="3">
<Book number="36" name="西番雅书" englishName="Zephaniah" version="kjv" chapters="3">
<Book number="37" name="哈该书" englishName="Haggai" version="kjv" chapters="2">
<Book number="38" name="撒迦利亚书" englishName="Zechariah" version="kjv" chapters="14">
<Book number="39" name="玛拉基书" englishName="Malachi" version="kjv" chapters="4">
<Book number="40" name="马太福音" englishName="Matthew" version="kjv" chapters="28">
<Book number="41" name="马可福音" englishName="Mark" version="kjv" chapters="16">
<Book number="42" name="路加福音" englishName="Luke" version="kjv" chapters="24">
<Book number="43" name="约翰福音" englishName="John" version="kjv" chapters="21">
<Book number="44" name="使徒行传" englishName="Acts" version="kjv" chapters="28">
<Book number="45" name="罗马书" englishName="Romans" version="kjv" chapters="16">
<Book number="46" name="哥林多前书" englishName="Corinthians1" version="kjv" chapters="16">
<Book number="47" name="哥林多后书" englishName="Corinthians2" version="kjv" chapters="13">
<Book number="48" name="加拉太书" englishName="Galatians" version="kjv" chapters="6">
<Book number="49" name="以弗所书" englishName="Ephesians" version="kjv" chapters="6">
<Book number="50" name="腓力比书" englishName="Philippians" version="kjv" chapters="4">
<Book number="51" name="歌罗西书" englishName="Colossians" version="kjv" chapters="4">
<Book number="52" name="帖撒罗尼迦前书" englishName="Thessalonians1" version="kjv" chapters="5">
<Book number="53" name="帖撒罗尼迦后书" englishName="Thessalonians2" version="kjv" chapters="3">
<Book number="54" name="提摩太前书" englishName="Timothy1" version="kjv" chapters="6">
<Book number="55" name="提摩太后书" englishName="Timothy2" version="kjv" chapters="4">
<Book number="56" name="提多书" englishName="Titus" version="kjv" chapters="3">
<Book number="57" name="腓利门书" englishName="Philemon" version="kjv" chapters="1">
<Book number="58" name="希伯来书" englishName="Hebrews" version="kjv" chapters="13">
<Book number="59" name="雅各书" englishName="James" version="kjv" chapters="5">
<Book number="60" name="彼得前书" englishName="Peter1" version="kjv" chapters="5">
<Book number="61" name="彼得后书" englishName="Peter2" version="kjv" chapters="3">
<Book number="62" name="约翰一书" englishName="John1" version="kjv" chapters="5">
<Book number="63" name="约翰二书" englishName="John2" version="kjv" chapters="1">
<Book number="64" name="约翰三书" englishName="John3" version="kjv" chapters="1">
<Book number="65" name="犹大书" englishName="Jude" version="kjv" chapters="1">
<Book number="66" name="启示录" englishName="Revelation" version="kjv" chapters="22">

	
*/	
}
