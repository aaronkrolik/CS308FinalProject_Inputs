package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class KeyboardModule extends InputDevice{
	JComponent myComponent;
	private Input myInput;
	private List<ActionObject> activeActions = new ArrayList<ActionObject>();
	public final String myDevice = "KEYBOARD";
	private boolean isSingleInput = true;

	public KeyboardModule(JComponent component, Input input) {
		super("Keyboard");
		myInput = input;
		myComponent = component;
		initialize();
		this.addObserver(new TwoCompoundInputObserver());
		this.addObserver(new ContinuousInputObserver());
	}

	private void initialize() {
		myComponent.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				isSingleInput = true;
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				System.out.println("Keyboard_" + keyName + "_KeyDown" + " "
						+ e.getWhen());
				String inputSource = "Keyboard_" + keyName + "_KeyDown";
				ActionObject currentAction = new AlertObject(e.getWhen(),
						inputSource);
				activeActions.add(currentAction);
				notifyChange();
				if(isSingleInput)
					myInput.actionNotification(inputSource, currentAction);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String keyName = KeyboardHelper.getKeyName(e.getKeyCode());
				System.out.println("Keyboard_" + keyName + "_KeyUp" + " "
						+ e.getWhen());
				String inputSource = "Keyboard_" + keyName + "_KeyUp";
				ActionObject currentAction = new AlertObject(e.getWhen(),
						inputSource);
				releaseInputs(currentAction);
				myInput.actionNotification(inputSource, currentAction);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	private void releaseInputs(ActionObject newAction) {
		for (int i = activeActions.size() - 1; i >= 0; i--) {
			if (newAction.isMatch(activeActions.get(i))) {
				activeActions.remove(i);
			}
		}
	}
	
	@Override
	public List<ActionObject> getActiveObjects(){
		return activeActions;
	}
	
	@Override
	public void notifyInputAction(String keyInfo, ActionObject object){
		myInput.actionNotification(keyInfo, object);
	}
	
	public void hasComplexInputs(){
		isSingleInput = false;
	}
}
