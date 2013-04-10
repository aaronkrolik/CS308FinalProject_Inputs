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

	private final ResourceBundle RESOURCES;

	private List<WeakReference> myWeakReferences = new ArrayList<WeakReference>();
	private Map<String, Method> keyToMethod = new HashMap<String, Method>();
	private Map<String, Object> keyToInstance = new HashMap<String, Object>();
	private ArrayList<InputDevice> inputDevices = new ArrayList<InputDevice>(); //still not sure if needed 
	private static ResourceBundle SETTINGS;
	private static ResourceBundle DEFAULT_SETTINGS;
	private Map<String, String> dynamicMapping = new HashMap<String, String>();

	public Input(String resourcePath, JComponent component) {
		RESOURCES = ResourceBundle.getBundle(resourcePath);
		inputDevices.add(new KeyboardInput(component, this));
		inputDevices.add(new MouseInput(component, this));
		DEFAULT_SETTINGS = ResourceBundle.getBundle("input/DefaultSettings");
	}
	
	public void overrideSettings(String resourcePath){
		SETTINGS = ResourceBundle.getBundle(resourcePath);
	}

	public String getSetting(String in){
		try{
			if(SETTINGS != null) {
				return SETTINGS.getString(in);
			} else {
				return DEFAULT_SETTINGS.getString(in);
			}
		} catch (MissingResourceException e) {
			return DEFAULT_SETTINGS.getString(in);
		}
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
					Annotation annotation = method
							.getAnnotation(InputMethodTarget.class);
					if (annotation instanceof InputMethodTarget) {
						keyToMethod.put(((InputMethodTarget) annotation).name(),
								method);
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
	 * Executes methods using reflection
	 * TODO: Better exception handling
	 * 
	 * @param key
	 * @param in
	 */
	public void execute(String key, AlertObject in) {
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
	
	public void setMapping(String inputBehavior, String gameBehavior) {
		dynamicMapping.put(inputBehavior, gameBehavior);
	}
	
	/**
	 * Notification receiver from input devices
	 * 
	 * @param action
	 * @param object
	 */
	public void actionNotification(String action, AlertObject object) {
		System.out.println(action);
		try {
			if(dynamicMapping.containsKey(action)) {
				execute(dynamicMapping.get(action), object);
			} else if(RESOURCES.containsKey(action)) {
				execute(RESOURCES.getString(action), object);
			}
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception");
		} catch (MissingResourceException e){
			System.out.println("Missing Resource Exception! Resources did not contain: " + action);
		}
		
	}
}

