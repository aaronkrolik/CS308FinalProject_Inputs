package input;

import java.util.Observable;

/**
 * The inputDevice performs as the super class for different device modules
 * 
 * @author Ying Chen
 * 
 */

public class InputDevice extends Observable {
	private String myName;
	private Input myInput;
	private ActionObjectManager myManager;

	public InputDevice(String name, Input input) {
		myName = name;
		myInput = input;
		myManager = new ActionObjectManager();
	}

	public String getName() {
		return myName;
	}

	/**
	 * Notify the inputObserver once a new input is made
	 */
	public void notifyChange() {
		setChanged();
		notifyObservers();
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

	public ActionObjectManager getManager() {
		return myManager;
	}
}
