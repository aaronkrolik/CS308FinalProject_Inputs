package input;

/**
 * A superclass for all objects sent to game behaviors. Root of parameter object hierarchy.
 * @author Gavin
 *
 */
public class ActionObject {
	private String myInputSource;
	private long myTime;
	
	public ActionObject(long time, String source){
		myInputSource = source;
		myTime = time;
	}
	
	public long getTime(){
		return myTime;
	}
	
	public String getInputSource(){
		return myInputSource;
	}
	
	public boolean isMatch(ActionObject o){
		return false;
	}
}


