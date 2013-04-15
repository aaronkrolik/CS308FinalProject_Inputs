package input;

/**
 * An object that includes the information of rolling actions of mouse wheel
 * 
 * @author Ying Chen 
 */
public class RollObject extends AlertObject {
	private int roundsRotated;
	private int unitsRotated;

	public RollObject(long time, int rounds, int units) {
		super(time);
		roundsRotated = rounds;
		unitsRotated = units;
	}

	public int getRoundsRotated() {
		return roundsRotated;
	}

	public int getUnitsRotated() {
		return unitsRotated;
	}

}
