package examples;


//import game.Model;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Creates an area of the screen in which the game will be drawn that supports:
 * <UL>
 *   <LI>animation via the Timer
 *   <LI>mouse input via the MouseListener
 *   <LI>keyboard input via the KeyListener
 * </UL>
 * 
 * @author Robert C Duvall
 */
public class Canvas extends JComponent {
    // default serialization ID
    private static final long serialVersionUID = 1L;
    // animate 25 times per second if possible
    public static final int FRAMES_PER_SECOND = 25;
    // better way to think about timed events (in milliseconds)
    public static final int ONE_SECOND = 1000;
    public static final int DEFAULT_DELAY = ONE_SECOND / FRAMES_PER_SECOND;
    // input state
    public static final int NO_KEY_PRESSED = -1;

    private Timer myTimer;
    private Game1 myGame;
    private int myLastKeyPressed;
    private Point myLastMousePosition;
    boolean mouseDown = false;
    int cumulativeTime = 0;
    JFrame myFrame;
    
    /**
     * Create a panel so that it knows its size
     */
    public Canvas (Dimension size, JFrame frame) {
    	myFrame = frame;
        setPreferredSize(size);
        setSize(size);
        setFocusable(true);
        requestFocus();
        setInputListeners();
    }

    /**
     * Paint the contents of the canvas.
     * 
     * Never called by you directly, instead called by Java runtime
     * when area of screen covered by this container needs to be
     * displayed (i.e., creation, uncovering, change in status)
     * 
     * @param pen used to paint shape on the screen
     */
    @Override
    public void paintComponent (Graphics pen) {
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getSize().width, getSize().height);
        // first time needs to be special cased :(
        if (myGame != null) {
            myGame.paint((Graphics2D) pen);
        }
    }

    /**
     * Returns last key pressed by the user.
     */
    public int getLastKeyPressed () {
        return myLastKeyPressed;
    }

    /**
     * Returns last position of the mouse in the canvas.
     */
    public Point getLastMousePosition () {
        return myLastMousePosition;
    }

    /**
     * Start the animation.
     */
    public void start () {
        final int stepTime = DEFAULT_DELAY;
        // create a timer to animate the canvas
        Timer timer = new Timer(stepTime, 
            new ActionListener() {
                public void actionPerformed (ActionEvent e) {
                    myGame.update((double) stepTime / ONE_SECOND);
                    repaint();
                }
            });
        // start animation
        myGame = new Game1(this);
        timer.start();
    }

    /**
     * Stop the animation.
     */
    public void stop () {
        myTimer.stop();
    }

    /**
     * Create listeners that will update state based on user input.
     */
    private void setInputListeners () {
        // initialize input state
        myLastKeyPressed = NO_KEY_PRESSED;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                myLastKeyPressed = e.getKeyCode();
            }
            @Override
            public void keyReleased (KeyEvent e) {
                myLastKeyPressed = NO_KEY_PRESSED;
            }
        });
        myLastMousePosition = new Point();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
                myLastMousePosition = e.getPoint();
            }
        });
        
        
        //sets the current mouse click state
        mouseDown = false;
        addMouseListener(new MouseListener() {
        	@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {
				mouseDown = true;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				mouseDown = false;
			}
        });
    }
    
    /**
     * Returns true if the mouse button is down, and false if it is up
     * 
     * @return mouse click state
     */
    public boolean getMouseDown(){
    	return mouseDown;
    }
}
