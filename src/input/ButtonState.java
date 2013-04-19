package input;

import java.awt.Point;

class ButtonState{
	private final String myDevice;
	private final String myName;
	private final long myTime;
	private Point myPosition = new Point(0,0);
	
	public ButtonState(String device, String name, long time, InputDevice inDev)  {
		myName = name;
		myTime = time;
		myDevice = device;
		inDev.notifyInputAction(getFullName() + "_Down", new AlertObject(time));
	}
	
	public ButtonState(String device, String name, long time, InputDevice inDev, Point downPoint) {
		this(device, name, time, inDev);
		myPosition = downPoint;
	}
	
	public String toString(){
		return myName;
	}
	
	public String getFullName() {
		return myDevice + "_" + myName;
	}
	
	public long getTime() {
		return myTime;
	}

	public Point getPosition() {
		return myPosition;
	}

	public boolean equals(Object in){
		if(in instanceof ButtonState){
			return ( myName.equals(((ButtonState) in).myName)  );
		}
		return false;
	}
}

