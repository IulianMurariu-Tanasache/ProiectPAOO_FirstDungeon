package GameStates;

import Dungeon.Dungeon;
import GUI.Elements.StatsBar;
import GUI.Elements.Text;
import GUI.Elements.UI_Elemenent;
import Game.Window;
import GameObject.GameObject;
import GameObject.ID;
import Player.*;
import SoundTrack.Music;
import SpriteSheet.MapSheet;

import java.awt.*;
import java.io.IOException;

public class GameplayState extends GameState{

    private Text scoreText;

    public GameplayState() {
        init();
        foundTreasure = false;
        Player.setInstance(70,300,2.5f, ID.Player,"adventurer1");
        Player.getInstance().addStatsBar((StatsBar) GameUI.get(2));

        try {
            //map_sheet = new MapSheet("dungeon");
            Dungeon.newInstance(new MapSheet("dungeon"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        score = 0;
        if((prev instanceof SettingsState) || (prev instanceof MenuState))
            Music.changedState = true;
        for(int i = 2; i < GameUI.size(); ++i)
            GameUI.get(i).setVisible(true);
        scoreText = (Text) GameUI.get(4);
    }

    @Override
    public void render(Graphics g) {

        g.clearRect(0,0,1024,576);
        g.setColor(new Color(75,44,54));
        g.fillRect(0,0,1024, 576);

        Dungeon.getInstance().getRoom().render(g);
        Player.getInstance().render(g);

        g.setFont(g.getFont().deriveFont((float)scoreText.getSize()));
        FontMetrics fm = g.getFontMetrics();
        scoreText.setPosition(Window.getInstance().getWidth() - fm.stringWidth("Score: " + score) - 20, scoreText.getRect().y);
        g.setFont(g.getFont());

        for(GameObject obj : Dungeon.getInstance().getRoom().getObjects())
            obj.render(g);

        for(UI_Elemenent ui : GameUI)
            ui.render(g);

    }

    @Override
    public void tick() {
        if(foundTreasure && !Player.getInstance().isArmed()) {
            Rectangle playerBounds = Player.getInstance().getBounds();
            Player.setInstance(playerBounds.x, playerBounds.y, 2.5f, ID.Player, "adventurer2");
            Player.getInstance().addStatsBar((StatsBar) GameUI.get(2));
        }
        Player.getInstance().tick();

        Dungeon.getInstance().getRoom().tick();

        scoreText.setText("Score: " + score);
    }

    @Override
    public void clearUI() {
        //GameUI.remove(GameUI.size() - 1);
        for(UI_Elemenent ui : GameUI)
            ui.setVisible(false);
    }

}