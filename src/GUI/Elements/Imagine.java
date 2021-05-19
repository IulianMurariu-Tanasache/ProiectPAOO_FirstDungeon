package GUI.Elements;

import SpriteSheet.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class Imagine
    \brief Clasa care extined UI_Element. Reprezitna o imagine statica din UI.
 */
public class Imagine extends UI_Elemenent{

    private BufferedImage img = null;

    public Imagine(int x, int y,String path) {
        img = ImageLoader.loadImage(path);
        rect = new Rectangle(x,y,img.getWidth(),img.getHeight());
        visible = false;
    }

    public Imagine(int x, int y, int w, int h, String path) {
        img = ImageLoader.loadImage(path);
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
