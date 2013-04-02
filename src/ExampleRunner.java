import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import examples.Canvas;

public class ExampleRunner {
	public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Race!";

    public static void main(String[] args) {
        Canvas display = new Canvas(SIZE);
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(display, BorderLayout.CENTER);
        frame.setMinimumSize(new Dimension(800,650));
        frame.pack();
        frame.setVisible(true);
        display.start();
	}
}
