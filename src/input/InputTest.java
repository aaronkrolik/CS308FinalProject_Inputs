package input;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import junit.framework.TestCase;

/**
 * A testing class which implments JUnit testing and manufactured
 * AWTEvent dispatching to make sure that the API is doing what
 * it is documented to be doing.
 * @author Gavin Ovsak
 *
 */
@InputClassTarget
public class InputTest extends TestCase {
    Input input1;
    JComponent myCanvas;
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Race!";
    String lastGameBehavior = "";
    ArrayList<String> inputBehaviorHistory = new ArrayList<String>();
    Map<Integer,String> keyMap = new HashMap<Integer,String>();
    KeyEvent testKeyEvent;
    MouseEvent testMouseEvent;
    
    /**
     * Constructs an input object which is overridden to report back what input events are being activated.
     */
    public void setUp(){
        JFrame frame = new JFrame(TITLE);
        myCanvas = new JComponent(){
            private static final long serialVersionUID = 1L;
        };
        frame.getContentPane().add(myCanvas, BorderLayout.CENTER);
        frame.setVisible(true);
        
        input1 = new Input("examples/Game1MappingsMode1", myCanvas) {
            @Override
            protected void execute(String gameBehavior, AlertObject in) {
                super.execute(gameBehavior, in);
                lastGameBehavior = gameBehavior;
            }
            
            @Override
            protected void actionNotification(String inputBehavior, AlertObject object) {
                super.actionNotification(inputBehavior, object);
                inputBehaviorHistory.add(inputBehavior);
            }
        };
        input1.addListenerTo(this);

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
    
    /**
     * A convenience method to dispatch events and catch exceptions.
     * @param event
     * @param comp
     */
    private static void dispatchEvent(final AWTEvent event, final Component comp) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        comp.dispatchEvent(event);
                    }
                });
            } catch (InterruptedException e) {
            } catch (InvocationTargetException e) {
            }
        }
    }
    
    /**
     * A test which goes through all Keyboard keys that are supported by the API and verifies their behavior calling. 
     */
    public void testIndividualKeys() {
        for (Integer key : keyMap.keySet()) {
            char keyChar = (keyMap.get(key).length() == 1)?(char)((int)key):KeyEvent.CHAR_UNDEFINED;
            testKeyEvent = new KeyEvent(myCanvas, KeyEvent.KEY_PRESSED,
                                             System.currentTimeMillis(), 0, key, keyChar);

            dispatchEvent(testKeyEvent, myCanvas);
            testKeyEvent = new KeyEvent(myCanvas, KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(), 0, key, keyChar);

            dispatchEvent(testKeyEvent, myCanvas);
            assertTrue(inputBehaviorHistory.contains("Keyboard_Any_Down"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_Any_Up"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(key)+"_KeyDown"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(key)+"_Down"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(key)+"_KeyUp"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(key)+"_Up"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(key)+"_ShortClick"));
            inputBehaviorHistory.clear();
        }
    }
    
    /**
     * A test which tries many combinations of keys being held down at the same time over the entire list of possible keys.
     */
    public void testComboKeys() {
    	
    	KeyEvent releaseLast = null;
    	
        for(Integer key : keyMap.keySet()) {
            char keyChar = (keyMap.get(key).length() == 1)? (char)((int)key):KeyEvent.CHAR_UNDEFINED;
            testKeyEvent = new KeyEvent(myCanvas, KeyEvent.KEY_PRESSED,
                                             System.currentTimeMillis(), 0, key, keyChar);
            dispatchEvent(testKeyEvent, myCanvas);

            assertTrue(inputBehaviorHistory.contains("Keyboard_Any_Down"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(key)+"_Down"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(key)+"_KeyDown"));
        	if(releaseLast != null) {
        		dispatchEvent(releaseLast, myCanvas);
                assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(key)+keyMap.get(releaseLast.getKeyCode())+"_Down"));
                assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(releaseLast.getKeyCode())+keyMap.get(key)+"_Down"));
                assertTrue(inputBehaviorHistory.contains("Keyboard_Any_Up"));
                assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(releaseLast.getKeyCode())+"_Up"));
                assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(releaseLast.getKeyCode())+"_KeyUp"));
                assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(releaseLast.getKeyCode())+"_ShortClick"));
        	}
            releaseLast = new KeyEvent(myCanvas, KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(), 0, key, keyChar);
            inputBehaviorHistory.clear();
        }
        
        if(releaseLast != null) {
    		dispatchEvent(releaseLast, myCanvas);
            assertTrue(inputBehaviorHistory.contains("Keyboard_Any_Up"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(releaseLast.getKeyCode())+"_Up"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(releaseLast.getKeyCode())+"_KeyUp"));
            assertTrue(inputBehaviorHistory.contains("Keyboard_"+keyMap.get(releaseLast.getKeyCode())+"_ShortClick"));
            inputBehaviorHistory.clear();
        }
    }
    
    /**
     * A test which verifies the correct input behavior when any mouse button is used.
     */
    public void testMouseButtons() {
    	Map<Integer, String> mouseNameMap;
    	mouseNameMap = new HashMap<Integer, String>();
		mouseNameMap.put(InputEvent.BUTTON1_MASK, "Left");
		mouseNameMap.put(InputEvent.BUTTON2_MASK, "Center");
		mouseNameMap.put(InputEvent.BUTTON3_MASK, "Right");
    	
    	for(Integer key : mouseNameMap.keySet()) {
    		int x = 10;
    		int y = 10;
            testMouseEvent = new MouseEvent(myCanvas, MouseEvent.MOUSE_PRESSED,
                                            System.currentTimeMillis(), key, x, y, 1, false);

            dispatchEvent(testMouseEvent, myCanvas);
            testMouseEvent = new MouseEvent(myCanvas, MouseEvent.MOUSE_RELEASED,
                    System.currentTimeMillis(), key, x, y, 1, false);

            dispatchEvent(testMouseEvent, myCanvas);
            assertTrue(inputBehaviorHistory.contains("Mouse_" + mouseNameMap.get(key)+"_Down"));
            assertTrue(inputBehaviorHistory.contains("Mouse_" + mouseNameMap.get(key)+"_Up"));
            assertTrue(inputBehaviorHistory.contains("Mouse_" + mouseNameMap.get(key)+"_Click"));
            assertTrue(inputBehaviorHistory.contains("Mouse_" + mouseNameMap.get(key)+"_ShortClick"));
            
			testMouseEvent = new MouseEvent(myCanvas, MouseEvent.MOUSE_PRESSED,
			        System.currentTimeMillis(), key, x, y, 1, false);
			
			dispatchEvent(testMouseEvent, myCanvas);
			testMouseEvent = new MouseEvent(myCanvas, MouseEvent.MOUSE_RELEASED,
			System.currentTimeMillis(), key, x, y, 1, false);
			
			dispatchEvent(testMouseEvent, myCanvas);
            assertTrue(inputBehaviorHistory.contains("Mouse_" + mouseNameMap.get(key)+"_DoubleClick"));

            inputBehaviorHistory.clear();
        }
    }
     
}
