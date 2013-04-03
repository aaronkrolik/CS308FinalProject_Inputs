package input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardInitialler {
	private Map<Integer,String> keyDownMap;
	private Map<Integer,String> keyUpMap;
	
	public KeyboardInitialler(){
		initKeyboardMapping();
	}
	
	public void initKeyboardMapping(){
		initKeyDownMapping();
		initKeyUpMapping();
	}
	
	private void initKeyDownMapping(){
		keyDownMap = new HashMap<Integer,String>();
		keyDownMap.put(KeyEvent.VK_SPACE, "Keyboard_KeyDown_Spacebar");
		keyDownMap.put(KeyEvent.VK_0, "Keyboard_KeyDown_0");
		keyDownMap.put(KeyEvent.VK_1, "Keyboard_KeyDown_1");
		keyDownMap.put(KeyEvent.VK_2, "Keyboard_KeyDown_2");
		keyDownMap.put(KeyEvent.VK_3, "Keyboard_KeyDown_3");
		keyDownMap.put(KeyEvent.VK_4, "Keyboard_KeyDown_4");
		keyDownMap.put(KeyEvent.VK_5, "Keyboard_KeyDown_5");
		keyDownMap.put(KeyEvent.VK_6, "Keyboard_KeyDown_6");
		keyDownMap.put(KeyEvent.VK_7, "Keyboard_KeyDown_7");
		keyDownMap.put(KeyEvent.VK_8, "Keyboard_KeyDown_8");
		keyDownMap.put(KeyEvent.VK_9, "Keyboard_KeyDown_9");
		keyDownMap.put(KeyEvent.VK_A, "Keyboard_KeyDown_A");
		keyDownMap.put(KeyEvent.VK_B, "Keyboard_KeyDown_B");
		keyDownMap.put(KeyEvent.VK_C, "Keyboard_KeyDown_C");
		keyDownMap.put(KeyEvent.VK_D, "Keyboard_KeyDown_D");
		keyDownMap.put(KeyEvent.VK_E, "Keyboard_KeyDown_E");
		keyDownMap.put(KeyEvent.VK_F, "Keyboard_KeyDown_F");
		keyDownMap.put(KeyEvent.VK_G, "Keyboard_KeyDown_G");
		keyDownMap.put(KeyEvent.VK_H, "Keyboard_KeyDown_H");
		keyDownMap.put(KeyEvent.VK_I, "Keyboard_KeyDown_I");
		keyDownMap.put(KeyEvent.VK_J, "Keyboard_KeyDown_J");
		keyDownMap.put(KeyEvent.VK_K, "Keyboard_KeyDown_K");
		keyDownMap.put(KeyEvent.VK_L, "Keyboard_KeyDown_L");
		keyDownMap.put(KeyEvent.VK_M, "Keyboard_KeyDown_M");
		keyDownMap.put(KeyEvent.VK_N, "Keyboard_KeyDown_N");
		keyDownMap.put(KeyEvent.VK_O, "Keyboard_KeyDown_O");
		keyDownMap.put(KeyEvent.VK_P, "Keyboard_KeyDown_P");
		keyDownMap.put(KeyEvent.VK_Q, "Keyboard_KeyDown_Q");
		keyDownMap.put(KeyEvent.VK_R, "Keyboard_KeyDown_R");
		keyDownMap.put(KeyEvent.VK_S, "Keyboard_KeyDown_S");
		keyDownMap.put(KeyEvent.VK_T, "Keyboard_KeyDown_T");
		keyDownMap.put(KeyEvent.VK_U, "Keyboard_KeyDown_U");
		keyDownMap.put(KeyEvent.VK_V, "Keyboard_KeyDown_V");
		keyDownMap.put(KeyEvent.VK_W, "Keyboard_KeyDown_W");
		keyDownMap.put(KeyEvent.VK_X, "Keyboard_KeyDown_X");
		keyDownMap.put(KeyEvent.VK_Y, "Keyboard_KeyDown_Y");
		keyDownMap.put(KeyEvent.VK_Z, "Keyboard_KeyDown_Z");
		keyDownMap.put(KeyEvent.VK_LEFT, "Keyboard_KeyDown_LEFT");
		keyDownMap.put(KeyEvent.VK_RIGHT, "Keyboard_KeyDown_RIGHT");
		keyDownMap.put(KeyEvent.VK_UP, "Keyboard_KeyDown_UP");
		keyDownMap.put(KeyEvent.VK_DOWN, "Keyboard_KeyDown_DOWN");
		keyDownMap.put(KeyEvent.VK_ENTER, "Keyboard_KeyDown_ENTER");
		keyDownMap.put(KeyEvent.VK_DELETE, "Keyboard_KeyDown_DELETE");
		keyDownMap.put(KeyEvent.VK_CONTROL, "Keyboard_KeyDown_CONTROL");
		keyDownMap.put(KeyEvent.VK_SHIFT, "Keyboard_KeyDown_SHIFT");
		keyDownMap.put(KeyEvent.VK_TAB, "Keyboard_KeyDown_TAB");
		keyDownMap.put(KeyEvent.VK_ADD, "Keyboard_KeyDown_ADD");
		keyDownMap.put(KeyEvent.VK_MINUS, "Keyboard_KeyDown_MINUS");
		keyDownMap.put(KeyEvent.VK_F1, "Keyboard_KeyDown_F1");
		keyDownMap.put(KeyEvent.VK_F2, "Keyboard_KeyDown_F2");
		keyDownMap.put(KeyEvent.VK_F3, "Keyboard_KeyDown_F3");
		keyDownMap.put(KeyEvent.VK_F4, "Keyboard_KeyDown_F4");
		keyDownMap.put(KeyEvent.VK_F5, "Keyboard_KeyDown_F5");
		keyDownMap.put(KeyEvent.VK_F6, "Keyboard_KeyDown_F6");
		keyDownMap.put(KeyEvent.VK_F7, "Keyboard_KeyDown_F7");
		keyDownMap.put(KeyEvent.VK_F8, "Keyboard_KeyDown_F8");
		keyDownMap.put(KeyEvent.VK_F9, "Keyboard_KeyDown_F9");
		keyDownMap.put(KeyEvent.VK_F10, "Keyboard_KeyDown_F10");
		keyDownMap.put(KeyEvent.VK_F11, "Keyboard_KeyDown_F11");
		keyDownMap.put(KeyEvent.VK_F12, "Keyboard_KeyDown_F12");
	}
	
	private void initKeyUpMapping(){
		keyUpMap = new HashMap<Integer,String>();
		keyUpMap.put(KeyEvent.VK_SPACE, "Keyboard_KeyUp_Spacebar");
		keyUpMap.put(KeyEvent.VK_0, "Keyboard_KeyUp_0");
		keyUpMap.put(KeyEvent.VK_1, "Keyboard_KeyUp_1");
		keyUpMap.put(KeyEvent.VK_2, "Keyboard_KeyUp_2");
		keyUpMap.put(KeyEvent.VK_3, "Keyboard_KeyUp_3");
		keyUpMap.put(KeyEvent.VK_4, "Keyboard_KeyUp_4");
		keyUpMap.put(KeyEvent.VK_5, "Keyboard_KeyUp_5");
		keyUpMap.put(KeyEvent.VK_6, "Keyboard_KeyUp_6");
		keyUpMap.put(KeyEvent.VK_7, "Keyboard_KeyUp_7");
		keyUpMap.put(KeyEvent.VK_8, "Keyboard_KeyUp_8");
		keyUpMap.put(KeyEvent.VK_9, "Keyboard_KeyUp_9");
		keyUpMap.put(KeyEvent.VK_A, "Keyboard_KeyUp_A");
		keyUpMap.put(KeyEvent.VK_B, "Keyboard_KeyUp_B");
		keyUpMap.put(KeyEvent.VK_C, "Keyboard_KeyUp_C");
		keyUpMap.put(KeyEvent.VK_D, "Keyboard_KeyUp_D");
		keyUpMap.put(KeyEvent.VK_E, "Keyboard_KeyUp_E");
		keyUpMap.put(KeyEvent.VK_F, "Keyboard_KeyUp_F");
		keyUpMap.put(KeyEvent.VK_G, "Keyboard_KeyUp_G");
		keyUpMap.put(KeyEvent.VK_H, "Keyboard_KeyUp_H");
		keyUpMap.put(KeyEvent.VK_I, "Keyboard_KeyUp_I");
		keyUpMap.put(KeyEvent.VK_J, "Keyboard_KeyUp_J");
		keyUpMap.put(KeyEvent.VK_K, "Keyboard_KeyUp_K");
		keyUpMap.put(KeyEvent.VK_L, "Keyboard_KeyUp_L");
		keyUpMap.put(KeyEvent.VK_M, "Keyboard_KeyUp_M");
		keyUpMap.put(KeyEvent.VK_N, "Keyboard_KeyUp_N");
		keyUpMap.put(KeyEvent.VK_O, "Keyboard_KeyUp_O");
		keyUpMap.put(KeyEvent.VK_P, "Keyboard_KeyUp_P");
		keyUpMap.put(KeyEvent.VK_Q, "Keyboard_KeyUp_Q");
		keyUpMap.put(KeyEvent.VK_R, "Keyboard_KeyUp_R");
		keyUpMap.put(KeyEvent.VK_S, "Keyboard_KeyUp_S");
		keyUpMap.put(KeyEvent.VK_T, "Keyboard_KeyUp_T");
		keyUpMap.put(KeyEvent.VK_U, "Keyboard_KeyUp_U");
		keyUpMap.put(KeyEvent.VK_V, "Keyboard_KeyUp_V");
		keyUpMap.put(KeyEvent.VK_W, "Keyboard_KeyUp_W");
		keyUpMap.put(KeyEvent.VK_X, "Keyboard_KeyUp_X");
		keyUpMap.put(KeyEvent.VK_Y, "Keyboard_KeyUp_Y");
		keyUpMap.put(KeyEvent.VK_Z, "Keyboard_KeyUp_Z");
		keyUpMap.put(KeyEvent.VK_LEFT, "Keyboard_KeyUp_LEFT");
		keyUpMap.put(KeyEvent.VK_RIGHT, "Keyboard_KeyUp_RIGHT");
		keyUpMap.put(KeyEvent.VK_UP, "Keyboard_KeyUp_UP");
		keyUpMap.put(KeyEvent.VK_DOWN, "Keyboard_KeyUp_DOWN");
		keyUpMap.put(KeyEvent.VK_ENTER, "Keyboard_KeyUp_ENTER");
		keyUpMap.put(KeyEvent.VK_DELETE, "Keyboard_KeyUp_DELETE");
		keyUpMap.put(KeyEvent.VK_CONTROL, "Keyboard_KeyUp_CONTROL");
		keyUpMap.put(KeyEvent.VK_SHIFT, "Keyboard_KeyUp_SHIFT");
		keyUpMap.put(KeyEvent.VK_TAB, "Keyboard_KeyUp_TAB");
		keyUpMap.put(KeyEvent.VK_ADD, "Keyboard_KeyUp_ADD");
		keyUpMap.put(KeyEvent.VK_MINUS, "Keyboard_KeyUp_MINUS");
		keyUpMap.put(KeyEvent.VK_F1, "Keyboard_KeyUp_F1");
		keyUpMap.put(KeyEvent.VK_F2, "Keyboard_KeyUp_F2");
		keyUpMap.put(KeyEvent.VK_F3, "Keyboard_KeyUp_F3");
		keyUpMap.put(KeyEvent.VK_F4, "Keyboard_KeyUp_F4");
		keyUpMap.put(KeyEvent.VK_F5, "Keyboard_KeyUp_F5");
		keyUpMap.put(KeyEvent.VK_F6, "Keyboard_KeyUp_F6");
		keyUpMap.put(KeyEvent.VK_F7, "Keyboard_KeyUp_F7");
		keyUpMap.put(KeyEvent.VK_F8, "Keyboard_KeyUp_F8");
		keyUpMap.put(KeyEvent.VK_F9, "Keyboard_KeyUp_F9");
		keyUpMap.put(KeyEvent.VK_F10, "Keyboard_KeyUp_F10");
		keyUpMap.put(KeyEvent.VK_F11, "Keyboard_KeyUp_F11");
		keyUpMap.put(KeyEvent.VK_F12, "Keyboard_KeyUp_F12");

	}
	
	public String getKeyPressed(int key){
		String thirdIdentifier = "" + ((char)key);
		if(key == KeyEvent.VK_SPACE)
			thirdIdentifier = "Spacebar";
		return "Keyboard_" + thirdIdentifier + "_KeyDown";
	}
	
	public String getKeyReleased(int key){
		String thirdIdentifier = "" + (char)key;
		if(key == KeyEvent.VK_SPACE)
			thirdIdentifier = "Spacebar";
		return "Keyboard_" + thirdIdentifier + "_KeyUp";
	}
	
}
