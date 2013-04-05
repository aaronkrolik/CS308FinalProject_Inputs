package input;

public class AlertObject extends ActionObject {
	private long myAlertTime;
	
	public AlertObject(long alertTime) {
		myAlertTime = alertTime;
	}
	
	public long getTime() {
		return myAlertTime;
	}
}


