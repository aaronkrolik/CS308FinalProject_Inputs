package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * KeyboardInput gets input info from keyboard and send appropriate input info to the Input object
 * @author Ying Chen, Gavin Ovsak, Aaron Krolik
 */
@SuppressWarnings("unchecked")
public class KeyboardInput extends InputDevice{

    public static final String DEVICE = "Keyboard";
    private ArrayList<ButtonState> myDownKeys;
    private Input myInput;
    private long lastClickTime = 0;
    private InputDevice inDev = this;
    private String lastClickKey = "";
    
    /**
     * Constructs the Keyboard Input object with a component to set up listeners
     * to and an Input object to send actions to.
     * @param component
     * @param input
     */
    public KeyboardInput(JComponent component, Input input) {
        super(DEVICE,input);
        myDownKeys = new ArrayList<ButtonState>();
        initialize(component);
        myInput = input;
    }

    /**
     * A recursive method which goes through all possible combinations of keys pressed down and notified their
     * actions. This allows the user to arbitrarily order the combos they desire.
     * @param accumulatedKeys
     * @param keyArray
     * @param maxSize
     * @param time
     */
    private void recursivePermutation(String accumulatedKeys, ArrayList<ButtonState> keyArray, int maxSize, long time) {
        if(maxSize == 0) {
            notifyInputAction(DEVICE + "_" + accumulatedKeys + "_Down", new AlertObject(time));
        } else {
            for(ButtonState key : keyArray) {
                ArrayList<ButtonState> modArray = (ArrayList<ButtonState>) keyArray.clone();
                modArray.remove(key);
                recursivePermutation(key.toString() + accumulatedKeys, modArray, maxSize - 1, time);
            }
        }
    }
    
    /**
     * A recursive combination method which goes through subsets of the roughly
     * simultaneous key presses in case a subset was being listened for.
     * @param keyArray
     * @param time
     * @param commonKey
     */
    private void recursiveCombination(ArrayList<ButtonState> keyArray, long time, ButtonState commonKey) {
        //for each thing, take it out and add keyName and permute it
        //for each of those things, take each one out and permute it
        //smaller sized permuations that all include the latest button
        if(keyArray.size() > 1) {
            for(int i = 0; i < keyArray.size(); i++) {
                ArrayList<ButtonState> modArray = (ArrayList<ButtonState>) keyArray.clone();
                modArray.remove(i);
                recursiveCombination(modArray, time, commonKey);
                modArray.add(commonKey);
                recursivePermutation("", modArray, modArray.size(), time);
            }
        }
    }
    
    /**
     * Creates the standard keyListener method overrides which process
     * the KeyEvents that are passed in from the component.
     * @param component
     */
    private void initialize(JComponent component) {
        component.addKeyListener(new KeyListener() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                notifyInputAction("Keyboard_Any_Down", new AlertObject(e.getWhen()));
                String keyName = KeyboardMappings.getKeyName(e.getKeyCode());
                ButtonState downKey = new ButtonState(DEVICE, keyName, e.getWhen(), inDev);
                notifyInputAction(downKey.getFullName() + "_KeyDown", new AlertObject(e.getWhen())); //Legacy Support
                if (!myDownKeys.contains(downKey)){
                    if(myDownKeys.size() > 1) {
                        recursiveCombination(myDownKeys, e.getWhen(), downKey);
                    }
                    myDownKeys.add(downKey);
                }
                else
                    notifyInputAction(downKey.getFullName() + "_LongPress",new AlertObject(e.getWhen()));
                
                ArrayList<ButtonState> buttonArray = (ArrayList<ButtonState>) myDownKeys.clone();
                if(buttonArray.size() > 1)
                    recursivePermutation("", buttonArray, buttonArray.size(), e.getWhen());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                notifyInputAction("Keyboard_Any_Up", new AlertObject(e.getWhen()));
                String keyName = KeyboardMappings.getKeyName(e.getKeyCode());
                ButtonState temp = new ButtonState(DEVICE, keyName, e.getWhen(), inDev);
                int keyIndex;
                if( (keyIndex = myDownKeys.indexOf(temp)) != -1 ) {
                    long timeDifference = temp.getTime() - myDownKeys.remove(keyIndex).getTime();
                    notifyInputAction(temp.getFullName() + "_Up", new AlertObject(e.getWhen()));
                    notifyInputAction(temp.getFullName() + "_KeyUp", new AlertObject(e.getWhen())); //Legacy Support
                    if (timeDifference < Integer.parseInt(myInput.getSetting("ShortClickTimeThreshold")) ) {
                        notifyInputAction(temp.getFullName() + "_ShortClick",
                                new AlertObject(e.getWhen()));
                    }
                    if (timeDifference >= Integer.parseInt(myInput.getSetting("LongClickTimeThreshold"))) {
                        notifyInputAction(temp.getFullName() + "_LongClick",
                                new AlertObject(e.getWhen()));
                    }
                    if (lastClickKey.equals(keyName) && e.getWhen() - lastClickTime < Integer.parseInt(myInput.getSetting("DoubleClickTimeThreshold"))) {
                        notifyInputAction(temp.getFullName() + "_DoubleClick", new AlertObject(e.getWhen()));
                    }
                    lastClickTime = e.getWhen();
                    lastClickKey = keyName;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
    }
}
