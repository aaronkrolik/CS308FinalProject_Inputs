package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

public class MouseInput extends InputDevice {
	JComponent myComponent;
	public static final String myDevice = "Mouse";

	private Point lastPosition;
	private long lastClickTime = 0;
	private String lastClickButton = "";
	private Input myInput;
	private InputDevice inDev = this;
	private Map<Integer, String> mouseNameMap;
	private List<ButtonState> downButtons = new ArrayList<ButtonState>();

	public MouseInput(JComponent component, Input input) {
		super(myDevice, input);
		myComponent = component;
		mouseNameMap = new HashMap<Integer, String>();
		mouseNameMap.put(MouseEvent.BUTTON1, "Left");
		mouseNameMap.put(MouseEvent.BUTTON2, "Center");
		mouseNameMap.put(MouseEvent.BUTTON3, "Right");		
		initialize();
		myInput = input;
	}

	private PositionObject makePositionObject(MouseEvent e) {
		return new PositionObject(e.getX() / myComponent.getWidth(), 
				                  e.getY() / myComponent.getHeight(), 
				                  e.getWhen());
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
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				String buttonName = mouseNameMap.get(e.getButton());
				ButtonState downButton = new ButtonState(myDevice, buttonName, e.getWhen(), inDev);
				downButtons.add(downButton);
				notifyInputAction("Mouse_" + buttonName + "_Down", new AlertObject(e.getWhen()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				String buttonName = mouseNameMap.get(e.getButton());
				ButtonState temp = new ButtonState(myDevice, buttonName, e.getWhen(), inDev);
				notifyInputAction(temp.getFullName() + "_Up", new AlertObject(e.getWhen()));
				notifyInputAction(temp.getFullName() + "_KeyUp", new AlertObject(e.getWhen())); //Legacy Support
				notifyInputAction(temp.getFullName() + "_Click", new AlertObject(e.getWhen())); //Legacy Support
				long timeDifference = temp.getTime() - downButtons.remove(downButtons.indexOf(temp)).getTime();
				
				if (timeDifference < Integer.parseInt(myInput
						.getSetting("ShortClickTimeThreshold"))) {
					notifyInputAction(temp.getFullName() + "_ShortClick",
					makePositionObject(e));
				}
				if (timeDifference > Integer.parseInt(myInput
						.getSetting("LongClickTimeThreshold"))) {
					notifyInputAction(temp.getFullName() + "_LongClick",
					makePositionObject(e));
				}
				if (lastPosition != null && lastClickButton.equals(buttonName)
						&& lastPosition.distance(e.getPoint()) < Integer.parseInt(myInput
								.getSetting("DoubleClickDistanceThreshold"))
						&& e.getWhen() - lastClickTime < Integer.parseInt(myInput
								.getSetting("DoubleClickTimeThreshold"))) {
					notifyInputAction(temp.getFullName() + "_DoubleClick",
					makePositionObject(e));
				}
				lastPosition = e.getPoint();
				lastClickTime = e.getWhen();
				lastClickButton = buttonName;
			}
		});
	}
	
	public void notifyInput(String keyInfo,MouseEvent e){
		notifyInputAction(keyInfo,makePositionObject(e));
	}
}