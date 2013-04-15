package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * KeyboardModule gets input info from keyboard and send appropriate input info to the Input object
 * @author Ying Chen, Gavin Ovsak, aaronkrolik
 */
@SuppressWarnings("unchecked")
public class KeyboardInput extends InputDevice{

	public static final String myDevice = "Keyboard";
	private ArrayList<ButtonState> myDownKeys;
	private Input myInput;
	private long lastClickTime = 0;
	private InputDevice inDev = this;
	private String lastClickKey = "";
	
	public KeyboardInput(JComponent component, Input input) {
		super(myDevice,input);
		myDownKeys = new ArrayList<ButtonState>();
		initialize(component);
		myInput = input;
	}

	private void recursivePermutation(String accumulatedKeys, ArrayList<ButtonState> keyArray, int maxSize, long time) {
		if(maxSize == 0) {
			notifyInputAction(myDevice + "_" + accumulatedKeys + "_Down", new AlertObject(time));
		} else {
			for(ButtonState key : keyArray) {
				ArrayList<ButtonState> modArray = (ArrayList<ButtonState>) keyArray.clone();
				modArray.remove(key);
				recursivePermutation(key.toString() + accumulatedKeys, modArray, maxSize - 1, time);
			}
		}
	}
	
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
	
	private void initialize(JComponent component) {
		component.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				notifyInputAction("Keyboard_Any_Down", new AlertObject(e.getWhen()));
				String keyName = KeyboardMappings.getKeyName(e.getKeyCode());
				ButtonState downKey = new ButtonState(myDevice, keyName, e.getWhen(), inDev);
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
				ButtonState temp = new ButtonState(myDevice, keyName, e.getWhen(), inDev);
				long timeDifference = temp.getTime() - myDownKeys.remove(myDownKeys.indexOf(temp)).getTime();
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

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}
}
