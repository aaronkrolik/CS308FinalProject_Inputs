package input;

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
		InputDevice device = (InputDevice) arg0;
		ActionObjectManager manager = device.getManager();
		boolean detectContinuousInputs = false;
		ActionObject newAction = manager.getLatestAction();
		if (manager.getNumberOfActiveActions() >= 2) {
			ActionObject previousAction = manager.getAction(manager.getNumberOfActiveActions()-2);
			if (newAction.getInputSource().equals(
					previousAction.getInputSource())
					&& newAction.getTime() - previousAction.getTime() <= continuousKeytimeLimit)
				detectContinuousInputs = true;
		}
		if(detectContinuousInputs)
			device.notifyInputAction(newAction.getInputSource()+"_LongPressed", newAction);
		else
			device.notifyInputAction(newAction.getInputSource(), newAction);
		
	}

}
