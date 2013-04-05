package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;

public class KeyboardModule extends InputDevice{
	JComponent myComponent;
	private Input myInput;
	public final String myDevice = "KEYBOARD";
	
	public KeyboardModule(JComponent component, Input input){
		super("Keyboard");
		myInput = input;
		myComponent = component;
		initialize();
	}
	
	private void initialize(){
		myComponent.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e){
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				myInput.actionNotification("Keyboard_" + keyName + "_KeyDown", new AlertObject(e.getWhen()));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				myInput.actionNotification("Keyboard_" + keyName + "_KeyUp", new AlertObject(e.getWhen()));
			}

			@Override
			public void keyTyped(KeyEvent e) {				
			}
		});
	}
}
