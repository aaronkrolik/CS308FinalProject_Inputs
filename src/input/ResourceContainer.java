package input;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * ResourceContainer makes it easy to work with resource files. 
 * @author aaronkrolik
 *
 */
public class ResourceContainer {
	
	private final String myName;
	private String myPath;
	private ResourceBundle myPersistentResources;
	private Map<String, String> resourceMapping;
	
	
	public ResourceContainer(String name){
		myName = name;
	}
	
	public ResourceContainer(String name, String path){
		this(name);
		setResourcePath(path);	
	}
	
	public void setResourcePath(String path){
		myPath = path;
		resourceMapping = convertResourceBundleToMap( (myPersistentResources = ResourceBundle.getBundle(path)) );
	}

	public String getName(){
		return myName;
	}
	
	public String getCurrentPath(){
		return myPath;
	}
	
	
	/**
	 * Sets mappings to those found in the current resource file.
	 */
	public void restoreDefualt(){
		resourceMapping = convertResourceBundleToMap(myPersistentResources);
	}
	
	/**
	 * Override the mappings in your current resource file. **watch out! will remove your current mappings**
	 * @param resourceKey 
	 * @param resourceValue
	 */
	public void overrideMapping(String resourceKey, String resourceValue) {
		Iterator<Map.Entry<String, String>> iter = resourceMapping.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> KV = iter.next();
			if ( KV.getValue().equals(resourceKey) ){
				iter.remove();
			}
		}
		for (String x : parseStr(resourceValue))
			resourceMapping.put(x, resourceKey);

		DEBUGprintMap(resourceMapping);

	}
	
	/**
	 * Full disclosure: got this method from stackoverflow
	 * @param resource
	 * @return
	 */
	 private Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
	        Map<String, String> map = new HashMap<String, String>();
	        Enumeration<String> keys = resource.getKeys();
	        while (keys.hasMoreElements()) {
	            String key = keys.nextElement();
	            for(String x : parseStr(resource.getString(key))){
	            	map.put(x, key);
	            }
	        }   
			DEBUGprintMap(map);
	        return map;
	 }
	 
	 /**
	  * dont mind me... i'm not hard coded at all
	  * TODO make abstract and all that shit
	  * @param in
	  * @return parsed string
	  */
	 private String[] parseStr(String in){
		 return in.split("\\|");
	 }
	 
	
	 private void DEBUGprintMap(Map<String, String> foo){
			Iterator iterator = foo.keySet().iterator();
	        while (iterator.hasNext()) {
	            String key = (String) iterator.next();
	            String value = foo.get(key);
	            System.out.println(key + " = " + value);
	        }
	        System.out.println("---");
	 }

}
