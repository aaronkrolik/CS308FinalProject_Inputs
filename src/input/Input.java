package input;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
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

import examples.Player;

/**
 * q Singleton Input class to handle all sorts of input goodies behind the hood
 * 
 * @author aaronkrolik
 * 
 */
public class Input {

	private static Input myInstance;

	private final ResourceBundle RESOURCES;

	List<WeakReference> myWeakReferences = new ArrayList<WeakReference>(); // to
																			// avoid
																			// memory
																			// leaks
	Map<String, Method> keyToMethod = new HashMap<String, Method>();
	Map<String, Object> keyToInstance = new HashMap<String, Object>();
	ArrayList<InputDevice> inputDevices = new ArrayList<InputDevice>(); // still
																		// not
																		// sure
																		// if
																		// needed

	/**
	 * (Convenience) Singleton constructor. I'm almost as ashamed as I am lazy
	 * 
	 * @param String
	 *            shameful
	 * @param JComponent
	 *            hack
	 * @return Input instance
	 */
	public static Input newSingletonInput(String resourcePath,
			JComponent component) {
		if (myInstance == null) {
			myInstance = new Input(resourcePath, component);
		}
		return myInstance;
	}

	public static Input getSingeltonInput() {
		return myInstance;
	}

	private Input(String resourcePath, JComponent component) {
		RESOURCES = ResourceBundle.getBundle(resourcePath);
		inputDevices.add(new KeyboardModule(component));
		inputDevices.add(new MouseModule(component));
	}

	/**
	 * Include input instance in our collection of instances that can have
	 * annotated methods invoked.
	 * 
	 * @param Object
	 *            input. any object will do TODO handle overloaded methods. need
	 *            incorporate the param field
	 * 
	 */
	public void addListenerTo(Object in) {
		myWeakReferences.add(new WeakReference(in));
		Class inputClass = in.getClass();
		while (inputClass != Object.class) {
			if (inputClass.getAnnotation(InputClassTarget.class) != null) {
				for (Method method : inputClass.getMethods()) {
					Annotation annotation = method
							.getAnnotation(InputMethodTarget.class);
					if (annotation instanceof InputMethodTarget) {
						System.out.println("reflecting"
								+ ((InputMethodTarget) annotation).name());
						keyToMethod
								.put(((InputMethodTarget) annotation).name(),
										method);
					}
				}
			}
			inputClass=inputClass.getSuperclass();
		}
	}

	/**
	 * Remove an instance from our cache. This means that it will not be called
	 * even if it has relevant target
	 * 
	 * @param Object
	 *            input (any type will do)
	 */
	public void removeListener(Object in) {
		for (int i = 0; i < myWeakReferences.size(); i++) {
			if (in == myWeakReferences.get(i).get()) {
				myWeakReferences.remove(i);
				System.out.println("out!");
				break;
			}
		}
	}

	/**
	 * Uses nifty methodObject and instance caches to reflexively invoke methods
	 * TODO: get working with the methodInputs TODO: real exceptions ...
	 * 
	 * @param key
	 * @param in
	 */
	public void execute(String key, ActionObject in) {
		for (WeakReference x : myWeakReferences) {
			try {
				keyToMethod.get(key).invoke(x.get(), null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {

			}
		}
	}

	/**
	 * actionNotification aka massiveHack. TODO: make less shitty.
	 * 
	 * @param action
	 * @param object
	 * 
	 */
	public void actionNotification(String action, ActionObject object) {
		try {
			execute(RESOURCES.getString(action), object);
		} catch (NullPointerException e) {
			System.out.println("null pointer oops");
		}
	}
}
