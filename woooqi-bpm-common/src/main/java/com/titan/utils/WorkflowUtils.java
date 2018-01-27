package com.titan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkflowUtils {
	
	
	public final static Pattern pattern = Pattern.compile("<documentation>.*?</documentation>");
	
	

	public static String changeXML(String xml){
		Matcher matcher = pattern.matcher(xml);

		int end = 0;
		StringBuffer sb = new StringBuffer();
		
		while (matcher.find())
		{
			 int start = matcher.start();
			 sb.append(xml.substring(end, start));
		     end = matcher.end();
		     String match = xml.substring(start, end);
		     String filed = match.substring(match.indexOf("<documentation>") + 15, match.indexOf("</documentation>"));
		     sb.append("<documentation>");
		     sb.append(replaceValue(filed));
		     sb.append("</documentation>");
		}
		sb.append(xml.substring(end));
		return sb.toString();
	}
	
	private static String replaceValue(String value){
		
		value = value.replaceAll("&lt;", "<");
		value = value.replaceAll("&gt;", ">");
		if(!value.startsWith("<![CDATA")){
			value = "<![CDATA["+value+"]]>";
		}
		return value;
	}
}
