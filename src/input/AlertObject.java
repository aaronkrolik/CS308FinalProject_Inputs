package input;

/**
 * A superclass for all objects sent to game behaviors. Root of parameter object hierarchy.
 * @author Gavin Ovsak, Ying Chen
 *
 */
public class AlertObject {
    private long myTime;
    
    /**
     * All objects returned as parameters to game methods
     * extend alert object and contain the time of the event.
     * @param time
     */
    public AlertObject(long time){
        myTime = time;
    }
    
    /**
     * Function returns the time of the event.
     * @return
     */
    public long getTime(){
        return myTime;
    }
}


