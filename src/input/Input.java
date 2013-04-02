package input;

import java.util.ResourceBundle;

public class Input {
	private final ResourceBundle RESOURCES;
	
	public Input(String resourcePath) {
		RESOURCES = ResourceBundle.getBundle(resourcePath);
	}
	
	
}
