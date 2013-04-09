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
		System.out.println(action);
		try {
			if(RESOURCES.containsKey(action))
				execute(RESOURCES.getString(action), object);
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception");
		}
	}
}



//package input;
//
//import java.lang.annotation.Annotation;
//import java.lang.ref.WeakReference;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//import javax.swing.JComponent;
//
///**
// * 
// * @author Gavin Ovsak, aaronkrolik
// * 
// */
//@SuppressWarnings("rawtypes")
//public class Input {
//
//	private final ResourceBundle RESOURCES;
//
//	List<WeakReference> myWeakReferences = new ArrayList<WeakReference>(); // to
//																			// avoid
//																			// memory
//																			// leaks
//	Map<String, Method> keyToMethod = new HashMap<String, Method>();
//	Map<String, Object> keyToInstance = new HashMap<String, Object>();
//	ArrayList<InputDevice> inputDevices = new ArrayList<InputDevice>(); // still
//																		// not
//																		// sure
//																		// if
//																		// needed
//
//<<<<<<< HEAD
//	/**
//	 * (Convenience) Singleton constructor. I'm almost as ashamed as I am lazy
//	 * 
//	 * @param String
//	 *            shameful
//	 * @param JComponent
//	 *            hack
//	 * @return Input instance
//	 */
//	public static Input newSingletonInput(String resourcePath,
//			JComponent component) {
//		if (myInstance == null) {
//			myInstance = new Input(resourcePath, component);
//		}
//		return myInstance;
//	}
//
//	public static Input getSingeltonInput() {
//		return myInstance;
//	}
//
//	private Input(String resourcePath, JComponent component) {
//=======
//	public Input(String resourcePath, JComponent component) {
//>>>>>>> 396ba00f8deb153d865bb4b0007c31d9916e5149
//		RESOURCES = ResourceBundle.getBundle(resourcePath);
//		inputDevices.add(new KeyboardModule(component, this));
//		inputDevices.add(new MouseModule(component, this));
//	}
//
//	/**
//	 * Include input instance in our collection of instances that can have
//	 * annotated methods invoked.
//	 * 
//	 * @param Object
//	 *            input. any object will do TODO handle overloaded methods. need
//	 *            incorporate the param field
//	 * 
//<<<<<<< HEAD
//=======
//	 * @param Object input
//>>>>>>> 396ba00f8deb153d865bb4b0007c31d9916e5149
//	 */
//	@SuppressWarnings("unchecked")
//	public void addListenerTo(Object in) {
//		myWeakReferences.add(new WeakReference(in));
//		Class inputClass = in.getClass();
//<<<<<<< HEAD
//		while (inputClass != Object.class) {
//			if (inputClass.getAnnotation(InputClassTarget.class) != null) {
//				for (Method method : inputClass.getMethods()) {
//					Annotation annotation = method
//							.getAnnotation(InputMethodTarget.class);
//					if (annotation instanceof InputMethodTarget) {
//						System.out.println("reflecting"
//								+ ((InputMethodTarget) annotation).name());
//						keyToMethod
//								.put(((InputMethodTarget) annotation).name(),
//										method);
//					}
//=======
//		if (inputClass.getAnnotation(InputClassTarget.class) != null) {
//			for (Method method : inputClass.getMethods()) {
//				Annotation annotation = method
//						.getAnnotation(InputMethodTarget.class);
//				if (annotation instanceof InputMethodTarget) {
//					keyToMethod.put(((InputMethodTarget) annotation).name(),
//							method);
//>>>>>>> 396ba00f8deb153d865bb4b0007c31d9916e5149
//				}
//			}
//			inputClass=inputClass.getSuperclass();
//		}
//	}
//
//	/**
//<<<<<<< HEAD
//	 * Remove an instance from our cache. This means that it will not be called
//	 * even if it has relevant target
//	 * 
//	 * @param Object
//	 *            input (any type will do)
//	 */
//	public void removeListener(Object in) {
//		for (int i = 0; i < myWeakReferences.size(); i++) {
//			if (in == myWeakReferences.get(i).get()) {
//				myWeakReferences.remove(i);
//				System.out.println("out!");
//				break;
//			}
//		}
//	}
//
//	/**
//	 * Uses nifty methodObject and instance caches to reflexively invoke methods
//	 * TODO: get working with the methodInputs TODO: real exceptions ...
//=======
//	 * Executes methods using reflection
//	 * TODO: Better exception handling
//>>>>>>> 396ba00f8deb153d865bb4b0007c31d9916e5149
//	 * 
//	 * @param key
//	 * @param in
//	 */
//	public void execute(String key, ActionObject in) {
//		for (WeakReference x : myWeakReferences) {
//			try {
//				Class[] paramClasses = keyToMethod.get(key).getParameterTypes();
//				if(paramClasses[0].isInstance(in))
// 					keyToMethod.get(key).invoke(x.get(), paramClasses[0].cast(in));
//			} catch (IllegalArgumentException e) {
//			} catch (IllegalAccessException e) {
//			} catch (InvocationTargetException e) {
//			} catch (NullPointerException e) {
//			}
//		}
//	}
//
//	/**
//<<<<<<< HEAD
//	 * actionNotification aka massiveHack. TODO: make less shitty.
//	 * 
//	 * @param action
//	 * @param object
//	 * 
//=======
//	 * Notification receiver from input devices
//	 * 
//	 * @param action
//	 * @param object
//>>>>>>> 396ba00f8deb153d865bb4b0007c31d9916e5149
//	 */
//	public void actionNotification(String action, ActionObject object) {
//		System.out.println(action);
//		try {
//			if(RESOURCES.containsKey(action))
//				execute(RESOURCES.getString(action), object);
//		} catch (NullPointerException e) {
//			System.out.println("Null Pointer Exception");
//		}
//	}
//}
