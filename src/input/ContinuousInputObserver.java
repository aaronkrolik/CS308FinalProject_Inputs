package input;

import java.util.Observable;

/**
 * This is an observer that observes continuous inputs from the same source
 * 
 * @author Ying Chen
 * 
public class ContinuousInputObserver extends InputObserver {
	private final int continuousKeytimeLimit = 100;

	@Override
	public void update(Observable arg0, Object arg1) {
		InputDevice device = (InputDevice) arg0;
		ActionObjectManager manager = device.getManager();
		boolean detectContinuousInputs = false;
		ActionObject newAction = manager.getLatestAction();
		
		for (int i = manager.getNumberOfActiveActions() - 2; i >= 0; i--) {
			ActionObject curr = manager.getAction(i);
			if (curr.getInputSource().equals(newAction.getInputSource())
					&& newAction.getTime() - curr.getTime() <= continuousKeytimeLimit) {
				detectContinuousInputs = true;
				break;
			}
			else if(newAction.getTime() - curr.getTime() > continuousKeytimeLimit){
				detectContinuousInputs = false;
				break;
			}
		}

		if (detectContinuousInputs)
			device.notifyInputAction(newAction.getInputSource()
					+ "_LongPressed", newAction);
		else
			device.notifyInputAction(newAction.getInputSource(), newAction);
	}

}
*/
