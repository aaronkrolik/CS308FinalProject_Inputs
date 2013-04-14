package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class ResourceContainer {
	
	private final String myName;
	private ResourceBundle myPersistentResources;
	private Map<String, String> resourceMapping;
	
	
	
	public ResourceContainer(String name){
		myName = name;
	}
	
	public ResourceContainer(String name, String path){
		this(name);
		setResourcePath(path);	
	}
	
	public void overrideMapping(String key, String value){
		resourceMapping.put(key, value);
	}
	
	
	public void setResourcePath(String path){
			myPersistentResources = ResourceBundle.getBundle(path); 
			Map<String, String> foo = convertResourceBundleToMap(myPersistentResources);
	}
	
	 private Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
	        Map<String, String> map = new HashMap<String, String>();

	        Enumeration<String> keys = resource.getKeys();
	        while (keys.hasMoreElements()) {
	            String key = keys.nextElement();
	            for(String x : resource.getString(key).split("\\|")){
	            	map.put(x, key);
	            }
	        }   
			DEBUGprintMap(map);
	        return map;
	 }
	 
	 
	
	 private void DEBUGprintMap(Map<String, String> foo){
			Iterator iterator = foo.keySet().iterator();
	        while (iterator.hasNext()) {
	            String key = (String) iterator.next();
	            String value = foo.get(key);
	            System.out.println(key + " = " + value);
	        }
	 }

}
