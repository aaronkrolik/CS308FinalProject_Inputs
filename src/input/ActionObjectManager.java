package input;

import java.util.ArrayList;
import java.util.List;

/**
 * ActionObjectManager stores and processes all the active actions
 * The active actions means inputs that have been pressed but not released
 * @author Ying Chen
 *
 */
public class ActionObjectManager {
	private List<ActionObject> activeActions = new ArrayList<ActionObject>();
	
	public void addNewAction(ActionObject object){
		activeActions.add(object);
	}
	
	public void releaseInputs(ActionObject newAction) {
		for (int i = activeActions.size() - 1; i >= 0; i--) {
			if (newAction.isMatch(activeActions.get(i))) {
				activeActions.remove(i);
			}
		}
	}
	
	public int getNumberOfActiveActions(){
		return activeActions.size();
	}
	
	public ActionObject getAction(int index){
		if(index>=0 && index < activeActions.size())
			return activeActions.get(index);
		else
			return null;
	}
	
	public ActionObject getLatestAction(){
		return activeActions.get(getNumberOfActiveActions()-1);
	}
}
