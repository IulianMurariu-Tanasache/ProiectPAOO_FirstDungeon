package GameObject;

import Dungeon.Dungeon;
import Game.Window;
import Observer.Observer;
import Player.Player;
import SpriteSheet.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Inima extends GameObject implements Observer {

    private BufferedImage img;

    public Inima(int x, int y, float scale, ID i) {
        super(x, y, scale, i);
        try {
            SpriteSheet sheet = new SpriteSheet("inimi","inimi");
            img = sheet.grabImages("inimi",1)[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateObserver(Player player) {
        Rectangle playerBounds = player.getBounds();
        if(playerBounds.intersects(getBounds())) {
            Dungeon.getInstance().getRoom().remove(this);
            player.setHealth(player.getHealth() + 2);
        }
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g, double elapsed) {
        g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
    }

    @Override
    public void mapCollision() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,img.getWidth(),img.getHeight());
    }
}
