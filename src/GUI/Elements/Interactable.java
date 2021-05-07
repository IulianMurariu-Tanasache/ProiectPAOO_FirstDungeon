package GUI.Elements;


import java.awt.event.MouseEvent;

public interface Interactable {

    public boolean isMouseOn(MouseEvent e);
    public void mouseClicked(MouseEvent e);
    public void mouseReleased(MouseEvent e);
}
