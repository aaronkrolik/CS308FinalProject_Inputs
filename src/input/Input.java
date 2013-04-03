package input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JComponent;

public class Input {
	private final ResourceBundle RESOURCES;
	
	HashMap<String, Command> behaviors = new HashMap<String, Command>();
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
