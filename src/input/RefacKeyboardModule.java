package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.JComponent;

/**
 * KeyboardModule gets input info from keyboard and send appropriate input info to the Input object
 * @author Ying Chen, Gavin Ovsak, aarokrolik
 *
 */
@SuppressWarnings("unchecked")
public class RefacKeyboardModule extends InputDevice{
	

	public static final String myDevice = "KEYBOARD";
	List<KeyState> myKeys;
	
	public RefacKeyboardModule(JComponent component, Input input) {
		super(myDevice,input);
		myKeys = new ArrayList<KeyState>();
		initialize(component);
	}
	/**
	 * taken from stack overflow
	 * @param prefix
	 * @param str
	 * @param time
	 */
	 private void recursivePermutation(String prefix, String str, long time) {
		    int n = str.length();
		    if (n == 0) notifyInputAction("Keyboard_" + prefix + "_KeyDown", new AlertObject(time));
		    else {
		        for (int i = 0; i < n; i++)
		           recursivePermutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), time);
		    }
		}
	
	private void initialize(JComponent component) {
		
		component.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				String keyName = KeyboardMappings.getKeyName(e.getKeyCode());
				KeyState downKey = new KeyState( keyName, e.getWhen());
				if (!myKeys.contains(downKey)){
					myKeys.add(downKey);
				}
				notifyInputAction("Keyboard_" + keyName + "_KeyDown", new AlertObject(e.getWhen()));
				recursivePermutation("", myKeys.toString().replace(", ", "").replace("[", "").replace("]", ""), e.getWhen() );
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String keyName = KeyboardMappings.getKeyName(e.getKeyCode());
				KeyState temp = new KeyState(keyName, e.getWhen());
				long timeDifference = temp.myTime - myKeys.remove(myKeys.indexOf(temp)).myTime;
				notifyInputAction("Keyboard_" + keyName + "_KeyUp",
							new AlertObject(e.getWhen()));
				if (timeDifference < 100) {
					notifyInputAction("Keyboard_" + keyName + "_ShortClick",
							new AlertObject(e.getWhen()));
				}
				if (timeDifference >= 400) {
					notifyInputAction("Keyboard_" + keyName + "_LongClick",
							new AlertObject(e.getWhen()));
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}
	
	private class KeyState{
		public final String myName;
		public final long myTime;
		
		public KeyState(String name, long time)  {
			myName = name;
			myTime = time;
		}
		
		public String toString(){
			return myName;
		}
	
		public boolean equals(Object in){
			if(in instanceof KeyState){
				return ( myName.equals(((KeyState) in).myName)  );
			}
			return false;
		}
	
	}
}
