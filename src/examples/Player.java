package examples;

import input.AlertObject;
import input.InputClassTarget;
import input.InputMethodTarget;

import java.awt.Dimension;

/**
 * Creates a Player object which contains the player's state. Is an extension of
 * Sprite so it can be drawn in a minigame.
 * 
 * @author Gavin Ovsak, Aaron Krolik
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

    private int playerPositionY = 0;
    private double minigamePosition = 0;
    private double minigameSpeed = 0;
    private double minigameTimeOfJump = -100;
    
    /**
     * A player object which contains the current state of a player and
     * extends Sprite so it can be drawn directly to the screen.
     * @param firstImage
     * @param secondImage
     */
    public Player(Pixmap firstImage, Pixmap secondImage) {
        super(firstImage, new Location(150, 400), new Dimension(60, 100));
        runningImage = firstImage;
        flyingImage = secondImage;
    }
    
    /**
     * A jump command which also records the time of jump.
     * @param time
     */
    public void jump(double time) {
        minigameTimeOfJump = time;
        if (minigameSpeed + 6 <= MAX_SPEED) {
            minigameSpeed += 6;
        }
    }
    
    /**
     * A cheat command which can be called by Input and turns
     * on a cheating state.
     * @param alObj
     */
    @InputMethodTarget(name="cheat")
    public void cheat(AlertObject alObj) {
        setCheating(true);
    }
    
    /**
     * An anti-cheat command (makes the game more challenging) which
     * can be called by Input and turns on an anticheating state.
     * @param alObj
     */
    @InputMethodTarget(name="anticheat")
    public void anticheat(AlertObject alObj) {
        setAntiCheating(true);
    }
    
    /**
     * A stop cheat command which can be called by Input and turns
     * off the cheating state.
     * @param alObj
     */
    @InputMethodTarget(name="stopcheat")
    public void stopcheat(AlertObject alObj) {
        setCheating(false);
    }
    
    /**
     * A stop anti-cheat command (makes the game more challenging) which
     * can be called by Input and turns off the anticheating state.
     * @param alObj
     */
    @InputMethodTarget(name="stopanticheat")
    public void stopanticheat(AlertObject alObj) {
        setAntiCheating(false);
    }
    
    /**
     * A function which increments the position of the player depending
     * on its current speed.
     */
    public void incrementPosition() {
        minigamePosition += minigameSpeed;

        if ((isCheating || getBottom() > 440) && minigameSpeed < MAX_SPEED) {
            minigameSpeed += 1;
        }
        if(isAntiCheating) {
            minigameSpeed = 0;
        }
    }
    
    /**
     * A function which decreases the player's speed.
     */
    public void slowDown() {
        if (minigameSpeed > 10) {
            minigameSpeed -= 10;
        }
    }
    
    /**
     * A function which increases the player's speed.
     * @param alObj
     */
    public void speedUp(AlertObject alObj){
        changeMinigameSpeed(20);
    }
    
    /**
     * A function which changes the minigame's speed
     * @param in
     */
    public void changeMinigameSpeed(int in){
        minigameSpeed += in;
    }

    /**
     * A function which returns the position of the minigame
     * @return
     */
    public double getPosition() {
        return minigamePosition;
    }

    /**
     * A function which resets the minigame parameters
     */
    public void resetMinigameVariables() {
        minigamePosition = 0;
        minigameSpeed = 0;
        minigameTimeOfJump = -100;
    }
    
    /**
     * A function which sets the cheating state to the given boolean
     * @param isCheating
     */
    public void setCheating(boolean isCheating) {
        this.isCheating = isCheating;
        
    }

    /**
     * A function which sets the anti-cheating (more challenging) state
     * to the given boolean.
     * @param isAntiCheating
     */
    public void setAntiCheating(boolean isAntiCheating) {
        this.isAntiCheating = isAntiCheating;
    }

    /**
     * A function which updates the image and position of the player.
     * @param time
     * @param cameraPosition
     */
    public void update(double time, double cameraPosition) {
        double jumpTime = time - minigameTimeOfJump;
        if (isCheating) {
            setView(flyingImage);
            setSize(50, 100);
            setCenter(150 + (int) (minigamePosition - cameraPosition), 100);
        } else {
            setCenter(
                    150 + (int) (minigamePosition - cameraPosition),
                    400 - Math.max(playerPositionY, (GRAVITY * jumpTime * jumpTime
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

    /**
     * A function which sets the player's speed.
     * @param speed
     */
    public void setSpeed(int speed) {
        minigameSpeed = speed;
    }

    /**
     * A function which returns the current speed of the player.
     * @return
     */
    public double getSpeed() {
        return minigameSpeed;
    }

    /**
     * A function which returns the difference in time
     * between the last jump and the present
     * @param time
     * @return
     */
    public double getTimeSinceJump(double currentTime) {
        return currentTime - minigameTimeOfJump;
    }
}