package input;

import java.util.List;
import java.util.Observable;

/**
 * This is an observer that observes continuous inputs from the same source
 * @author Ying Chen
 *
 */
public class ContinuousInputObserver extends InputObserver{
	private final int continuousKeytimeLimit = 100;
	
	@Override
	public void update(Observable arg0, Object arg1) {
		KeyboardModule device = (KeyboardModule) arg0;
		List<ActionObject> activeActions = device.getActiveObjects();
		boolean detectContinuousInputs = false;
		ActionObject newAction = activeActions
				.get(activeActions.size() - 1);
		if (activeActions.size() >= 2) {
			ActionObject previousAction = activeActions.get(activeActions
					.size() - 2);
			if (newAction.getInputSource().equals(
					previousAction.getInputSource())
					&& newAction.getTime() - previousAction.getTime() <= continuousKeytimeLimit)
				detectContinuousInputs = true;
		}
		if(detectContinuousInputs){
			device.notifyInputAction(newAction.getInputSource()+"_LongPressed", newAction);
			device.hasComplexInputs();
		}
	}

}
