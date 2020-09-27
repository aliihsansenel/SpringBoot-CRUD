package com.aliihsansenel.springboot.regex;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

	public static void main(String[] args) {
		f("{\r\n" + 
				"	\"itemId\": 8\r\n" + 
				"	\"quantity\": 9\r\n" + 
				"	\"orderDate\": \"22-06-2028\"\r\n" + 
				"}", false);
		
		/*String json = "{\r\n" + 
				"		\"itemId\": 4\r\n" + 
				"		\"quantity\": 5\r\n" + 
				"		\"orderDate\": \"22-02-2020\"\r\n" + 
				"}";
		Pattern p = Pattern.compile(":\\s*\"?(\\d*|.*)[\\s\"}]");
		//Pattern p = Pattern.compile("\\d+\\D");
	    Matcher m = p.matcher(json);   // get a matcher object
	    while(m.find()) {
	    	//System.out.println(Integer.parseInt(json.substring(m.start(), m.end() - 1 )));
	         System.out.println("start(): " + m.start());
	         System.out.println("end(): " + m.end());
	         System.out.print(json.substring(m.start(), m.end()));
	      }*/
	}
	public static void f(String json, boolean update) {
		//System.out.println("ana\"baba".indexOf('\"'));
		Pattern numbersP = Pattern.compile("\\d+\\D");
	    Matcher m = numbersP.matcher(json);
	    if(update && m.find())
	    	System.out.println(Integer.parseInt(json.substring(m.start(), m.end() - 1)));
	    if(m.find())
	    	System.out.println(Integer.parseInt(json.substring(m.start(), m.end() - 1)));
	    if(m.find())
	    	System.out.println(Integer.parseInt(json.substring(m.start(), m.end() - 1)));
	    System.out.println("m.end() string:" + json.substring(m.end()));
	    System.out.println("m.end() kaçtır=:" + m.end());
	    int index1 = json.substring(m.end()).indexOf('\"') + 12 + m.end();
	    System.out.println("index1:" + index1);
	    System.out.println("afterindex1:" + json.substring(index1));
	    index1 += json.substring(index1).indexOf('\"') + 1;
	    System.out.println("index1:" + index1);
	    int index2 = json.substring(index1).indexOf('\"') + index1;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    java.util.Date tempDate = null;
		try {
			System.out.println("tarih:" + json.substring(index1, index2));
			System.out.println(index1);
			System.out.println(index2);
			tempDate = sdf.parse(json.substring(index1, index2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    Date date = new Date(tempDate.getTime());
	    System.out.println("tarih2:" + date);
	}

}
