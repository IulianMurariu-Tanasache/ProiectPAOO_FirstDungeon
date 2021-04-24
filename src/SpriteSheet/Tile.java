package SpriteSheet;

import java.awt.image.BufferedImage;

public class Tile {
    private String name;
    private boolean solid = false;

    public String getName() {
        return name;
    }

    public BufferedImage getImg() {
        return img;
    }

    private BufferedImage img;

    public Tile(String n, BufferedImage img, int solid)
    {
        this.name = n;
        this.img = img;
        if(solid == 1)
            this.solid = true;
    }


    public boolean isSolid() {
        return solid;
    }
}
