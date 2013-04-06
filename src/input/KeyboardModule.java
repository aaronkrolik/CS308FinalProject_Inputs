package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

/**
 * KeyboardModule gets input info from keyboard and send appropriate input info to the Input object
 * @author Ying Chen
 *
 */
public class KeyboardModule extends InputDevice{
	JComponent myComponent;
	public final String myDevice = "KEYBOARD";
	

	public KeyboardModule(JComponent component, Input input) {
		super("Keyboard",input);
		myComponent = component;
		initialize();
		this.addObserver(new TwoCompoundInputObserver());
		this.addObserver(new ContinuousInputObserver());
	}

	private void initialize() {
		myComponent.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				setSingleInputTag(true);
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				String inputSource = "Keyboard_" + keyName + "_KeyDown";
				ActionObject currentAction = new AlertObject(e.getWhen(),
						inputSource);
				getManager().addNewAction(currentAction);
				notifyChange();
				if(isSingleInput())
					notifyInputAction(inputSource, currentAction);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				String inputSource = "Keyboard_" + keyName + "_KeyUp";
				ActionObject currentAction = new AlertObject(e.getWhen(),
						inputSource);
				getManager().releaseInputs(currentAction);
				notifyInputAction(inputSource, currentAction);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

}
