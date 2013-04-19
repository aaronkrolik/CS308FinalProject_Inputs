package examples;

import input.AlertObject;
import input.InputClassTarget;
import input.InputMethodTarget;

@InputClassTarget
public class PlayerSuperHero extends Player{

	public PlayerSuperHero(Pixmap firstImage, Pixmap secondImage) {
		super(firstImage, secondImage);
	}
	
	@Override
	@InputMethodTarget(name="playerspeedup")
	public void speedUp(AlertObject alObj){
		changeMinigameSpeed(20);
	}
	
	@Override
	@InputMethodTarget(name="playerslowdown")
	public void slowDown() {
		changeMinigameSpeed(-5);
	}
}
