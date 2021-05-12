package GUI.Elements;

import SpriteSheet.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Imagine extends UI_Elemenent{

    private BufferedImage img = null;

    public Imagine(int x, int y, SpriteSheet sheet) {
        img = sheet.getImage();
        rect = new Rectangle(x,y,img.getWidth(),img.getHeight());
        visible = false;
    }

    public Imagine(int x, int y, int w, int h, SpriteSheet sheet) {
        img = sheet.getImage();
        rect = new Rectangle(x,y,w,h);
        visible = false;
    }

    @Override
    public void render(Graphics g) {
        if(!visible)
            return;

        g.drawImage(img, rect.x, rect.y,rect.width,rect.height,null);
    }
}
