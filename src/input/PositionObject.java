package input;

import java.awt.geom.Point2D;

/**
 * Position Object holds the coordinate information of an event as might be needed by a game behavior.
 * @author Gavin Ovsak
 */
public class PositionObject extends AlertObject {
    private double myX;
    private double myY;
    private double myZ;
    private double myXMax;
    private double myYMax;
    private double myZMax;
    
    /**
     * Overflow constructor for only two dimensional positions. Z axis defaults to 0 out of 1.
     * @param x
     * @param xMax
     * @param y
     * @param yMax
     * @param time
     */
    public PositionObject(double x, double xMax, double y, double yMax, long time) {
        this(x,xMax,y,yMax,0,1,time);
    }
    
    /**
     * Constructs a Position Object form absolute position and scale in three dimensions along with time.
     * @param x
     * @param xMax
     * @param y
     * @param yMax
     * @param z
     * @param zMax
     * @param time
     */
    public PositionObject(double x, double xMax, double y, double yMax, double z, double zMax, long time) {
        super(time);
        myX = x;
        myY = y;
        myZ = z;
        myXMax = xMax;
        myYMax = yMax;
        myZMax = zMax;
    }
    
    /**
     * Returns the absolute X position information. Deprecated for not being cross device compatible. See getRelativeX()
     * @return
     */
    @Deprecated
    public double getX() {
        return myX;
    }
    
    /**
     * Returns the relative X position information. Ranges from 0 to 1.
     * @return
     */
    public double getRelativeX() {
        return myX/myXMax;
    }

    /**
     * Returns the absolute Y position information. Deprecated for not being cross device compatible. See getRelativeY()
     * @return
     */
    @Deprecated
    public double getY() {
        return myY;
    }

    /**
     * Returns the relative Y position information. Ranges from 0 to 1.
     * @return
     */
    public double getRelativeY() {
        return myY/myYMax;
    }
    
    /**
     * Returns the absolute Z position information. Deprecated for not being cross device compatible. See getRelativeZ()
     * @return
     */
    @Deprecated
    public double getZ() {
        return myZ;
    }

    /**
     * Returns the relative Z position information. Ranges from 0 to 1.
     * @return
     */
    public double getRelativeZ() {
        return myZ/myZMax;
    }

    /**
     * Returns the absolute X and Y data as a Point2D object.
     */
    public Point2D getPoint2D() {
        return new Point2D.Double(myX, myY);
    }
}

