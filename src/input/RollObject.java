package input;

/**
 * An object that includes the information of rolling actions of mouse wheel
 * 
 * @author Ying Chen 
 */
public class RollObject extends AlertObject {
    private int unitsRotated;

    /**
     * Constructor takes in time of roll event and units of roll.
     * @param time
     * @param units
     */
    public RollObject(long time, int units) {
        super(time);
        unitsRotated = units;
    }

    /**
     * Returns the units that the wheel has moved.
     * @return
     */
    public int getUnitsRotated() {
        return unitsRotated;
    }

}
