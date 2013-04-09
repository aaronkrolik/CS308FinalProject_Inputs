package examples;

import java.awt.Dimension;

/**
 * Creates a Player object which contains the player's state. Is an extension of
 * Sprite so it can be drawn in a minigame.
 * 
 * @author Gavin Ovsak
 */
public class Player extends Sprite {
	private static final int GRAVITY = -280;
	private static final int JUMP_VELOCITY = 330;
	private final int MAX_SPEED = 30;
	private Pixmap runningImage;
	private Pixmap flyingImage;
	private boolean isCheating = false;
	private boolean isAntiCheating = false;

	private double minigamePosition = 0;
	private double minigameSpeed = 0;
	private double minigameTimeOfJump = -100;
	
	public Player(Pixmap firstImage, Pixmap secondImage) {
		super(firstImage, new Location(150, 400), new Dimension(60, 100));
		runningImage = firstImage;
		flyingImage = secondImage;
	}
	
	public void jump(double time) {
		minigameTimeOfJump = time;
		if (minigameSpeed + 6 <= MAX_SPEED) {
			minigameSpeed += 6;
		}
	}

	public void incrementPosition() {
		minigamePosition += minigameSpeed;

		if ((isCheating || getBottom() > 440) && minigameSpeed < MAX_SPEED) {
			minigameSpeed += 1;
		}
		if(isAntiCheating) {
			minigameSpeed = 0;
		}
	}
	
	
	public void slowDown() {
		if (minigameSpeed > 10) {
			minigameSpeed -= 10;
		}
	}

	public double getPosition() {
		return minigamePosition;
	}

	public void resetMinigameVariables() {
		minigamePosition = 0;
		minigameSpeed = 0;
		minigameTimeOfJump = -100;
	}
	
	public void setCheating(boolean isCheating) {
		this.isCheating = isCheating;
		
	}

	public void setAntiCheating(boolean isAntiCheating) {
		this.isAntiCheating = isAntiCheating;
		
	}

	public void update(double time, double cameraPosition) {
		double jumpTime = time - minigameTimeOfJump;
		if (isCheating) {
			setView(flyingImage);
			setSize(50, 100);
			setCenter(150 + (int) (minigamePosition - cameraPosition), 200);
		} else {
			setCenter(
					150 + (int) (minigamePosition - cameraPosition),
					400 - Math.max(0, (GRAVITY * jumpTime * jumpTime
							* jumpTime + JUMP_VELOCITY * jumpTime)));
			if (getBottom() > 440) {
				setView(runningImage);
				setSize(60, 100);
			} else {
				setView(flyingImage);
				setSize(50, 100);
			}
		}
	}

	public void setSpeed(int speed) {
		minigameSpeed = speed;
	}

	public double getSpeed() {
		return minigameSpeed;
	}

	public double getTimeSinceJump(double time) {
		return time - minigameTimeOfJump;
	}
}