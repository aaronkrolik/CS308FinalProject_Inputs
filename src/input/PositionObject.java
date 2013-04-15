package input;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Position Object holds 
 * @author Gavin Ovsak
 */
public class PositionObject extends AlertObject {
	private double myX;
	private double myY;
	private double myZ;
	private double myXMax;
	private double myYMax;
	private double myZMax;
	
	public PositionObject(double x, double xMax, double y, double yMax, long time) {
		this(x,xMax,y,yMax,0,1,time);
	}
	
	public PositionObject(double x, double xMax, double y, double yMax, double z, double zMax, long time) {
		super(time);
		myX = x;
		myY = y;
		myZ = z;
		myXMax = xMax;
		myYMax = yMax;
		myZMax = zMax;
	}
	
	public double getX() {
		return myX;
	}
	
	public double getRelativeX() {
		return myX/myXMax;
	}

	public double getY() {
		return myY;
	}

	public double getRelativeY() {
		return myY/myYMax;
	}
	
	public double getZ() {
		return myZ;
	}

	public double getRelativeZ() {
		return myZ/myZMax;
	}

	public Point2D getPoint2D() {
		return new Point2D.Double(myX, myY);
	}
}

