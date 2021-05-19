package Enemies;

import Dungeon.Dungeon;
import Enemies.States.EnemyState;
import GameObject.ID;
import Player.Player;
import SpriteSheet.Animation;

import java.awt.*;

/*! \class Ciuperca
    \brief Clasa care extinde Enemy. Reprezinta inamicul Ciuperca.
 */
public class Ciuperca extends Enemy{

    public Ciuperca(int x, int y, float scale, ID i) {
        super(x, y, scale, i);
        img = null;
        currentAnimation = new Animation(ciuperca_anim[animations_enemy_enum.walk]);
        attackTimer = 0;
        height = (int) (40 * scale);
        width = (int) (40 * scale);
    }

    @Override
    public void tick() {
        Player player = Player.getInstance();
        playerBounds = player.getBounds();
        playerBounds.x -= EnemyState.getDistanceToAttack() / 2;
        playerBounds.width += EnemyState.getDistanceToAttack() / 2;
        if(playerBounds.intersects(getBounds()) && player.isAttacking())
        {
            health--;
            player.setAttacking(false);
        }
        player.setGotHit(hitPlayer);
        hitPlayer = false;

        if(health <= 0) {
            Dungeon.getInstance().getRoom().remove(this);
            return;
        }
        state = state.update(playerBounds);

        mapCollision();

        //this.x += speedX;

        if(attackTimer > 0)
            attackTimer--;
    }


    @Override
    public void render(Graphics g) {
        if(currentAnimation != null)
            img = currentAnimation.getCurrentFrame(inAnimation);
        else
            img = ciuperca_anim[0].getImage(0);
        if(!facing)
            g.drawImage(img, (int) (x + 10 * scale + img.getWidth() * scale), y, (int)(img.getWidth() * scale * -1), (int)(img.getHeight() * scale), null);
        else
            g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
        //g.drawRect((int)(x +10 * scale), (int)(y + 10 * scale), width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)(x + 10 * scale), (int)(y + 10 * scale), width, height);
    }
}
