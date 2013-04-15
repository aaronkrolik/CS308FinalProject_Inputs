package input;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourceContainerReversed extends ResourceContainer{
	
	public ResourceContainerReversed(String name){
		super(name);
	}
	
	public ResourceContainerReversed(String name, String path){
		super(name, path);	
	}
	
	/**
	 * Full disclosure: got this method from stackoverflow
	 * @param resource
	 * @return
	 */
	@Override
	 protected Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
	        Map<String, String> map = new HashMap<String, String>();
	        Enumeration<String> keys = resource.getKeys();
	        while (keys.hasMoreElements()) {
	            String key = keys.nextElement();
	            for(String x : parseStr(resource.getString(key))){
	            	map.put(x, key);
	            }
	        }   
	        return map;
	 }
	 

}
