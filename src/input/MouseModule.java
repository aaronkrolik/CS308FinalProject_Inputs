package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

public class MouseModule extends InputDevice{
	JComponent myComponent;
	Input myInput;
	public final String myDevice = "MOUSE";
	
	public MouseModule(JComponent component, Input input){
		super("Keyboard");
		myInput = input;
		myComponent = component;
		initialize();
	}
	
	private void initialize(){
		myComponent.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
            public void mouseMoved (MouseEvent e) {
				myInput.actionNotification("Mouse_Move", new PositionObject(e.getX(), e.getY()));
            }
            
			@Override
            public void mouseDragged (MouseEvent e) {
				myInput.actionNotification("Mouse_Drag", new PositionObject(e.getX(), e.getY()));
            }
        });
        
		myComponent.addMouseListener(new MouseListener() {
        	@Override
			public void mouseClicked(MouseEvent e) {
				String mouseSide = "";
				switch(e.getButton()) {
					case MouseEvent.BUTTON1:
						mouseSide = "Left";
						break;
					case MouseEvent.BUTTON2:
						mouseSide = "Right";
						break;
				}
				myInput.actionNotification("Mouse_" + mouseSide + "_Click", new PositionObject(e.getX(), e.getY()));
        	}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				String mouseSide = "";
				switch(e.getButton()) {
					case MouseEvent.BUTTON1:
						mouseSide = "Left";
						break;
					case MouseEvent.BUTTON2:
						mouseSide = "Right";
						break;
				}
				myInput.actionNotification("Mouse_" + mouseSide + "_Down", new PositionObject(e.getX(), e.getY()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				String mouseSide = "";
				switch(e.getButton()) {
					case MouseEvent.BUTTON1:
						mouseSide = "Left";
						break;
					case MouseEvent.BUTTON2:
						mouseSide = "Right";
						break;
				}
				myInput.actionNotification("Mouse_" + mouseSide + "_Up", new PositionObject(e.getX(), e.getY()));
			}
        });
	}
}
