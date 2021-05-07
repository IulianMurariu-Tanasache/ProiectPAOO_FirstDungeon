package SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private String name;
    private boolean solid = false;
    private Rectangle bounds;

    public String getName() {
        return name;
    }

    public BufferedImage getImg() {
        return img;
    }

    private BufferedImage img;

    public Tile(String n, BufferedImage img, int solid) {
        this.name = n;
        this.img = img;
        if (solid == 1) {
            this.solid = true;
        }
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isSolid() {
        return solid;
    }

    public Rectangle getBounds() {
        if(!solid)
            return null;
        return bounds;
    }
}
