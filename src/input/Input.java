package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JComponent;

public class Input {
	private final ResourceBundle RESOURCES;
	
	HashMap<String, Command> behaviors = new HashMap<String, Command>();
	ArrayList<InputDevice> inputDevices = new ArrayList<InputDevice>();
	
	public Input(String resourcePath, JComponent component) {
		RESOURCES = ResourceBundle.getBundle(resourcePath);
		
		inputDevices.add(new KeyboardListener(component));
		
		component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
            }
        });
        
		component.addMouseListener(new MouseListener() {
        	@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if(behaviors.containsKey("continue")) {
					ActionObject actObj = new ActionObject();
					//actObj.addData("xPos",arg0.getX());
					behaviors.get("continue").execute(actObj);
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
        });
	}
	
	public void updateDeviceCommands(String behaviorName) {
		for(String key : RESOURCES.keySet()) {
			String value = RESOURCES.getString(key);
			if(value.equals(behaviorName)) {
				String[] actionIdentifiers = key.split("_");
				for(InputDevice inDev : inputDevices) {
					if(actionIdentifiers[0].equals(inDev.getName()) && behaviors.containsKey(value)) {
						inDev.setCommand(actionIdentifiers, behaviors.get(RESOURCES.getString(key)));
					}
				}
			}
		}
	}
	
	public void setBehavior(String behaviorName, Command behavior) {
		behaviors.put(behaviorName, behavior);
		updateDeviceCommands(behaviorName);
	}
	
}
