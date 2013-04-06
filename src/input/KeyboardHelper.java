package input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardHelper {
	private static Map<Integer,String> keyMap;
	
	public static void initialize(){
		keyMap = new HashMap<Integer,String>();
		keyMap.put(KeyEvent.VK_0, "0");
		keyMap.put(KeyEvent.VK_1, "1");
		keyMap.put(KeyEvent.VK_2, "2");
		keyMap.put(KeyEvent.VK_3, "3");
		keyMap.put(KeyEvent.VK_4, "4");
		keyMap.put(KeyEvent.VK_5, "5");
		keyMap.put(KeyEvent.VK_6, "6");
		keyMap.put(KeyEvent.VK_7, "7");
		keyMap.put(KeyEvent.VK_8, "8");
		keyMap.put(KeyEvent.VK_9, "9");
		keyMap.put(KeyEvent.VK_A, "A");
		keyMap.put(KeyEvent.VK_B, "B");
		keyMap.put(KeyEvent.VK_C, "C");
		keyMap.put(KeyEvent.VK_D, "D");
		keyMap.put(KeyEvent.VK_E, "E");
		keyMap.put(KeyEvent.VK_F, "F");
		keyMap.put(KeyEvent.VK_G, "G");
		keyMap.put(KeyEvent.VK_H, "H");
		keyMap.put(KeyEvent.VK_I, "I");
		keyMap.put(KeyEvent.VK_J, "J");
		keyMap.put(KeyEvent.VK_K, "K");
		keyMap.put(KeyEvent.VK_L, "L");
		keyMap.put(KeyEvent.VK_M, "M");
		keyMap.put(KeyEvent.VK_N, "N");
		keyMap.put(KeyEvent.VK_O, "O");
		keyMap.put(KeyEvent.VK_P, "P");
		keyMap.put(KeyEvent.VK_Q, "Q");
		keyMap.put(KeyEvent.VK_R, "R");
		keyMap.put(KeyEvent.VK_S, "S");
		keyMap.put(KeyEvent.VK_T, "T");
		keyMap.put(KeyEvent.VK_U, "U");
		keyMap.put(KeyEvent.VK_V, "V");
		keyMap.put(KeyEvent.VK_W, "W");
		keyMap.put(KeyEvent.VK_X, "X");
		keyMap.put(KeyEvent.VK_Y, "Y");
		keyMap.put(KeyEvent.VK_Z, "Z");
		keyMap.put(KeyEvent.VK_LEFT, "Left");
		keyMap.put(KeyEvent.VK_RIGHT, "Right");
		keyMap.put(KeyEvent.VK_UP, "Up");
		keyMap.put(KeyEvent.VK_DOWN, "Down");
		keyMap.put(KeyEvent.VK_ENTER, "Enter");
		keyMap.put(KeyEvent.VK_DELETE, "Delete");
		keyMap.put(KeyEvent.VK_CONTROL, "Control");
		keyMap.put(KeyEvent.VK_SHIFT, "Shift");
		keyMap.put(KeyEvent.VK_SPACE, "Spacebar");
		keyMap.put(KeyEvent.VK_TAB, "Tab");
		keyMap.put(KeyEvent.VK_ADD, "Add");
		keyMap.put(KeyEvent.VK_MINUS, "Minus");
		keyMap.put(KeyEvent.VK_F1, "F1");
		keyMap.put(KeyEvent.VK_F2, "F2");
		keyMap.put(KeyEvent.VK_F3, "F3");
		keyMap.put(KeyEvent.VK_F4, "F4");
		keyMap.put(KeyEvent.VK_F5, "F5");
		keyMap.put(KeyEvent.VK_F6, "F6");
		keyMap.put(KeyEvent.VK_F7, "F7");
		keyMap.put(KeyEvent.VK_F8, "F8");
		keyMap.put(KeyEvent.VK_F9, "F9");
		keyMap.put(KeyEvent.VK_F10, "F10");
		keyMap.put(KeyEvent.VK_F11, "F11");
		keyMap.put(KeyEvent.VK_F12, "F12");
	}
	
	public static String getKeyName(int key) {
		if(keyMap == null)
			initialize();
		if(keyMap.containsKey(key))
			return keyMap.get(key);
		return null;
	}	
}
