package GameStates;

import GUI.Elements.Text;
import GUI.Elements.UI_Elemenent;
import Game.Window;

import java.awt.*;

public class LeaderboardState extends GameState{

/*    public SettingsState() {
        init();
    }*/

    @Override
    public void init() {
        for(UI_Elemenent ui : LeaderboardUI)
            ui.setVisible(true);
        //((Text)(LeaderboardUI.get(2))).setText(String.valueOf(score));
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
