package GUI.Elements;

import Game.Window;
import SpriteSheet.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class MenuParallax
    \brief Clasa care extinde UI_Element.
 */
public class MenuParallax extends UI_Elemenent{

    private BufferedImage[] back1;
    private BufferedImage[] back2;
    private BufferedImage back3;
    private Rectangle[] rect1;
    private Rectangle[] rect2;

    public MenuParallax(String folder) {
        visible = false;
        back1 = new BufferedImage[2];
        back2 = new BufferedImage[2];
        rect1 = new Rectangle[2];
        rect2 = new Rectangle[2];
        back1[0] = ImageLoader.loadImage(folder + "/back1.png");
        back1[1] = ImageLoader.loadImage(folder + "/back1.png");
        back2[0] = ImageLoader.loadImage(folder + "/back2.png");
        back2[1] = ImageLoader.loadImage(folder + "/back2.png");
        back3 = ImageLoader.loadImage(folder + "/back3.png");
        rect1[0] = new Rectangle(0,0, Window.getInstance().getWidth(), Window.getInstance().getHeight());
        rect1[1] = new Rectangle(-Window.getInstance().getWidth(),0, Window.getInstance().getWidth(), Window.getInstance().getHeight());
        rect2[0] = new Rectangle(0,0, Window.getInstance().getWidth(), Window.getInstance().getHeight());
        rect2[1] = new Rectangle(-Window.getInstance().getWidth(),0, Window.getInstance().getWidth(), Window.getInstance().getHeight());
    }

    @Override
    public void render(Graphics g) {
        if(!visible)
            return;
        for(Rectangle layer1 : rect1)
            layer1.x += 1;
        for(Rectangle layer2 : rect2)
            layer2.x += 2;

        if(rect1[0].x >= Window.getInstance().getWidth())
        {
            rect1[0] = new Rectangle(rect1[1].x,rect1[1].y,rect1[1].width,rect1[1].height);
            rect1[1] = new Rectangle(rect1[0].x - Window.getInstance().getWidth(),rect1[0].y,rect1[0].width,rect1[0].height);
        }

        if(rect2[0].x >= Window.getInstance().getWidth())
        {
            rect2[0] = new Rectangle(rect2[1].x,rect2[1].y,rect2[1].width,rect2[1].height);
            rect2[1] = new Rectangle(rect2[0].x - Window.getInstance().getWidth(),rect2[0].y,rect2[0].width,rect2[0].height);
        }

        for(int i = 0; i < 2; ++i)
            g.drawImage(back1[i], rect1[i].x, rect1[i].y,rect1[i].width,rect1[i].height,null);

        for(int i = 0; i < 2; ++i)
            g.drawImage(back2[i], rect2[i].x, rect2[i].y,rect2[i].width,rect2[i].height,null);

        g.drawImage(back3,0,-32, Window.getInstance().getWidth(), Window.getInstance().getHeight(), null);

    }
}
