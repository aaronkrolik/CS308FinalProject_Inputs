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
import javax.swing.JFrame;

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
		//in.initListener(mapping);
		
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
	
	public Input(String resourcePath, JComponent component) {
		
		
		
		
		RESOURCES = ResourceBundle.getBundle(resourcePath);
		
		KeyboardListeners foo = new KeyboardListeners(component);
		//Hardcoded resource file for now
	
//		component.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed (KeyEvent e) {
//            	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
//            		if(behaviors.containsKey("jump")) {
//    					behaviors.get("jump").execute(null);
//    				}
//            	}
//            	if(e.getKeyCode() == KeyEvent.VK_G) {
//            		if(behaviors.containsKey("cheat")) {
//    					behaviors.get("cheat").execute(null);
//    				}
//            	}
//            	if(e.getKeyCode() == KeyEvent.VK_F) {
//            		if(behaviors.containsKey("anticheat")) {
//    					behaviors.get("anticheat").execute(null);
//    				}
//            	}
//            }
//            @Override
//            public void keyReleased (KeyEvent e) {
//            	if(e.getKeyCode() == KeyEvent.VK_G) {
//            		if(behaviors.containsKey("stopcheat")) {
//    					behaviors.get("stopcheat").execute(null);
//    				}
//            	}
//            	if(e.getKeyCode() == KeyEvent.VK_F) {
//            		if(behaviors.containsKey("stopanticheat")) {
//    					behaviors.get("stopanticheat").execute(null);
//    				}
//            	}
//            }
//        });

		component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
            }
        });
        
		component.addMouseListener(new MouseListener() {
        	@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if(behaviors.containsKey("continue")) {
					behaviors.get("continue").execute(null);
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
        });
	}
	
	public void setBehavior(String behaviorName, Command behavior) {
		behaviors.put(behaviorName, behavior);
	}
	
}
