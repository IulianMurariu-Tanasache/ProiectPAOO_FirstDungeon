package Input;

import GUI.Elements.Interactable;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseListener implements java.awt.event.MouseListener {

    private ArrayList<Interactable> uiElements;

    public MouseListener(){
        uiElements = new ArrayList<>();
    }

    public void add(Interactable i) {
        uiElements.add(i);
    }

    public void remove(Interactable e) {
        uiElements.remove(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(Interactable el : uiElements)
            el.mouseClicked(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(Interactable el : uiElements)
            el.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
