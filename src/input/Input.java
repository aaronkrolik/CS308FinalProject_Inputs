package input;

import java.util.HashMap;
import java.util.ResourceBundle;

public class Input {
	private final ResourceBundle RESOURCES;
	
	HashMap<String, Command> behaviors = new HashMap<String, Command>();
	
	public Input(String resourcePath) {
		RESOURCES = ResourceBundle.getBundle(resourcePath);
	
		//set up mouse listeners
		//set up keyboard listeners
	}
	
	public void setBehavior(String behaviorName, Command behavior) {
		behaviors.put(behaviorName, behavior);
	}
	
}
