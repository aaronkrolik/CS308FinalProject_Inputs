package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * ResourceContainer makes it easy to work with resource files. 
 * @author Aaron Krolik, Gavin Ovsak
 *
 */
public class ResourceMappingObject {
    
    private final String myName;
    private String myPath;
    private ResourceBundle myPersistentResources;
    private Map<String, String> resourceMapping;
    private ArrayList<WebButton> interpretedButtons = new ArrayList<WebButton>();
    
    /**
     * Constructs an object which manages a dynamic behavior mapping.
     * @param name
     */
    public ResourceMappingObject(String name){
        myName = name;
    }
    
    /**
     * Overflow constructor for immediately supplying the mapping resource path
     * @param name
     * @param path
     */
    public ResourceMappingObject(String name, String path){
        this(name);
        setResourcePath(path);    
    }
    
    /**
     * Called when behavior mapping has been modified, goes through the resource
     * mapping and creates a button for each game behavior except for behaviors
     * which are called by Ups and Downs of the same device button. Used for
     * intuitively generating web buttons.
     */
    public void interpretButtons() {
        interpretedButtons.clear();
        WebButton newButtonBehaviors = new WebButton();
        boolean stillNeedsToBeSet = true;
        for(String inputBehavior : resourceMapping.keySet()) {
            newButtonBehaviors = new WebButton();
            stillNeedsToBeSet = true;
            boolean isUp = inputBehavior.substring(inputBehavior.length()-2, inputBehavior.length()).equals("Up");
            boolean isDown = inputBehavior.substring(inputBehavior.length()-4, inputBehavior.length()).equals("Down");
            if(isUp || isDown){
                String conjugateCommand = isUp?inputBehavior.substring(0, inputBehavior.length()-2)+"Down"
                	                          :inputBehavior.substring(0, inputBehavior.length()-4)+"Up";
                for(WebButton button : interpretedButtons) {
                    if(button.getDownBehavior().length() == 0 && isDown && button.getUpInputBehaviors().contains(conjugateCommand)) {
                        button.setDownBehavior(resourceMapping.get(inputBehavior));
                        button.setDownInputBehaviors(inputBehavior);
                        stillNeedsToBeSet = false;
                    }
                    if(button.getUpBehavior().length() == 0 && isUp && button.getDownInputBehaviors().contains(conjugateCommand)) {
                        button.setUpBehavior(resourceMapping.get(inputBehavior));
                        button.setUpInputBehaviors(inputBehavior);
                        stillNeedsToBeSet = false;
                    }
                }                
                if(stillNeedsToBeSet && isUp) { 
                    newButtonBehaviors.setUpBehavior(resourceMapping.get(inputBehavior));
                    newButtonBehaviors.setUpInputBehaviors(newButtonBehaviors.getUpInputBehaviors()+inputBehavior);
                    interpretedButtons.add(newButtonBehaviors);
                }
            } else{
                newButtonBehaviors.setDownBehavior(resourceMapping.get(inputBehavior));
                newButtonBehaviors.setDownInputBehaviors(newButtonBehaviors.getDownInputBehaviors()+inputBehavior);
                interpretedButtons.add(newButtonBehaviors);
            }
        }
    }
    
    /**
     * returns a list of interpretted buttons for use on a web UI
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList<WebButton> getWebButtons() {
        return (ArrayList<WebButton>) interpretedButtons.clone();
    }
    
    /**
     * 
     * @param path
     */
    public void setResourcePath(String path){
        myPath = path;
        resourceMapping = convertResourceBundleToMap( (myPersistentResources = ResourceBundle.getBundle(path)) );
        interpretButtons();
        //DEBUGprintMap(resourceMapping);
    }

    /**
     * Returns the name of this MappingObject
     * @return
     */
    public String getName(){
        return myName;
    }
    
    /**
     * Returns the String of last current resource path given.
     * @return
     */
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
    public String getGameBehavior(String inputBehavior) {
        return resourceMapping.get(inputBehavior);
    }
    
    /**
     * Returns a true value if the given inputBehavior is currently mapped to a game behavior, false if not.
     * @param inputBehavior
     * @return
     */
    public boolean containsInputBehavior(String inputBehavior){
        return resourceMapping.containsKey(inputBehavior);
    }
    
    /**
     * Sets mappings to those found in the current resource file.
     */
    public void restoreDefault(){
        resourceMapping.clear();
        resourceMapping = convertResourceBundleToMap(myPersistentResources);
        interpretButtons();
    }
    
    /**
     * Override the mappings in your current resource file. **watch out! will remove your current mappings**
     * @param gameBehavior 
     * @param inputBehavior
     */
    public void overrideMapping(String gameBehavior, String inputBehaviors) {
        Iterator<Map.Entry<String, String>> iter = resourceMapping.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> KV = iter.next();
            if ( KV.getValue().equals(gameBehavior) || KV.getKey().equals(gameBehavior)){
                iter.remove();
            }
        }
        for (String inputBehavior : parseStr(inputBehaviors))
            resourceMapping.put(inputBehavior, gameBehavior);
        interpretButtons();
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
        
        while (iter.hasNext()){
            entry = iter.next();
            if (temp.containsKey(entry.getValue())){
                temp.put(
                            entry.getValue(),
                            temp.get(entry.getValue()).concat("|" + entry.getKey() )
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
        }
    }
    
    /**
     * Parse string as coming in form resource file. Supports legacy formatting.
     * @param resource
     * @return
     */
     protected Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
            Map<String, String> map = new HashMap<String, String>();
            Enumeration<String> keys = resource.getKeys();
            while (keys.hasMoreElements()) {
                String gameBehavior = keys.nextElement();
                if (resource.getString(gameBehavior).contains("_")) {
                    for (String inputBehavior : parseStr(resource.getString(gameBehavior))) {
                         map.put(inputBehavior, gameBehavior);
                    }
                } else {
                    String oldInputBehavior = gameBehavior;
                    String oldGameBehavior = resource.getString(oldInputBehavior);
                    map.put(oldInputBehavior, oldGameBehavior);//Legacy support for flipped resource files
                }
            }   
            return map;
     }
     
     /**
      * Breaks up a list of inputBehaviors separated by "or" pipes
      * @param in
      * @return parsed string
      */
     protected String[] parseStr(String in){
         return in.split("\\|");
     }

     private void DEBUGprintMap(Map<String, String> map){
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = map.get(key);
                System.out.println(key + " = " + value);
            }
            System.out.println("---");
     }
}
