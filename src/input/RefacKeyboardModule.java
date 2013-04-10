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
 * @author Ying Chen, Gavin Ovsak
 *
 */
@SuppressWarnings("unchecked")
public class RefacKeyboardModule extends InputDevice{
	JComponent myComponent;
	public static final String myDevice = "KEYBOARD";
	ArrayList<KeyState> myPressedKeys;
	List<KeyState> myKeys;
	
	private class KeyState implements Comparable{
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
		public int hashCode(){
			return myName.hashCode();
		}
		
		public int compareTo(Object in) {
			if (!equals(in)){
				return (int) (((KeyState) in).myTime - myTime);
			}
			return 0;
		}

	
		
	}
	
	

	public RefacKeyboardModule(JComponent component, Input input) {
		super(myDevice,input);
		myComponent = component;
		myPressedKeys = new ArrayList<KeyState>();
		myKeys = new ArrayList<KeyState>();
		initialize();
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
	
	private void initialize() {
		
		myComponent.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				KeyState downKey = new KeyState( KeyboardHelper.getKeyName(e.getKeyCode()), e.getWhen());
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				if (!myKeys.contains(downKey)){
					myKeys.add(downKey);
				}	
				notifyInputAction("Keyboard_" + keyName + "_KeyDown", new AlertObject(e.getWhen()));
				recursivePermutation("", myKeys.toString().replace(", ", "").replace("[", "").replace("]", ""), e.getWhen() );
			}

			@Override
			public void keyReleased(KeyEvent e) {
				KeyState temp = new KeyState( KeyboardHelper.getKeyName(e.getKeyCode()), e.getWhen());
				long timeDifference = temp.myTime - myKeys.remove(myKeys.indexOf(temp)).myTime;
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
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
}
