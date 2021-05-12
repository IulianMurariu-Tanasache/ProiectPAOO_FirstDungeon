package GameStates;

import java.awt.*;

import GUI.Elements.UI_Elemenent;
import Game.Window;
import SoundTrack.Music;

import javax.swing.*;

public class MenuState extends GameState{

    @Override
    public void init() {
        if((!(prev instanceof SettingsState)) && (!(prev instanceof MenuState)))
            Music.changedState = true;
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
