package examples;

import input.AlertObject;
import input.InputClassTarget;
import input.InputMethodTarget;

/**
 * An example of a subclass of an annotated class which still retains
 * the ability for input to make method calls to inherited annotated
 * methods but also has the ability to reference annotated new methods.
 * @author Aaron Krolik
 */
@InputClassTarget
public class PlayerSuperHero extends Player{

	private final int ACCELERATION_SPEED = 20;
	private final int DECELERATION_SPEED = -5;
	/**
	 * A super-powered version of player
	 * @param firstImage
	 * @param secondImage
	 */
    public PlayerSuperHero(Pixmap firstImage, Pixmap secondImage) {
        super(firstImage, secondImage);
    }
    
    /**
     * An overriden method which can be called by Input to speed
     * up the player.
     */
    @Override
    @InputMethodTarget(name="playerspeedup")
    public void speedUp(AlertObject alObj){
        changeMinigameSpeed(ACCELERATION_SPEED);
    }
    
    /**
     * An overriden method which can be called by Input to slow
     * down the player.
     */
    @Override
    @InputMethodTarget(name="playerslowdown")
    public void slowDown() {
        changeMinigameSpeed(DECELERATION_SPEED);
    }
}
