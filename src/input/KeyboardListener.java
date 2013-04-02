package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JComponent;

public class KeyboardListener extends InputDevice{
	JComponent myFrame;
	public final String myDevice = "KEYBOARD";
	
	HashMap<Integer, Command> keyPressedCommands = new HashMap<Integer, Command>();
	HashMap<Integer, Command> keyReleasedCommands = new HashMap<Integer, Command>();
	
	public KeyboardListener(JComponent component){
		super("Keyboard");
		myFrame = component;
		initialize();
	}
	
	public ActionObject send(KeyEvent e) {
		ActionObject ret = new ActionObject();
		ret.myCallerDevice = myDevice;
		ret.myData = e.paramString();
		return null;
	}
	
	@Override
	public void setCommand(String[] actionIdentifiers, Command command) {
		if(actionIdentifiers[2].equals("KeyDown")) {
			if(actionIdentifiers[1].length() == 1) {
				keyPressedCommands.put((int)(actionIdentifiers[1].charAt(0)), command);
			} else if(actionIdentifiers[1].equals("Spacebar")) {
				keyPressedCommands.put(KeyEvent.VK_SPACE, command);
			}
		} else if(actionIdentifiers[2].equals("KeyUp")) {
			if(actionIdentifiers[1].length() == 1) {
				keyReleasedCommands.put((int)(actionIdentifiers[1].charAt(0)), command);
			} else if(actionIdentifiers[1].equals("Spacebar")) {
				keyPressedCommands.put(KeyEvent.VK_SPACE, command);
			}
		}
	}
	
	private void initialize(){
		myFrame.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(keyPressedCommands.containsKey(e.getKeyCode())) {
					keyPressedCommands.get(e.getKeyCode()).execute(new ActionObject());
				}
				send(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(keyReleasedCommands.containsKey(e.getKeyCode())) {
					keyReleasedCommands.get(e.getKeyCode()).execute(new ActionObject());
				}
				send(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {				
			}
		});
	}
}
