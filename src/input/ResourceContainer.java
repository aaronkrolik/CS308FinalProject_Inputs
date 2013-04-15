package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
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
		DEBUGprintMap(resourceMapping);
	}

	public String getName(){
		return myName;
	}
	
	public String getCurrentPath(){
		return myPath;
	}
	/**
	 * get corresponding value from our resource map given a key
	 * @param key
	 * @return value
	 * 
	 * TODO handle invalid keys. maybe throw exception 
	 */
	public String getValue(String key) {
		return resourceMapping.get(key);
	}
	
	public boolean containsKey(String key){
		return resourceMapping.containsKey(key);
	}
	
	
	/**
	 * Sets mappings to those found in the current resource file.
	 */
	public void restoreDefualt(){
		resourceMapping.clear();
		resourceMapping = convertResourceBundleToMap(myPersistentResources);
	}
	
	/**
	 * Override the mappings in your current resource file. **watch out! will remove your current mappings**
	 * @param resourceKey 
	 * @param resourceValue
	 */
	public void overrideMapping(String resourceValue, String resourceKey) {
		Iterator<Map.Entry<String, String>> iter = resourceMapping.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> KV = iter.next();
			if ( KV.getValue().equals(resourceKey) ){
				iter.remove();
			}
		}
		for (String x : parseStr(resourceValue))
			resourceMapping.put(x, resourceKey);
	}
	
	/**
	 * writes your current mappings to a file specified by path. if it does not exist, we'll make it.
	 * @param path to file you want. doesn't need to exist
	 * TODO figure out exception handling
	 */
	public void saveMappingsToFile(String path){
		Map<String, String> temp = new HashMap<String, String>();
		Iterator<Map.Entry<String, String>> iter = resourceMapping.entrySet().iterator();
		Map.Entry<String, String> entry;
		
		while(iter.hasNext()){
			entry = iter.next();
			if(temp.containsKey(entry.getValue())){
				temp.put(
							entry.getValue(),
							temp.get( entry.getValue() ).concat("|" + entry.getKey() )
						);
			} else {
				temp.put(entry.getValue(), entry.getKey());
			}
		}
		
		
		
		try {
			PrintWriter out = new PrintWriter(new File(path));
			for (Map.Entry<String,String> ent : temp.entrySet()) {
	            out.println(ent.getKey() + "\t=\t" + ent.getValue());
	        }

	        out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Full disclosure: got this method from stackoverflow
	 * @param resource
	 * @return
	 */
	 protected Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
	        Map<String, String> map = new HashMap<String, String>();
	        Enumeration<String> keys = resource.getKeys();
	        while (keys.hasMoreElements()) {
	            String key = keys.nextElement();
	            map.put(key, resource.getString(key));
	        }   
	        return map;
	 }
	 
	 /**
	  * dont mind me... i'm not hard coded at all
	  * TODO make abstract and all that shit
	  * @param in
	  * @return parsed string
	  */
	 protected String[] parseStr(String in){
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
