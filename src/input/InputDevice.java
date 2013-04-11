package input;

/**
 * The inputDevice performs as the super class for different device modules
 * 
 * @author Ying Chen, Gavin Ovsak, aaronkrolik
 * 
 */

public abstract class InputDevice {
	private String myName;
	private Input myInput;
	
	public InputDevice(String name, Input input) {
		myName = name;
		myInput = input;
	}

	public String getName() {
		return myName;
	}
	
	/**
	 * Send the input information to the input object
	 * 
	 * @param keyInfo
	 * @param object
	 */
	protected void notifyInputAction(String info, AlertObject object) {
		myInput.actionNotification(info, object);
	}
	
}
