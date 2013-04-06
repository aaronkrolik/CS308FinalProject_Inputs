package input;

import java.util.List;
import java.util.Observable;

/**
 * This is an observer that observes the two compound inputs actions
 * @author Ying Chen
 *
 */
public class TwoCompoundInputObserver extends InputObserver{
	private final int counpoudKeytimeLimit = 10;

	@Override
	public void update(Observable arg0, Object arg1) {
		KeyboardModule device = (KeyboardModule) arg0;
		boolean detectCompoundKeys = false;
		List<ActionObject> activeActions = device.getActiveObjects();
		ActionObject newAction = activeActions.get(activeActions.size() - 1);
		long time = newAction.getTime();
		String compundKeyInfo = newAction.getInputSource();
		for (int i = activeActions.size() - 2; i >= 0; i--) {
			if (time - activeActions.get(i).getTime() <= counpoudKeytimeLimit) {
				compundKeyInfo = compundKeyInfo + "&"
						+ activeActions.get(i).getInputSource();
				detectCompoundKeys = true;
			}
		}
		if(detectCompoundKeys){
			device.notifyInputAction(compundKeyInfo, newAction);
			device.hasComplexInputs();
		}
	}

}
