package GameStates;

import Dungeon.Dungeon;
import GUI.Elements.UI_Elemenent;
import GameObject.GameObject;
import Player.Player;

import java.awt.*;


public class GamePausedState extends  GameState{

    @Override
    public void init() {
        GameUI.get(0).setVisible(true);
    }


    @Override
    public void render(Graphics g, double elapsed) {

        g.clearRect(0,0,1024,576);
        g.setColor(new Color(75,44,54));
        g.fillRect(0,0,1024, 576);

        Dungeon.getInstance().getRoom().render(g);
        Player.getInstance().render(g,0);

        for(GameObject obj : Dungeon.getInstance().getRoom().getObjects())
            obj.render(g,0);

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
