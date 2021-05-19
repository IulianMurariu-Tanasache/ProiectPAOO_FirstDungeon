package Enemies;

import GameObject.ID;
import SpriteSheet.Animation;

import java.awt.*;

/*! \class Goblin
    \brief Clasa care extinde Enemy. Reprezinta inamicul Goblin.
 */
public class Goblin extends Enemy{

    public Goblin(int x, int y, float scale, ID i) {
        super(x, y, scale, i);
        img = null;
        currentAnimation = new Animation(goblin_anim[animations_enemy_enum.walk]);
        height = (int) (36 * scale);
        width = (int) (38 * scale);
    }

    @Override
    public void render(Graphics g) {
        if(currentAnimation != null)
            img = currentAnimation.getCurrentFrame(inAnimation);
        else
            img = goblin_anim[0].getImage(0);
        if(!facing)
            g.drawImage(img, x + (int)(img.getWidth() * scale), y, (int)(img.getWidth() * scale * -1), (int)(img.getHeight() * scale), null);
        else
            g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
        //g.drawRect(x, y , width, height);
    }
}
