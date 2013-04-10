package input;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The inputDevice performs as the super class for different device modules
 * 
 * @author Ying Chen, Gavin Ovsak, aaronkrolik
 * 
 */

public abstract class InputDevice {
	private String myName;
	private Input myInput;
	private static ResourceBundle SETTINGS;

	public InputDevice(String name, Input input) {
		myName = name;
		myInput = input;
	}

	public String getName() {
		return myName;
	}
	
	protected void setSettings(ResourceBundle in){
		SETTINGS = in;
	}
	
	protected static String getSetting(String in){
		try{
			return SETTINGS.getString(in);
		} catch (MissingResourceException e) {
			return "";
		}
	}

	/**
	 * Send the input information to the input object
	 * 
	 * @param keyInfo
	 * @param object
	 */
	protected void notifyInputAction(String info, ActionObject object) {
		myInput.actionNotification(info, object);
	}
	
}
