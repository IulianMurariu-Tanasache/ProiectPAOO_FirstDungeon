package Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private final boolean[] keys;

    public KeyInput()
    {
        keys = new boolean[KeyEnum.values().length];
    }

    public boolean getKey(KeyEnum key)
    {
        return keys[key.ordinal()];
    }

    public void setKey(KeyEnum key, boolean val) {
        keys[key.ordinal()] = val;
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) keys[KeyEnum.W.ordinal()] = true;
        if(key == KeyEvent.VK_A) keys[KeyEnum.A.ordinal()] = true;
        if(key == KeyEvent.VK_D) keys[KeyEnum.D.ordinal()] = true;
        if(key == KeyEvent.VK_SPACE) keys[KeyEnum.SPACE.ordinal()] = true;
        if(key == KeyEvent.VK_SHIFT) keys[KeyEnum.SHIFT.ordinal()] = true;
        if(key == KeyEvent.VK_CONTROL) keys[KeyEnum.CTRL.ordinal()] = !keys[KeyEnum.CTRL.ordinal()];
        if(key == KeyEvent.VK_L) keys[KeyEnum.L.ordinal()] = true;
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) keys[KeyEnum.W.ordinal()] = false;
        if(key == KeyEvent.VK_A) keys[KeyEnum.A.ordinal()] = false;
        if(key == KeyEvent.VK_D) keys[KeyEnum.D.ordinal()] = false;
        if(key == KeyEvent.VK_SPACE) keys[KeyEnum.SPACE.ordinal()] = false;
        if(key == KeyEvent.VK_SHIFT) keys[KeyEnum.SHIFT.ordinal()] = false;
        //if(key == KeyEvent.VK_CONTROL) keys[KeyEnum.CTRL.ordinal()] = false;
        if(key == KeyEvent.VK_L) keys[KeyEnum.L.ordinal()] = false;
    }

}
