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
	private final ResourceBundle RESOURCES;
	
	HashMap<String, Command> behaviors = new HashMap<String, Command>();
	List<Object> objs = new ArrayList<Object>();
	Map<String, Method> mapping = new HashMap<String, Method>();
	Object foo;
	
	public void initBehavior(Player in){
		foo = in;
		Class targetClass = Player.class;		
		for (Method method : targetClass.getMethods()){
			Annotation annotation = method.getAnnotation(InputTarget.class);
			if (annotation instanceof InputTarget){
				System.out.println("reflecting"+((InputTarget) annotation).name());
				mapping.put(((InputTarget) annotation).name(), method);
			}
		}
		
		ActionObject x = new ActionObject();
		x.myName = "blah";
		try {
			mapping.get(x.myName).invoke(foo, x);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	ArrayList<InputDevice> inputDevices = new ArrayList<InputDevice>();
	
	public Input(String resourcePath, JComponent component) {
		
		
		
		
		RESOURCES = ResourceBundle.getBundle(resourcePath);
		inputDevices.add(new KeyboardModule(component,this));
		inputDevices.add(new MouseModule(component,this));
	}
	
	public void setBehavior(String behaviorName, Command behavior) {
		behaviors.put(behaviorName, behavior);
	}
	
	public void actionNotification(String action, ActionObject object){
		if(RESOURCES.containsKey(action) && behaviors.containsKey(RESOURCES.getString(action))) {
			behaviors.get(RESOURCES.getString(action)).execute(object);
		}
	}
}
