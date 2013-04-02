package input;

import java.util.HashMap;

import javax.swing.JComponent;

public class InputDevice {
	JComponent myFrame;
	private String myName;
	
	HashMap<Integer, Command> keyPressedCommands = new HashMap<Integer, Command>();
	HashMap<Integer, Command> keyReleasedCommands = new HashMap<Integer, Command>();
	
	public InputDevice(String name) {
		myName = name;
	}
	
	public void setCommand(String[] actionIdentifiers, Command command) {
	}
	
	public String getName() {
		return myName;
	}
}
