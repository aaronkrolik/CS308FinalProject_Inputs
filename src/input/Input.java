package input;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JComponent;

/**
 * Input API built to allow games to accept input from an expandable set of input devices.
 * 
 * @author Gavin Ovsak, aaronkrolik
 * 
 */
@SuppressWarnings("rawtypes")
public class Input {

	private List<WeakReference> myWeakReferences = new ArrayList<WeakReference>();
	private Map<String, Method> keyToMethod = new HashMap<String, Method>();
	private ArrayList<InputDevice> inputDevices = new ArrayList<InputDevice>(); 
	
	private ResourceContainer MAPPINGRESOURCE;
	private static ResourceContainer SETTINGS;
	private static ResourceBundle DEFAULT_SETTINGS;
	

	
	public Input(String inputMapResourcePath, JComponent component) {
		this(inputMapResourcePath, "input/DefaultSettings", component);
	}
	
	public Input(String inputMapResourcePath, String settingsResourcePath, JComponent component) {
		MAPPINGRESOURCE = new ResourceContainerReversed("mapping", inputMapResourcePath);
		SETTINGS = new ResourceContainer("settings", settingsResourcePath);
		inputDevices.add(new KeyboardInput(component, this));
		inputDevices.add(new MouseInput(component, this));
	}
	
	/**
	 * put new key/values in our dynaicMapping object
	 * TODO: not a fan of two objects. Lets try to put it into RESOURCES or get new path. 
	 * 			also, don't know if this solves the root problem. abk
	 * TODO: need to be able to remove mappings. abk
	 * @param inputBehavior
	 * @param gameBehavior
	 */
	public void overrideMapping(String inputBehavior, String gameBehavior) {
		MAPPINGRESOURCE.overrideMapping(inputBehavior, gameBehavior);
	}
	
//	/**
//	 * populate RESOURCES with resource file at path
//	 * @param path to resource file
//	 */

	
	public void restoreMappingDefualts(){
		MAPPINGRESOURCE.restoreDefualt();
	}

	/**
	 * Override our default input settings
	 * @param String resourcePath is the relative location to the settings resource file
	 */
	public void overrideSettings(String resourcePath){
		SETTINGS.setResourcePath(resourcePath);
	}

	/**
	 * Include input instance in our collection of instances that can have
	 * annotated methods invoked
	 * 
	 * @param Object input
	 */
	@SuppressWarnings("unchecked")
	public void addListenerTo(Object in) {

		myWeakReferences.add(new WeakReference(in));
		Class inputClass = in.getClass();
		
		while (inputClass != Object.class){
			if (inputClass.getAnnotation(InputClassTarget.class) != null) {
				for (Method method : inputClass.getMethods()) {
					Annotation annotation = method.getAnnotation(InputMethodTarget.class);
					if (annotation instanceof InputMethodTarget) {
						keyToMethod.put(((InputMethodTarget) annotation).name(),method);
					}
				}
			}
			inputClass = inputClass.getSuperclass();
		}
	}
	
	/**
	 * Removes instance from our cache of instances to invoke methods on
	 * @param Instance to be removed
	 */
	public void removeListener(Object in) {
		for (int i = 0; i < myWeakReferences.size(); i++) {
			if (in == myWeakReferences.get(i).get()) {
				myWeakReferences.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Get a setting from our SETTINGS resource file object
	 * @param String key in
	 * @return String value out
	 */
	protected String getSetting(String in){
		return SETTINGS.getValue(in);
	}

	/**
	 * Notification receiver from input devices
	 * TODO not a fan of two maps. abk
	 * TODO better exception handling
	 * @param String action (key for dynamicMapping)
	 * @param AlertObject object (input state and specifics)
	 */
	void actionNotification(String action, AlertObject object) {
		try {
			if (MAPPINGRESOURCE.containsKey(action)) {
				execute(MAPPINGRESOURCE.getValue(action), object);
			}
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception");
		} catch (MissingResourceException e) {
			System.out
					.println("Missing Resource Exception! Resources did not contain: "
							+ action);
		}
	}
	
	/**
	 * Executes methods using reflection
	 * TODO: handle exceptions 
	 * @param key
	 * @param in
	 */
	private void execute(String key, AlertObject in) {
		for (WeakReference x : myWeakReferences) {
			try {
				Class[] paramClasses = keyToMethod.get(key).getParameterTypes();
				if(paramClasses[0].isInstance(in))
 					keyToMethod.get(key).invoke(x.get(), paramClasses[0].cast(in));
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			} catch (NullPointerException e) {
			}
		}
	}
	
}

