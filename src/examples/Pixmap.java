package examples;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class represents an image on the screen and 
 * adds some utility functions to the Image class.
 * 
 * Note, Java only supports the formats: png, jpg, gif.
 * 
 * @author Robert C. Duvall, Gavin Ovsak
 */
public class Pixmap {
    // underlying implementation
    private BufferedImage myImage;
    private String myFileName;


    /**
     * Create an image from the given filename.
     * @param fileName is the file of image
     */
    public Pixmap (String fileName) {
        setImage(fileName);
    }

    /**
     * Create a copy of image from the given other image.
     * @param other 
     */
    public Pixmap (Pixmap other) {
        this(other.myFileName);
    }

    /**
     * Set this image to the image referred to by the given filename.
     * @param fileName 
     */
    public void setImage (String fileName) {
    	try {
			myImage = ImageIO.read(new File("src/examples/" + fileName));
		} catch (IOException e) {}
        myFileName = fileName;
    }

    /**
     * Describes how to draw the image on the screen.
     * @param pen 
     * @param center 
     * @param size 
     */
    public void paint (Graphics2D pen, Point2D center, Dimension size) {
        paint(pen, center, size, 0);
    }

    /**
     * Describes how to draw the image rotated on the screen.
     * 
     * angle is in degrees
     * @param pen 
     * @param center 
     * @param size 
     * @param angle 
     */
    public void paint (Graphics2D pen, Point2D center, Dimension size, double angle) {
        // save current state of the graphics area
        AffineTransform old = new AffineTransform(pen.getTransform());
        // move graphics area to center of this shape
        pen.translate(center.getX(), center.getY());
        // rotate area about this shape
        pen.rotate(Math.toRadians(angle));
        // draw as usual (i.e., rotated)
        pen.drawImage(myImage, -size.width / 2, -size.height / 2, size.width, size.height, null);
        // restore graphics area to its old state, so our changes have no lasting effects
        pen.setTransform(old);
    }
}
