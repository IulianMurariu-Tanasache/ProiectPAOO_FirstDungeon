package GameObject;

import Dungeon.Dungeon;
import Player.Player;
import SoundTrack.SoundManager;
import SpriteSheet.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Inima extends GameObject {

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
    public void tick() {
        Player player = Player.getInstance();
        Rectangle playerBounds = player.getBounds();
        if(playerBounds.intersects(getBounds()) && Player.getInstance().getHealth() < 6) {
            Dungeon.getInstance().getRoom().remove(this);
            player.setHealth(player.getHealth() + 2);
            SoundManager.getSoundManager().play("heart.wav");
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
    }

    @Override
    public void mapCollision() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,(int)(img.getWidth() * scale), (int)(img.getHeight() * scale));
    }
}
