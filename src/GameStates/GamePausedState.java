package GameStates;

import Dungeon.Dungeon;
import GUI.Elements.UI_Elemenent;
import Game.Window;
import GameObject.GameObject;
import Player.Player;

import java.awt.*;


public class GamePausedState extends GameState{

    @Override
    public void init() {
        GameUI.get(0).setVisible(true);
    }


    @Override
    public void render(Graphics g) {

        g.clearRect(0,0, Window.getInstance().getWidth(), Window.getInstance().getHeight());
        g.setColor(new Color(75,44,54));
        g.fillRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());

        Dungeon.getInstance().getRoom().render(g);
        Player.getInstance().render(g);

        for(GameObject obj : Dungeon.getInstance().getRoom().getObjects())
            obj.render(g);

        for(UI_Elemenent ui : GameUI)
            ui.render(g);

    }

    @Override
    public void tick() {
    }

    @Override
    public void clearUI() {
        GameUI.get(0).setVisible(false);
    }
}
