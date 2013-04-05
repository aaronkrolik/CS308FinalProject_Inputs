package input;

import java.util.HashMap;

public class ActionObject {
	private final HashMap<String, String> data = new HashMap<String, String>();
	public String myName;
	public String myCallerDevice;
	public String myData;
	public void setData(String name, String datum) {
		data.put(name, datum);
	}

	public String getData(String name) {
		if(data.containsKey(name))
			return data.get(name);
		return null;
	}
	
	public boolean hasData(String name) {
		return data.containsKey(name);
	}
}


