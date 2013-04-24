package input;

/**
 * The inputDevice performs as the super class for different device modules
 * 
 * @author Ying Chen, Gavin Ovsak, Aaron Krolik
 * 
 */

public abstract class InputDevice {
    private String myName;
    private Input myInput;
    
    /**
     * Initializes the device with a device name and a
     * reference to the input class (used for calling on
     * when input events occur)
     * @param name
     * @param input
     */
    public InputDevice(String name, Input input) {
        myName = name;
        myInput = input;
    }

    /**
     * Returns the device name.
     * @return
     */
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
