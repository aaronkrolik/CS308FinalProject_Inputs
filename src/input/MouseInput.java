package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> Merge and Refactor
import javax.swing.JComponent;

public class MouseInput extends InputDevice {
	JComponent myComponent;
	public final String myDevice = "MOUSE";

<<<<<<< HEAD
	long leftPressedTime = 0;
	long rightPressedTime = 0;
	long centerPressedTime = 0;

	Point lastPosition;
	long lastClickTime = 0;
	Input myInput;
=======
	Point lastPosition;
	long lastClickTime = 0;
	Input myInput;
	long buttonPressedTime = 0;
	String mouseSide;
	private List<MouseButton> buttons = new ArrayList<MouseButton>();
>>>>>>> Merge and Refactor

	public MouseInput(JComponent component, Input input) {
		super("MOUSE", input);
		myComponent = component;
<<<<<<< HEAD
=======
		buttons.add(new MouseButton(MouseEvent.BUTTON1,"Left",this));
		buttons.add(new MouseButton(MouseEvent.BUTTON2,"center",this));
		buttons.add(new MouseButton(MouseEvent.BUTTON3,"right",this));
>>>>>>> Merge and Refactor
		initialize();
		myInput = input;
	}

	private PositionObject makePositionObject(MouseEvent e) {
<<<<<<< HEAD
		return new PositionObject(e.getX() / myComponent.getWidth(), e.getY() / myComponent.getHeight(), e.getWhen());
	}
	
	/**
	 * Sets up single mouse listener. implements MouseMotionAdapter with mouseMove and MouseDrag
=======
		return new PositionObject(e.getX() / myComponent.getWidth(), e.getY()
				/ myComponent.getHeight(), e.getWhen());
	}

	/**
	 * Sets up single mouse listener. implements MouseMotionAdapter with
	 * mouseMove and MouseDrag
>>>>>>> Merge and Refactor
	 */
	private void initialize() {
		myComponent.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				notifyInputAction("Mouse_Move", makePositionObject(e));
			}
<<<<<<< HEAD
=======

>>>>>>> Merge and Refactor
			public void mouseDragged(MouseEvent e) {
				notifyInputAction("Mouse_Drag", makePositionObject(e));
			}
		});

		myComponent.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
<<<<<<< HEAD
				String mouseSide = "";
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					mouseSide = "Left";
					break;
				case MouseEvent.BUTTON2:
					mouseSide = "Center";
					break;
				case MouseEvent.BUTTON3:
					mouseSide = "Right";
					break;
				}
				String inputSource = "Mouse_" + mouseSide + "_Click";
				notifyInputAction(inputSource, makePositionObject(e));
=======
				for(MouseButton b : buttons){
					if(b.isThisSet(e.getButton())){
						b.mouseClicked(e);
						break;
					}
				}
>>>>>>> Merge and Refactor
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
<<<<<<< HEAD
				String mouseSide = "";
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					mouseSide = "Left";
					leftPressedTime = e.getWhen();
					break;
				case MouseEvent.BUTTON2:
					mouseSide = "Center";
					centerPressedTime = e.getWhen();
					break;
				case MouseEvent.BUTTON3:
					mouseSide = "Right";
					rightPressedTime = e.getWhen();
					break;
				}
				String inputSource = "Mouse_" + mouseSide + "_Down";
				notifyInputAction(inputSource, makePositionObject(e));
=======
				for(MouseButton b: buttons){
					if(b.isThisSet(e.getButton())){
						b.mousePressed(e);
						break;
					}
				}
>>>>>>> Merge and Refactor
			}

			@Override
			public void mouseReleased(MouseEvent e) {
<<<<<<< HEAD
				String mouseSide = "";
				long buttonPressedTime = 0;
				switch (e.getButton()) {
				
					case MouseEvent.BUTTON1:
						mouseSide = "Left";
						buttonPressedTime = leftPressedTime;
						leftPressedTime = 0;
						break;
					case MouseEvent.BUTTON2:
						mouseSide = "Center";
						buttonPressedTime = centerPressedTime;
						centerPressedTime = 0;
						break;
					case MouseEvent.BUTTON3:
						mouseSide = "Right";
						buttonPressedTime = rightPressedTime;
						rightPressedTime = 0;
						break;	
				}
				
				notifyInputAction("Mouse_" + mouseSide + "_Up", makePositionObject(e));
				
				if (e.getWhen() - buttonPressedTime < Integer.parseInt(myInput.getSetting("ShortClickTimeThreshold"))) {
					notifyInputAction("Mouse_" + mouseSide + "_ShortClick", makePositionObject(e));
				}
				if (e.getWhen() - buttonPressedTime > Integer.parseInt(myInput.getSetting("LongClickTimeThreshold"))) {
					notifyInputAction("Mouse_" + mouseSide + "_LongClick", makePositionObject(e));
				}
				if (lastPosition != null && lastPosition.distance(e.getPoint()) < Integer.parseInt(myInput.getSetting("DoubleClickDistanceThreshold")) && e.getWhen() - lastClickTime < Integer.parseInt(myInput.getSetting("DoubleClickTimeThreshold")))  {
					notifyInputAction("Mouse_" + mouseSide + "_DoubleClick", makePositionObject(e));
=======
				buttonPressedTime = 0;
				for(MouseButton b: buttons){
					if(b.isThisSet(e.getButton())){
						b.mouseReleased(e);
						break;
					}
				}
				
				if (e.getWhen() - buttonPressedTime < Integer.parseInt(myInput
						.getSetting("ShortClickTimeThreshold"))) {
					notifyInputAction("Mouse_" + mouseSide + "_ShortClick",
							makePositionObject(e));
				}
				if (e.getWhen() - buttonPressedTime > Integer.parseInt(myInput
						.getSetting("LongClickTimeThreshold"))) {
					notifyInputAction("Mouse_" + mouseSide + "_LongClick",
							makePositionObject(e));
				}
				if (lastPosition != null
						&& lastPosition.distance(e.getPoint()) < Integer.parseInt(myInput
								.getSetting("DoubleClickDistanceThreshold"))
						&& e.getWhen() - lastClickTime < Integer.parseInt(myInput
								.getSetting("DoubleClickTimeThreshold"))) {
					notifyInputAction("Mouse_" + mouseSide + "_DoubleClick",
							makePositionObject(e));
>>>>>>> Merge and Refactor
				}
				lastPosition = e.getPoint();
				lastClickTime = e.getWhen();
			}
		});
	}
<<<<<<< HEAD
=======
	
	public void notifyInput(String keyInfo,MouseEvent e){
		notifyInputAction(keyInfo,makePositionObject(e));
	}
	
	public void setMousePressedTime(long time){
		buttonPressedTime = time;
	}
	
	public void setMouseSide(String s){
		mouseSide = s;
	}
>>>>>>> Merge and Refactor
}