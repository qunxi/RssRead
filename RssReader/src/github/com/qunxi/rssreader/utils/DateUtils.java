package github.com.qunxi.rssreader.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtils {
	
	//EEE
	/*public static final String DATE_ATOM = "Atom  Atom ( 2005-08-15T15:52:01+00:00)";
	
	public static final String DATE_ISO8601	= "ISO-8601 ( 2005-08-14T16:13:03+0000)"; 
	public static final String DATE_RFC822 = "RFC 822 ( Sun, 14 Aug 2005 16:13:03 UTC)";	 
	public static final String DATE_RFC850 = "RFC 850 ( Sunday, 14-Aug-05 16:13:03 UTC)"; 
	public static final String DATE_RFC1036 = "RFC 1036 ( Sunday, 14-Aug-05 16:13:03 UTC)";	 
	public static final String DATE_RFC1123 = "RFC 1123 ( Sun, 14 Aug 2005 16:13:03 UTC)"; 
	public static final String DATE_RFC2822 = "RFC 2822 (Sun, 14 Aug 2005 16:13:03 +0000)";
	public static final String DATE_RSS = "RSS (Sun, 14 Aug 2005 16:13:03 UTC)";
	
	public static final String DATE_W3C	= "World Wide Web Consortium ( 2005-08-14T16:13:03+0000)";	 
	public static final String DATE_COOKIE ="HTTP Cookies ( Sun, 14 Aug 2005 16:13:03 UTC)";*/
	
	
	public static final String DATE_ATOM_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";//2005-08-15T15:52:01
	public static final String DATE_RFC822_PATTERN = "[a-zA-Z]{3},\\s{1}\\d{2}\\s{1}[a-zA-Z]{3}\\s{1}\\d{4}\\s{1}\\d{2}:\\d{2}:\\d{2}";//Sun, 14 Aug 2005 16:13:03 UTC
	public static final String DATE_RFC1036_PATTERN = "[a-zA-Z]{6,9},\\s{1}\\d{2}-[a-zA-Z]{3}-\\d{4}\\s{1}\\d{2}:\\d{2}:\\d{2}"; //Sunday, 14-Aug-05 16:13:03 UTC
	
	public static final String FINAL_SAVE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String ATOM_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String RSS_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss";
	

	@SuppressLint("SimpleDateFormat")
	public static String AtomDateConvert(String fromDate){
		Pattern p=Pattern.compile(DATE_ATOM_PATTERN);
		Matcher m=p.matcher(fromDate);
		if(m.find()){
			String matchDate = m.group(0);
			SimpleDateFormat sdf = new SimpleDateFormat(ATOM_DATE_FORMAT);
			try {
				Date t = sdf.parse(matchDate);
				sdf.applyPattern(FINAL_SAVE_FORMAT);
				return sdf.format(t);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fromDate;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String RssDateConvert(String fromDate){
		Pattern p=Pattern.compile(DATE_RFC822_PATTERN);
		Matcher m=p.matcher(fromDate);
		if(m.find()){
			String matchDate = m.group(0);
			SimpleDateFormat sdf = new SimpleDateFormat(RSS_DATE_FORMAT, Locale.US);
			sdf.setLenient(true);  
			try {
				Date t = sdf.parse(matchDate);
				sdf.applyPattern(FINAL_SAVE_FORMAT);
				return sdf.format(t);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fromDate;
	}

	@SuppressLint("SimpleDateFormat")
	public static boolean isLarge(String src, String target){
		DateFormat df = new SimpleDateFormat(FINAL_SAVE_FORMAT);
	    try {
	    	Date dSrc = df.parse(src);
			Date dTarget = df.parse(target);
			return dSrc.compareTo(dTarget) > 0 ? true : false;
			
		} catch (ParseException e) {
			Log.e("DateUtils", "parse is error");
			e.printStackTrace();
			return src.compareTo(target) > 0 ? true : false;
		}
	}
}
