package GameStates;

import GUI.Elements.UI_Elemenent;
import Game.Window;

import java.awt.*;

/*! \class MenuState
    \brief Clasa care extinde GameState. Reprezinta meniul principal al jocului.
 */
public class MenuState extends GameState{

    @Override
    public void init() {
        for(UI_Elemenent ui : MenuUI)
            ui.setVisible(true);
    }

    @Override
    public void render(Graphics g) {

        g.clearRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());
        g.setColor(Color.PINK);
        g.fillRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());
        for(UI_Elemenent ui : MenuUI)
            ui.render(g);
    }

    @Override
    public void tick() {

    }

    @Override
    public void clearUI() {
        for(UI_Elemenent ui : MenuUI)
            ui.setVisible(false);
    }
}
