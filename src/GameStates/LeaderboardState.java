package GameStates;

import GUI.Elements.UI_Elemenent;
import Game.Window;

import java.awt.*;

/*! \class LeaderboardState
    \brief Clasa care extinde GameState. Reprezinta meniul care afiseaza topul scorurilor.
 */
public class LeaderboardState extends GameState{

    @Override
    public void init() {
        for(UI_Elemenent ui : LeaderboardUI)
            ui.setVisible(true);
    }

    @Override
    public void render(Graphics g) {
        g.clearRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());
        g.setColor(Color.PINK);
        g.fillRect(0,0, Game.Window.getInstance().getWidth(), Window.getInstance().getHeight());
        for(UI_Elemenent ui : LeaderboardUI)
            ui.render(g);
    }

    @Override
    public void tick() {

    }

    @Override
    public void clearUI() {
        for(UI_Elemenent ui : LeaderboardUI)
            ui.setVisible(false);
    }
}
