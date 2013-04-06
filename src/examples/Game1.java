package examples;

import input.AlertObject;
import input.Input;
import input.InputClassTarget;
import input.InputMethodTarget;
import input.PositionObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

@InputClassTarget
public class Game1 {
	private static final int TRACK_LENGTH = 20000;
	public static final int CHEAT_CODE = KeyEvent.VK_G;
	public static final int ANTI_CHEAT_CODE = KeyEvent.VK_F;
	public static final int TV = 0;
	public static final int COMPUTER = 1;
	public static final int PORTABLE_DEVICE = 2;
	public static final int CAR = 3;
	public static final int TOTAL = 4;
	private Player jones;
	private Player you;
	private double time;
	private Dimension windowSize;
	private boolean popup = false;
	Input input1;
	Canvas myCanvas;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

	public Game1(Canvas canvas){
		myCanvas = canvas;
		jones = new Player(new Pixmap("runningJones.png"),new Pixmap("flyingJones.png"));
		you = new Player(new Pixmap("runningYou.png"),new Pixmap("flyingYou.png"));
		updateWindowSize();
		
		input1 = new Input("examples/Game1Mapping", myCanvas);
		input1.addListenerTo(this);

		setUpObstacles();
	}
	
	/**
	 * Stores the window size Dimension in a convenient local variable
	 */
	private void updateWindowSize() {
		windowSize = myCanvas.getSize();
	}
	
	@InputMethodTarget(name="jump")
	public void jumpInput(AlertObject alObj) {
		if (you.getTimeSinceJump(time) > 1 && you.getBottom() > 448) {
			you.jump(time);
		}
	}

	@InputMethodTarget(name="cheat")
	public void cheat(AlertObject alObj) {
		you.setCheating(true);
	}
	
	@InputMethodTarget(name="anticheat")
	public void anticheat(AlertObject alObj) {
			you.setAntiCheating(true);
	}
	
	@InputMethodTarget(name="stopcheat")
	public void stopcheat(AlertObject alObj) {
		you.setCheating(false);
	}

	@InputMethodTarget(name="stopanticheat")
	public void stopanticheat(AlertObject alObj) {
		you.setAntiCheating(false);
	}
	
	@InputMethodTarget(name="continue")
	public void goPastPopup(PositionObject posObj) {
		popup = false;
	}
	
	@InputMethodTarget(name="test")
	public void movementCoordTest(PositionObject posObj) {
		System.out.println(posObj.getX() + ", " + posObj.getY());
	}
	
	/**
	 * Keeps the local time counter updated
	 * 
	 * @param elapsedTime
	 */
	private void incrementTime(double elapsedTime) {
		time += elapsedTime;
	}
	
	private void setUpObstacles() {
		obstacles.clear();
		for (int i = 0; i < 60; i++) {
			obstacles.add(new Obstacle(TRACK_LENGTH * Math.random()));
		}
	}
	
	/**
     * Update game for this moment, given the time since the last moment.
     */
    public void update (double elapsedTime) {
    	updateWindowSize();
		incrementTime(elapsedTime);
		if (popup)
			return;
		you.update(time, you.getPosition());
		jones.update(time, you.getPosition());

		for (Obstacle i : obstacles) {
			if (i.getLeft() > jones.getRight()
					&& i.getLeft() < jones.getRight() + 120
					&& jones.getBottom() > 440
					&& jones.getTimeSinceJump(time) > 1) {
				jones.jump(time);
			}

			if (i.intersects(you)) {
				you.slowDown();
			}

			if (i.intersects(jones)) {
				jones.slowDown();
			}
		}

		if (you.getPosition() < TRACK_LENGTH
				&& jones.getPosition() < TRACK_LENGTH) {
			you.incrementPosition();
			jones.incrementPosition();
		}
    }
    
    /**
     * Draw all elements of the game.
     */
    public void paint (Graphics2D pen) {
    	paintMiniGameBackground(pen);
		jones.paint(pen);
		you.paint(pen);
		paintObstacles(pen);
		paintProgressIndicator(pen);
		if (popup) {
			paintPopup(pen);
		}
    }
    
    /**
	 * Draws obstacles in the MINIGAME view
	 * 
	 * @param pen
	 */
	private void paintObstacles(Graphics2D pen) {
		for (Obstacle i : obstacles) {
			pen.setColor(Color.black);
			i.paint(pen, you);
		}
	}

	/**
	 * Draws the progress indicator in the MINIGAME view
	 * 
	 * @param pen
	 */
	private void paintProgressIndicator(Graphics2D pen) {
		pen.setColor(Color.black);
		pen.fillRect(40, windowSize.height - 65, 300, 10);
		pen.setColor(Color.red);
		pen.fillRect(340, windowSize.height - 65, 10, 10);
		pen.setColor(Color.green);
		pen.fillRect(40 + (int) (jones.getPosition() * 300 / TRACK_LENGTH),
				windowSize.height - 73, 10, 20);
		pen.setColor(Color.blue);
		pen.fillRect(40 + (int) (you.getPosition() * 300 / TRACK_LENGTH),
				windowSize.height - 70, 10, 20);
	}

	/**
	 * Draws the Minigame background in the MINIGAME view
	 * 
	 * @param pen
	 */
	private void paintMiniGameBackground(Graphics2D pen) {
		pen.setColor(Color.blue);
		pen.fillRect(0, 0, windowSize.width, 40);
		pen.setColor(Color.gray);
		pen.fillRect(0, 405, windowSize.width, windowSize.height - 405);
		pen.setColor(Color.cyan);
		pen.fillRect(0, 40, windowSize.width, 365);
		pen.setColor(Color.white);
		pen.setFont(new Font("Default", Font.BOLD, 30));
		pen.drawString("Mini Game: Race Mr. Jones", 100, 30);
		pen.setColor(Color.red);
		pen.fillRect(130 + (int) (TRACK_LENGTH - you.getPosition()), 205, 300, 200);
		pen.setColor(Color.black);
		pen.drawString("STORE", 130 + (int)(TRACK_LENGTH - you.getPosition()) + 100, 250);

		//Draws The Houses
		for (int i = 0; i < 10; i++) {
			float housePosition = (int) (you.getPosition() - you.getPosition() % 300)
					+ 300 * i;
			pen.setColor(new Color(204, 102, 0));
			if (housePosition < TRACK_LENGTH)
				pen.fillRect(50 + (int) ((housePosition - you.getPosition()) * 1),
						205, 200, 200);
		}

		pen.setColor(Color.black);
		pen.setFont(new Font("Default", Font.BOLD, 30));
		if (you.getPosition() >= TRACK_LENGTH) {
			pen.drawString("You won the race!", 90, 100);
		} else if (jones.getPosition() >= TRACK_LENGTH) {
			pen.drawString(
					"You lost the race", 90, 100);
		} else {
			pen.drawString("Speed: " + (int)Math.floor(you.getSpeed()/5) + " MPH", //tries to be a more realistic sprint speed
					70, 100);
		}
	}
	
	/**
	 * Draws a Popup with the correct text whenever it is needed at the start of
	 * a game, minigame, or the end of a game
	 * 
	 * @param pen
	 */
	private void paintPopup(Graphics2D pen) {
		pen.setColor(Color.lightGray);
		pen.fillRect(windowSize.width / 4, windowSize.height / 4,
				windowSize.width / 2, windowSize.height / 2);
		pen.setFont(new Font("Default", Font.BOLD, 20));
		pen.setColor(Color.black);
		pen.drawString("Try to keep up!",
				windowSize.width / 2 - 80, windowSize.height / 4 + 110);
		pen.drawString("Jump with the space bar",
				windowSize.width / 2 - 120, windowSize.height / 4 + 150);
		pen.drawString("Click To Move On", windowSize.width / 2 - 90,
				windowSize.height * 3 / 4 - 30);
	}
}
