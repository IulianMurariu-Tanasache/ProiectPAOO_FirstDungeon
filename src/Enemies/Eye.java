package Enemies;

import ChestiiRandom.ChestiiStatice;
import Dungeon.Dungeon;
import Game.Window;
import GameObject.ID;
import Room.Room;
import SpriteSheet.Animation;

import java.awt.*;

/*! \class Eye
    \brief Clasa care extinde Enemy. Reprezinta inamicul Ochi de Ceapa.
 */
public class Eye extends Enemy{

    public Eye(int x, int y, float scale, ID i) {
        super(x, y, scale, i);
        img = null;
        currentAnimation = new Animation(eye_anim[animations_enemy_enum.walk]);
        height = (int) (26 * scale);
        width = (int) (30 * scale);
    }

    @Override
    public void render(Graphics g) {
        if(currentAnimation != null)
            img = currentAnimation.getCurrentFrame(inAnimation);
        else
            img = eye_anim[0].getImage(0);
        if(!facing)
            g.drawImage(img, x + (int)(img.getWidth() * scale), y, (int)(img.getWidth() * scale * -1), (int)(img.getHeight() * scale), null);
        else
            g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
        //g.drawRect(x, y , width, height);
    }

    @Override
    public void mapCollision() {
        Dungeon dungeon = Dungeon.getInstance();
        Room r = dungeon.getRoom();
        int startX = (this.x - 5) / ChestiiStatice.tileDimension - 1;
        int startY = this.y / ChestiiStatice.tileDimension - 1;
        if(startX < 0)
            startX = 0;
        if(startY < 0)
            startY = 0;
        int endX = (this.x + 5 + width) / ChestiiStatice.tileDimension + 1;
        int endY = (this.y + height) / ChestiiStatice.tileDimension + 1;
        if(endX >= r.getDimX())
            endX = r.getDimX() - 1;
        if(endY >= r.getDimY())
            endY = r.getDimY() - 1;
        Rectangle tileBounds = null;
        Rectangle bounds = getBounds();
        Rectangle ecran = Window.getInstance().getBounds();
        boolean hit;
        nothingUnder = false;
        for(int i = startY; i <= endY; ++i)
            for(int j = startX; j <= endX; ++j) {
                hit = false;
                bounds.x = this.x + speedX;
                if (!ecran.contains(bounds)) {
                    if (bounds.x + bounds.width >= ecran.width) {
                        facing = false;
                        speedX = -speedX;
                        return;
                    } else if (bounds.x <= 0) {
                        facing = true;
                        speedX = -speedX;
                        return;
                    }
                }
                tileBounds = r.getBoundsOfTile(i, j);
                if ((tileBounds != null && bounds.intersects(tileBounds))) {
                    if (bounds.intersects(tileBounds)) {
                        speedX = 0;
                        hit = true;
                    }
                    if (hit)
                        return;
                }
            }
    }
}
