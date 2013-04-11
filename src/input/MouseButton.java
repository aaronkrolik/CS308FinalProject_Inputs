package input;

import java.awt.event.MouseEvent;

public class MouseButton {
	private int myButton;
	private String buttonName;
	private MouseInput myInput;
	private long pressedTime;
	
	public MouseButton(int button, String name,MouseInput input){
		myButton = button;
		buttonName = name;
		myInput = input;
	}
	
	public boolean isThisSet(int button){
		return myButton == button;
	}
	
	public void mouseClicked(MouseEvent e){
		String inputSource = "Mouse_"+buttonName+"_Click";
		myInput.notifyInput(inputSource, e);
	}
	
	public void mousePressed(MouseEvent e){
		pressedTime = e.getWhen();
		String inputSource = "Mouse_"+ buttonName +"_Down";
		myInput.notifyInput(inputSource, e);
	}
	
	public void mouseReleased(MouseEvent e){
		myInput.setMousePressedTime(pressedTime);
		pressedTime = 0;
		String inputSource = "Mouse_"+buttonName+"_Up";
		myInput.setMouseSide(buttonName);
		myInput.notifyInput(inputSource, e);
	}
}
