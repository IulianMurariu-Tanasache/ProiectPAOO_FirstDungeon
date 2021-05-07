package Enemies;

import GameObject.ID;
import SpriteSheet.Animation;

import java.awt.*;

public class Slime extends Enemy{

    public Slime(int x, int y, float scale, ID i) {
        super(x, y, scale, i);
        img = null;
        currentAnimation = new Animation(slime_anim[animations_enemy_enum.walk]);
        attackTimer = 0;
        height = (int) (16 * scale);
        width = (int) (26 * scale);
    }

    @Override
    public void render(Graphics g, double elapsed) {
        if(currentAnimation != null)
            img = currentAnimation.getCurrentFrame(elapsed, inAnimation);
        else
            img = slime_anim[0].getImage(0);
        if(facing)
            g.drawImage(img, x + (int)(img.getWidth() * scale), y, (int)(img.getWidth() * scale * -1), (int)(img.getHeight() * scale), null);
        else
            g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
        g.drawRect(x, y , width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, (int)(y + 5 * scale), width, height);
    }
}