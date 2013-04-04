package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;

public class KeyboardModule extends InputDevice{
	JComponent myComponent;
	private Input myInput;
	public final String myDevice = "KEYBOARD";
	
	public KeyboardModule(JComponent component){
		super("Keyboard");
		myComponent = component;
		initialize();
	}
	
	private ActionObject getObject(KeyEvent e){
		ActionObject actObj = new ActionObject();
		actObj.setData("My Caller Device", myDevice);
		actObj.setData("My Data", e.paramString());
		actObj.myName = "blah";
		return actObj;
	}
	
	private void initialize(){
		myComponent.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e){
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				Input.getSingeltonInput().actionNotification("Keyboard_" + keyName + "_KeyDown", getObject(e));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				Input.getSingeltonInput().actionNotification("Keyboard_" + keyName + "_KeyUp", getObject(e));
			}

			@Override
			public void keyTyped(KeyEvent e) {				
			}
		});
	}
}
