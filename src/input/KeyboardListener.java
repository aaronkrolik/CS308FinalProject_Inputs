package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JComponent;

public class KeyboardListener extends InputDevice{
	JComponent myComponent;
	private Input myInput;
	//private KeyboardInitialler initialler;
	public final String myDevice = "KEYBOARD";
	
	public KeyboardListener(JComponent component, Input input){
		super("Keyboard");
		myComponent = component;
		myInput = input;
		//initialler = new KeyboardInitialler();
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
				String action = getKeyPressed(e.getKeyCode());
				myInput.actionNotify(action, getObject(e));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String action = getKeyReleased(e.getKeyCode());
				myInput.actionNotify(action, getObject(e));
			}

			@Override
			public void keyTyped(KeyEvent e) {				
			}
		});
	}
	
	private String getKeyPressed(int key){
		String thirdIdentifier = "" + ((char)key);
		if(key == KeyEvent.VK_SPACE)
			thirdIdentifier = "Spacebar";
		return "Keyboard_" + thirdIdentifier + "_KeyDown";
	}
	
	private String getKeyReleased(int key){
		String thirdIdentifier = "" + (char)key;
		if(key == KeyEvent.VK_SPACE)
			thirdIdentifier = "Spacebar";
		return "Keyboard_" + thirdIdentifier + "_KeyUp";
	}
}
