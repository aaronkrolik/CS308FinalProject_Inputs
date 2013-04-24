package input;

import java.awt.Point;

/**
 * This object contains the state of a button and is used primarily as an ArrayList
 * item type for monitoring the state of a variety of keys as they do short clicks,
 * long clicks, and/or double clicks.
 * @author Gavin Ovsak
 */
class ButtonState {
    private final String DEVICE;
    private final String NAME;
    private final long TIME;
    private Point myPosition = new Point(0, 0);
    
    /**
     * Constructs a button state object using the device name, button name,
     * time of down press, and the input device specifically which called it.
     * @param device
     * @param name
     * @param time
     * @param inDev
     */
    public ButtonState(String device, String name, long time, InputDevice inDev)  {
        NAME = name;
        TIME = time;
        DEVICE = device;
        inDev.notifyInputAction(getFullName() + "_Down", new AlertObject(time));
    }
    
    /**
     * An overflow constructor which allow the additional specification of a position
     * at which the button what pressed down. Used in the MouseInput module.
     * @param device
     * @param name
     * @param time
     * @param inDev
     * @param downPoint
     */
    public ButtonState(String device, String name, long time, InputDevice inDev, Point downPoint) {
        this(device, name, time, inDev);
        myPosition = downPoint;
    }
    
    /**
     * The button name is the main identifier used in comparisons.
     */
    public String toString(){
        return NAME;
    }
    
    /**
     * Returns the device name and the button name together with an underscore between them.
     * @return
     */
    public String getFullName() {
        return DEVICE + "_" + NAME;
    }
    
    /**
     * Returns the time of the button down event.
     * @return
     */
    public long getTime() {
        return TIME;
    }

    /**
     * Returns the position of button down.
     * @return
     */
    public Point getPosition() {
        return myPosition;
    }
    
    /**
     * Button name is used as the sole comparator.
     */
    public boolean equals(Object in){
        if (in instanceof ButtonState){
            return (NAME.equals(((ButtonState)in).toString()));
        }
        return false;
    }
}

