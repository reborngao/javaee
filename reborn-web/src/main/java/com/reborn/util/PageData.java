package com.reborn.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 
* @Title   说明：参数封装Map

* @Description: TODO

* @author reborngao  

* @date 2016年12月11日 
 
* @version V1.0
 */
public class PageData extends HashMap   implements Map{
	
   public  static final String  KEYDATA="KEYDATA";
	
	private static final long serialVersionUID = 1L;

	Map map = null;
	public PageData (HttpServletRequest request){
		Map properties=request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator iterator= properties.entrySet().iterator();
		Map.Entry entry; 
		String name = "";  
		String value = "";  
		while(iterator.hasNext()){
			entry= (Map.Entry) iterator.next();
			name=(String)entry.getKey();
			Object  valueObj= entry.getValue();
			 if(null==valueObj){
				 value="";
			 }
			 else if(valueObj instanceof String[]){
				 String [] values=(String[]) valueObj;
				 for(String s:values){
					 value+=s+",";
				 }
				 value=value.substring(0,value.length()-1);
			 }
			 else{
				 value=valueObj.toString();
			 }
			 returnMap.put(name, value);
		}  
		map = returnMap;
	}
	
	public PageData(){
		map=new HashMap();
	}
	
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection values() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getString(String key) {
		return (String)map.get(key);
	}
	
}
