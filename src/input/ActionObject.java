package input;

/**
 * A superclass for all objects sent to game behaviors. Root of parameter object hierarchy.
 * @author Gavin
 *
 */
public class ActionObject {
	private long myTime;
	
	public ActionObject(long time){
		myTime = time;
	}
	
	public long getTime(){
		return myTime;
	}
}


