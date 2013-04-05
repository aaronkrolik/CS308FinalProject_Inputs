package input;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	List<WeakReference> myWeakReferences = new ArrayList<WeakReference>();
	Map<String, Method> keyToMethod = new HashMap<String, Method>();
	Map<String, Object> keyToInstance = new HashMap<String, Object>();
	ArrayList<InputDevice> inputDevices = new ArrayList<InputDevice>(); //still not sure if needed 

	public Input(String resourcePath, JComponent component) {
		RESOURCES = ResourceBundle.getBundle(resourcePath);
		inputDevices.add(new KeyboardModule(component, this));
		inputDevices.add(new MouseModule(component, this));
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
	}

	/**
	 * Executes methods using reflection
	 * TODO: Better exception handling
	 * 
	 * @param key
	 * @param in
	 */
	public void execute(String key, ActionObject in) {
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

	/**
	 * Notification receiver from input devices
	 * 
	 * @param action
	 * @param object
	 */
	public void actionNotification(String action, ActionObject object) {
		try {
			if(RESOURCES.containsKey(action))
				execute(RESOURCES.getString(action), object);
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception");
		}
	}
}
