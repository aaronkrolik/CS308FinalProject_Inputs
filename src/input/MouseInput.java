package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class MouseInput extends InputDevice {
	JComponent myComponent;
	public final String myDevice = "MOUSE";

	Point lastPosition;
	long lastClickTime = 0;
	Input myInput;
	long buttonPressedTime = 0;
	String mouseSide;
	private List<MouseButton> buttons = new ArrayList<MouseButton>();

	public MouseInput(JComponent component, Input input) {
		super("MOUSE", input);
		myComponent = component;
		buttons.add(new MouseButton(MouseEvent.BUTTON1,"Left",this));
		buttons.add(new MouseButton(MouseEvent.BUTTON2,"center",this));
		buttons.add(new MouseButton(MouseEvent.BUTTON3,"right",this));
		initialize();
		myInput = input;
	}

	private PositionObject makePositionObject(MouseEvent e) {
		return new PositionObject(e.getX() / myComponent.getWidth(), e.getY()
				/ myComponent.getHeight(), e.getWhen());
	}

	/**
	 * Sets up single mouse listener. implements MouseMotionAdapter with
	 * mouseMove and MouseDrag
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
				for(MouseButton b : buttons){
					if(b.isThisSet(e.getButton())){
						b.mouseClicked(e);
						break;
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				for(MouseButton b: buttons){
					if(b.isThisSet(e.getButton())){
						b.mousePressed(e);
						break;
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
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
				}
				lastPosition = e.getPoint();
				lastClickTime = e.getWhen();
			}
		});
	}
	
	public void notifyInput(String keyInfo,MouseEvent e){
		notifyInputAction(keyInfo,makePositionObject(e));
	}
	
	public void setMousePressedTime(long time){
		buttonPressedTime = time;
	}
	
	public void setMouseSide(String s){
		mouseSide = s;
	}
}