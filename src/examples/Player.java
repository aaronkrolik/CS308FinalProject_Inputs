package examples;

import input.ActionObject;
import input.InputClassTarget;
import input.InputMethodTarget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * Creates a Player object which contains the player's state. Is an extension of
 * Sprite so it can be drawn in a minigame.
 * 
 * @author Gavin Ovsak
 */
@InputClassTarget
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
	
	private Map<String, Method> myMapping;
	
	
	public void initListener(Map<String, Method> in){
		myMapping = in;
		System.out.println(myMapping);
	}
	
	@InputMethodTarget(name = "foo")
	public void listener(ActionObject in){
		
		try {
			myMapping.get(in.myName).invoke(this, in);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
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

	@InputMethodTarget(name = "blah")
	public void  doShit(ActionObject in){
		System.out.println("works! "+in.myName);
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
	@InputMethodTarget(name="jump")
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