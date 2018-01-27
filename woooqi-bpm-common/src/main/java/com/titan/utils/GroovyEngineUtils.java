package com.titan.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.HashMap;
import java.util.Map;


public class GroovyEngineUtils{
  
  public static Object executeScript(String script, Map<String, Object> map){
	  Binding binding = new Binding();
	  Object result = null;
	  try{
		  if (map != null) {
			  for(String key:map.keySet()){
				  binding.setVariable(key, map.get(key)); 
			  }
		  }
		  GroovyShell shell = new GroovyShell(binding);
		  result = shell.evaluate(script);
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return result;
  }
  
  public static void main(String[] args){
	  
	  Map<String, Object> sub = new HashMap<String, Object>();
	  sub.put("bxje", "30000");
	  Object executeScript = executeScript("Integer.parseInt(bxje) > 5000",sub);
	  System.out.println(executeScript);
  }
}
