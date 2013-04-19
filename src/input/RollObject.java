package input;

/**
 * An object that includes the information of rolling actions of mouse wheel
 * 
 * @author Ying Chen 
 */
public class RollObject extends AlertObject {
	private int unitsRotated;

	public RollObject(long time, int units) {
		super(time);
		unitsRotated = units;
	}

	public int getUnitsRotated() {
		return unitsRotated;
	}

}
