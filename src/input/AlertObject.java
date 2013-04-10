package input;

/**
 * A superclass for all objects sent to game behaviors. Root of parameter object hierarchy.
 * @author Gavin
 *
 */
public class AlertObject {
	private long myTime;
	
	public AlertObject(long time){
		myTime = time;
	}
	
	public long getTime(){
		return myTime;
	}
}


