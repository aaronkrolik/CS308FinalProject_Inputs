package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;

public class KeyboardListener extends InputDevice{
	JComponent myComponent;
	private Input myInput;
	public final String myDevice = "KEYBOARD";
	
	public KeyboardListener(JComponent component, Input input){
		super("Keyboard");
		myComponent = component;
		myInput = input;
		initialize();
	}
	
	private ActionObject getObject(KeyEvent e){
		ActionObject actObj = new ActionObject();
		actObj.myCallerDevice = myDevice;
		actObj.myData = e.paramString();
		return actObj;
	}
	
	private void initialize(){
		myComponent.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e){
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				myInput.actionNotify("Keyboard_" + keyName + "_KeyDown", getObject(e));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				myInput.actionNotify("Keyboard_" + keyName + "_KeyUp", getObject(e));
			}

			@Override
			public void keyTyped(KeyEvent e) {				
			}
		});
	}
}
