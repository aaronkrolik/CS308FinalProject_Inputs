import examples.Canvas;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * The constructor of an example game which uses Input
 * @author Gavin Ovsak
 */
public class ExampleRunner {
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Race!";

    /**
     * Construct a frame and start the game.
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(TITLE);
        Canvas display = new Canvas(SIZE, frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(display, BorderLayout.CENTER);
        frame.setMinimumSize(new Dimension(800,650));
        frame.pack();
        frame.setVisible(true);
        display.start();
    }
}
