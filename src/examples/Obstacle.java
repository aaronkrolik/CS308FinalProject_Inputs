package examples;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * An Obstacle box which gets in the way of racers in the Keeping Up With The Joneses minigame.
 * @author Gavin
 */
public class Obstacle extends Sprite {
	private boolean active = true;
	private double position = 0;
	
	/**
	 * Constructs an obstacle
	 * @param Position
	 */
	public Obstacle(double Position){
		super(new Pixmap("null.gif"), new Location(150 + Position,440), new Dimension(40,40), new Vector(0,0));
		position = Position;
	}
	
	/**
	 * Paints the obstacle box
	 * @param pen
	 * @param you
	 */
	public void paint(Graphics2D pen, Player you) {
		if(active) {
			setCenter(150 + position - you.getPosition(), 440);
			pen.fillRect((int)getLeft(), (int)getTop(), (int)getWidth(), (int)getHeight());
		}
    }
	
	/**
	 * Overrides intersects so that a non active obstacle can't be intersected
	 */
	@Override
	public boolean intersects (Sprite other) {
		if(active && super.intersects(other)) {
			active = false;
			return true;
		}
		return false;
    }
}