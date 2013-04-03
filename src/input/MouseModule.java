package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

public class MouseModule extends InputDevice{
	JComponent myComponent;
	private Input myInput;
	public final String myDevice = "MOUSE";
	
	public MouseModule(JComponent component, Input input){
		super("Keyboard");
		myComponent = component;
		myInput = input;
		initialize();
	}
	
	private ActionObject getObject(MouseEvent e){
		ActionObject actObj = new ActionObject();
		actObj.setData("X_Position", ""+e.getX());
		actObj.setData("Y_Position", ""+e.getY());
		actObj.setData("My Caller Device", myDevice);
		actObj.setData("My Data", e.paramString());
		return actObj;
	}
	
	private void initialize(){
		myComponent.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
            public void mouseMoved (MouseEvent e) {
				myInput.actionNotification("Mouse_Move", getObject(e));
            }
            
			@Override
            public void mouseDragged (MouseEvent e) {
				myInput.actionNotification("Mouse_Drag", getObject(e));
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
				myInput.actionNotification("Mouse_" + mouseSide + "_Click", getObject(e));
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
				myInput.actionNotification("Mouse_" + mouseSide + "_Down", getObject(e));
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
				myInput.actionNotification("Mouse_" + mouseSide + "_Up", getObject(e));
			}
        });
	}
}
