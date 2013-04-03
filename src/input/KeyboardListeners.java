package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

public class KeyboardListeners{
	
	JComponent myFrame;
	private String myDevice = "KEYBOARD";
	
	public KeyboardListeners(JComponent component){
		myFrame = component;
		initialize();
	}

	
	public ActionObject send(KeyEvent e) {
		ActionObject ret = new ActionObject();
		ret.myCallerDevice = myDevice;
		ret.myData = e.paramString();
		return null;
	}
	
	private void initialize(){
		myFrame.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				send(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				send(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {				
			}
			
		});
		
	}
	
}
