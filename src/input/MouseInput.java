package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;

public class MouseInput extends InputDevice {
	JComponent myComponent;
	public final String myDevice = "MOUSE";

	long leftPressedTime = 0;
	long rightPressedTime = 0;
	long centerPressedTime = 0;

	Point lastPosition;
	long lastClickTime = 0;

	public MouseInput(JComponent component, Input input) {
		super("MOUSE", input);
		myComponent = component;
		initialize();
	}

	private PositionObject makePositionObject(MouseEvent e) {
		return new PositionObject(e.getX() / myComponent.getWidth(), e.getY() / myComponent.getHeight(), e.getWhen());
	}
	

		
	

	/**
	 * Sets up single mouse listener. implements MouseMotionAdapter with mouseMove and MouseDrag
	 */
	private void initialize() {
		myComponent.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				notifyInputAction("Mouse_Move", makePositionObject(e));
			}
			public void mouseDragged(MouseEvent e) {
				notifyInputAction("Mouse_Drag", makePositionObject(e));
			}
		});

		myComponent.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
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
			}

			@Override
			public void mouseReleased(MouseEvent e) {
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
				
				if (e.getWhen() - buttonPressedTime < 100) {
					notifyInputAction("Mouse_" + mouseSide + "_ShortClick", makePositionObject(e));
				}
				if (e.getWhen() - buttonPressedTime > 400) {
					notifyInputAction("Mouse_" + mouseSide + "_LongClick", makePositionObject(e));
				}
				if (lastPosition != null && lastPosition.distance(e.getPoint()) < 10 && e.getWhen() - lastClickTime < 200) {
					notifyInputAction("Mouse_" + mouseSide + "_DoubleClick", makePositionObject(e));
				}
				lastPosition = e.getPoint();
				lastClickTime = e.getWhen();
			}
		});
	}
}