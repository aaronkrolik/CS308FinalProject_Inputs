package input;

import java.util.List;
import java.util.Observable;

public class InputDevice extends Observable{
	private String myName;
	
	public InputDevice(String name) {
		myName = name;
	}
	
	public String getName() {
		return myName;
	}
	
	public void notifyChange(){
		setChanged();
        notifyObservers();
	}
	
	public List<ActionObject> getActiveObjects(){
		return null;
	}
	
	public void notifyInputAction(String keyInfo, ActionObject object){
	} 
	
}
