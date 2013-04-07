package input;

import java.util.Observable;

/**
 * This is an observer that observes the two compound inputs actions
 * @author Ying Chen
 *
 */
public class TwoCompoundInputObserver extends InputObserver{
	private final int compoundKeytimeLimit = 10;

	@Override
	public void update(Observable arg0, Object arg1) {
		InputDevice device = (InputDevice) arg0;
		boolean detectCompoundKeys = false;
		ActionObjectManager manager = device.getManager();
		ActionObject newAction = manager.getLatestAction();
		long time = newAction.getTime();
		String compundKeyInfo = newAction.getInputSource();
		for (int i = manager.getNumberOfActiveActions() - 2; i >= 0; i--) {
			if (time - manager.getAction(i).getTime() <= compoundKeytimeLimit) {
				compundKeyInfo = compundKeyInfo + "&"
						+ manager.getAction(i).getInputSource();
				detectCompoundKeys = true;
			}
		}
		if(detectCompoundKeys){
			device.notifyInputAction(compundKeyInfo, newAction);
			device.setSingleInputTag(false);
		}
	}

}
