package GameStates;

import Game.Window;

import java.awt.*;

public class DiffSelectState extends GameState{

    @Override
    public void init() {
        menuBack.setVisible(true);
        diffPanel.setVisible(true);
    }

    @Override
    public void render(Graphics g) {

        g.clearRect(0,0, Game.Window.getInstance().getWidth(), Game.Window.getInstance().getHeight());
        g.setColor(Color.PINK);
        g.fillRect(0,0, Game.Window.getInstance().getWidth(), Window.getInstance().getHeight());
        menuBack.render(g);
        diffPanel.render(g);
    }

    @Override
    public void tick() {

    }

    @Override
    public void clearUI() {
        menuBack.setVisible(false);
        diffPanel.setVisible(false);
    }
}
