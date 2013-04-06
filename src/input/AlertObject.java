package input;

public class AlertObject extends ActionObject {
	
	public AlertObject(long time, String source) {
		super(time, source);
	}

	@Override
	public boolean isMatch(ActionObject o){
		if(!(o instanceof AlertObject))
			return false;
		int pos = this.getInputSource().lastIndexOf("_");
		return this.getInputSource().substring(0, pos).equals(o.getInputSource().substring(0, pos));
	}
}


