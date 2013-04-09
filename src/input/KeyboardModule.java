package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

/**
 * KeyboardModule gets input info from keyboard and send appropriate input info to the Input object
 * @author Ying Chen, Gavin Ovsak
 *
 */
@SuppressWarnings("unchecked")
public class KeyboardModule extends InputDevice{
	JComponent myComponent;
	public final String myDevice = "KEYBOARD";

	private class KeyState {
		private String myName;
		private long myTime;
		public KeyState(String name, long time) {
			myName = name;
			myTime = time;
		}

		public long getTime() {
			return myTime;
		}

		public String getName() {
			return myName;
		}
	}

	ArrayList<KeyState> pressedKeys = new ArrayList<KeyState>();

	public KeyboardModule(JComponent component, Input input) {
		super("Keyboard",input);
		myComponent = component;
		initialize();
	}

	private void recurse(String accumulatedKeys, ArrayList<KeyState> keyArray, int maxSize, long time) {
		if(maxSize == 0) {
			notifyInputAction("Keyboard_" + accumulatedKeys + "_KeyDown", new AlertObject(time));
		} else {
			for(KeyState key : keyArray) {
				ArrayList<KeyState> modArray = (ArrayList<KeyState>) keyArray.clone();
				modArray.remove(key);
				recurse(key.getName() + accumulatedKeys, modArray, maxSize - 1, time);
			}
		}
	}

	private void initialize() {
		myComponent.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				final String keyName = KeyboardHelper.getKeyName(e.getKeyCode());

				boolean alreadyExists = false;
				for(KeyState key : pressedKeys) {
					if(key.getName().equals(keyName)) {
						alreadyExists = true;
					}
				}
				if(!alreadyExists)
				{
					pressedKeys.add(new KeyState(keyName, e.getWhen()));

					ArrayList<KeyState> buttonArray = (ArrayList<KeyState>) pressedKeys.clone();

					if(buttonArray.size() != 1)
						notifyInputAction("Keyboard_" + keyName + "_KeyDown", new AlertObject(e.getWhen()));

					recurse("",buttonArray,buttonArray.size(), e.getWhen());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				notifyInputAction("Keyboard_" + keyName + "_KeyUp", new AlertObject(e.getWhen()));

				Set<KeyState> keysToRemove = new HashSet<KeyState>();
				for(KeyState key : pressedKeys) {
					if(key.getName().equals(keyName)) {
						long timeDifference = e.getWhen() - key.getTime();
						if(timeDifference < 100) {
							notifyInputAction("Keyboard_" + keyName + "_ShortClick", new AlertObject(e.getWhen()));
						}
						if(timeDifference >= 400) {
							notifyInputAction("Keyboard_" + keyName + "_LongClick", new AlertObject(e.getWhen()));
						}
						keysToRemove.add(key);
					}
				}
				for(KeyState key : keysToRemove) {
					pressedKeys.remove(key);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}
}