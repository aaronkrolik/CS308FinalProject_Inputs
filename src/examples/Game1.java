package examples;

import java.awt.geom.Point2D;

import input.ActionObject;
import input.Command;
import input.Input;

public class Game1 {
	Pixmap avatar = new Pixmap("/src/examples/jumper");
	Point2D position = new Point2D.Double();
	
	public Game1() {
		Input input1 = new Input("examples/Game1Mapping");
		input1.setBehavior("jump", new Command() {
			@Override
			public void execute(ActionObject actObj) {
				jump();
			}
		});
	}
	
	public void jump() {
		position.setLocation(0, 50);
	}
	
}
