package input;

class ButtonState{
	private final String myDevice;
	private final String myName;
	private final long myTime;
	
	public ButtonState(String device, String name, long time, InputDevice inDev)  {
		myName = name;
		myTime = time;
		myDevice = device;
		inDev.notifyInputAction(getFullName() + "_Down", new AlertObject(time));
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

	public boolean equals(Object in){
		if(in instanceof ButtonState){
			return ( myName.equals(((ButtonState) in).myName)  );
		}
		return false;
	}
}

