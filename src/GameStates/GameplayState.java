package GameStates;

import ChestiiRandom.ChestiiStatice;
import Dungeon.Dungeon;
import GUI.Elements.StatsBar;
import GUI.Elements.Text;
import GUI.Elements.UI_Elemenent;
import Game.Window;
import GameObject.GameObject;
import GameObject.ID;
import Player.Player;
import SQLite.SQLite;
import SpriteSheet.MapSheet;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class GameplayState extends GameState{

    private Text scoreText;

    public GameplayState(boolean incercCevaCiudatCaSaNuChemCSuperConstructor){
        System.out.println("Am pacalit JVM");
    }

    public GameplayState() {
        init();
        if(toLoad) {
            try {
                SQLite.getInstance().loadGame(new MapSheet("dungeon"));
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            score = 0;
            foundTreasure = false;
            Player.setInstance(70,300,2.5f, ID.Player,"adventurer1");
            Player.getInstance().addStatsBar((StatsBar) GameUI.get(0));

            try {
                //map_sheet = new MapSheet("dungeon");
                Dungeon.newInstance(new MapSheet("dungeon"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init() {
        for(UI_Elemenent ui : GameUI)
            ui.setVisible(true);
        scoreText = (Text) GameUI.get(2);
        menuBack.setVisible(true);
    }

    @Override
    public void render(Graphics g) {

        g.clearRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());
        g.setColor(ChestiiStatice.visiniu);
        g.fillRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());

        if(Dungeon.getInstance().isOutside())
            menuBack.render(g);

        Dungeon.getInstance().getRoom().render(g);
        Player.getInstance().render(g);

        g.setFont(g.getFont().deriveFont((float)scoreText.getSize()));
        FontMetrics fm = g.getFontMetrics();
        scoreText.setPosition(Window.getInstance().getWidth() - fm.stringWidth("Score: " + score) - 50, scoreText.getRect().y);
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
            Player.getInstance().addStatsBar((StatsBar) GameUI.get(0));
        }
        Player.getInstance().tick();

        Dungeon.getInstance().getRoom().tick();

        scoreText.setText("Score: " + score);
    }

    @Override
    public void clearUI() {
        menuBack.setVisible(false);
        for(UI_Elemenent ui : GameUI)
            ui.setVisible(false);
    }

}