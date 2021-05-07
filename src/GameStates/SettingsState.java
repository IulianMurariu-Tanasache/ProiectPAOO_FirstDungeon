package GameStates;

import GUI.Elements.UI_Elemenent;
import Game.Window;

import java.awt.*;

public class SettingsState extends GameState{

/*    public SettingsState() {
        init();
    }*/

    @Override
    public void init() {
        for(UI_Elemenent ui : SettingsUI)
            ui.setVisible(true);
    }

    @Override
    public void render(Graphics g, double elapsed) {
        g.clearRect(0,0,1024,576);
        g.setColor(Color.PINK);
        g.fillRect(0,0, Game.Window.getInstance().getWidth(), Window.getInstance().getHeight());
        for(UI_Elemenent ui : SettingsUI)
            ui.render(g);
    }

    @Override
    public void tick() {

    }

    @Override
    public void clearUI() {
        for(UI_Elemenent ui : SettingsUI)
            ui.setVisible(false);
    }
}
