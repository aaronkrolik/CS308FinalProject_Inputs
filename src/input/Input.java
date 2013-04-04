package input;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JComponent;

import examples.Player;

public class Input {
	
	private static Input myInstance;
	
	private final ResourceBundle RESOURCES;
	
	HashMap<String, Command> behaviors = new HashMap<String, Command>();
	List<Object> myObjects = new ArrayList<Object>();
	Map<String, Method> keyToMethod = new HashMap<String, Method>();
	Map<String, Object> keyToInstance = new HashMap<String, Object>();
	ArrayList<InputDevice> inputDevices = new ArrayList<InputDevice>();


	
	public static Input newSingletonInput(String resourcePath, JComponent component){
		if (myInstance == null) {
			myInstance = new Input(resourcePath, component);
		}
		return myInstance;
	}
	
	public static Input getSingeltonInput(){
		return myInstance;
	}
	
	private Input(String resourcePath, JComponent component) {
		RESOURCES = ResourceBundle.getBundle(resourcePath);
		inputDevices.add(new KeyboardModule(component));
		inputDevices.add(new MouseModule(component));
	}
	
	
	public void addListenerTo(Object in){
		myObjects.add(in);
		Class inputClass = in.getClass();	
		if (inputClass.getAnnotation(InputClassTarget.class) != null){
			for (Method method : inputClass.getMethods()){
				Annotation annotation = method.getAnnotation(InputMethodTarget.class);
				if (annotation instanceof InputMethodTarget){
					System.out.println("reflecting"+((InputMethodTarget) annotation).name());
					keyToMethod.put(((InputMethodTarget) annotation).name(), method);
				}	
			}
		}			
	}
	
	public void removeListener(Object in){
		for (int i=0; i<myObjects.size(); i++){
			if (in == myObjects.get(i)){
				myObjects.remove(i);
				break;
			}
		}
	}
	
	public void execute(String key, ActionObject in){
		for (Object x : myObjects){
			try {
				keyToMethod.get(key).invoke(x, null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e){
				
			}
		}
	}
	
	
	public void setBehavior(String behaviorName, Command behavior) {
		behaviors.put(behaviorName, behavior);
	}
	
	public void actionNotification(String action, ActionObject object){
		try{
		execute(RESOURCES.getString(action), object);
		}catch (NullPointerException e){
			System.out.println("null pointer oops");
		}
		if(RESOURCES.containsKey(action) && behaviors.containsKey(RESOURCES.getString(action))) {
			//behaviors.get(RESOURCES.getString(action)).execute(object);
			
		}
	}
}
