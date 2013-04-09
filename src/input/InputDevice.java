package input;

/**
 * The inputDevice performs as the super class for different device modules
 * 
 * @author Ying Chen, Gavin Ovsak
 * 
 */

public class InputDevice {
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
	public void notifyInputAction(String keyInfo, ActionObject object) {
		myInput.actionNotification(keyInfo, object);
	}
}
