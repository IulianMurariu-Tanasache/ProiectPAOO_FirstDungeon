package GameObject;

import ChestiiRandom.MutableBoolean;
import Game.Window;
import GameStates.GameState;
import SpriteSheet.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Comoara extends GameObject{

    private Animation animation;
    private MutableBoolean inAnimation;
    private boolean entered = false;
    private int startX,startY;
    private float startScale;

    public Comoara(int x, int y, float scale, ID i) {
        super(x, y, scale, i);
        startX = x;
        startY = y;
        startScale = scale;
        inAnimation = new MutableBoolean();
        inAnimation.val = false;
        try {
            animation = new Animation("comoara","open",22);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        //entered = true;
        if(!entered)
        {
            entered = true;
            inAnimation.val = true;
            scale = 5;
            x = (int) (Window.getInstance().getWidth() / 2 - 32 * scale);
            y = (int) (Window.getInstance().getHeight() / 2 - 35 * scale);
        }
        else if(entered && !inAnimation.val){
            x = startX;
            y = startY;
            scale = startScale;
            GameState.setFoundTreasure(true);
        }
    }


    @Override
    public void mapCollision() {

    }

    @Override
    public void render(Graphics g) {
        BufferedImage img;
        if(inAnimation.val)
        {
            img = animation.getCurrentFrame(inAnimation);
            g.setColor(new Color(40,40,40,100));
            g.fillRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());
        }
        else
            img = animation.getImage(3);
        g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, (int)(64 * scale), (int)(64 * scale));
    }
}
