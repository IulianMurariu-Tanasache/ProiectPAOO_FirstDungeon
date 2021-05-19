package GUI.Elements;

/*! \class Interactable
    \brief Interfata pentru toate elementele de UI pe care se poate da click. Primeste un MouseEvent de la MouseListener. E practic un Observer.
 */
import java.awt.event.MouseEvent;

public interface Interactable {

    public boolean isMouseOn(MouseEvent e);
    public void mouseClicked(MouseEvent e);
    public void mouseReleased(MouseEvent e);
}
