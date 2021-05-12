package Enemies.States;

import Enemies.Enemy;
import Enemies.animations_enemy_enum;
import GameObject.ID;

import java.awt.*;

public class Attack extends EnemyState{

    private boolean canAttack;

    public Attack(Enemy enemy) {
        thisEnemy = enemy;
        init();
    }

    @Override
    public EnemyState update(Rectangle playerBounds) {

        int x = thisEnemy.getX();
        if(!(((playerBounds.x >= x - distanceToAttack && playerBounds.x < x) || (playerBounds.x < x + thisEnemy.getWidth() + distanceToAttack && playerBounds.x > x)) && (Math.abs(playerBounds.y + playerBounds.height - (thisEnemy.getY() + thisEnemy.getHeight())) < height) && (playerBounds.y < thisEnemy.getY() + thisEnemy.getHeight())))
        {
            if (thisEnemy.getId() == ID.Enemy_Skeleton) {
                thisEnemy.setCurrentAnimation(Enemy.skeleton_anim[animations_enemy_enum.walk]);
            }
            if (thisEnemy.getId() == ID.Enemy_Goblin) {
                thisEnemy.setCurrentAnimation(Enemy.goblin_anim[animations_enemy_enum.walk]);
            }
            if (thisEnemy.getId() == ID.Enemy_Eye) {
                thisEnemy.setCurrentAnimation(Enemy.eye_anim[animations_enemy_enum.walk]);
            }
            if (thisEnemy.getId() == ID.Enemy_Slime) {
                thisEnemy.setCurrentAnimation(Enemy.slime_anim[animations_enemy_enum.walk]);
            }
            if (thisEnemy.getId() == ID.Enemy_Ciuperca) {
                thisEnemy.setCurrentAnimation(Enemy.ciuperca_anim[animations_enemy_enum.walk]);
            }
            return new Follow(thisEnemy);
        }

        if(thisEnemy.getAttackTimer() <= 0) {
            canAttack = true;
        }

        if(canAttack) {
            thisEnemy.setAttackTimer(80);
            canAttack = false;
            if (thisEnemy.getId() == ID.Enemy_Skeleton) {
                thisEnemy.setCurrentAnimation(Enemy.skeleton_anim[animations_enemy_enum.attack]);
                return this;
            }
            if (thisEnemy.getId() == ID.Enemy_Goblin) {
                thisEnemy.setCurrentAnimation(Enemy.goblin_anim[animations_enemy_enum.attack]);
                return this;
            }
            if (thisEnemy.getId() == ID.Enemy_Eye) {
                thisEnemy.setCurrentAnimation(Enemy.eye_anim[animations_enemy_enum.attack]);
                return this;
            }
            if (thisEnemy.getId() == ID.Enemy_Slime) {
                thisEnemy.setCurrentAnimation(Enemy.slime_anim[animations_enemy_enum.attack]);
                return this;
            }
            if (thisEnemy.getId() == ID.Enemy_Ciuperca) {
                thisEnemy.setCurrentAnimation(Enemy.ciuperca_anim[animations_enemy_enum.attack]);
                return this;
            }

        }

        if(!thisEnemy.getInAnimation().val && thisEnemy.getCurrentAnimation() != null) {
            if(thisEnemy.getBounds().intersects(playerBounds)){
                thisEnemy.setHitPlayer(true);
            }
            thisEnemy.setCurrentAnimation(null);
        }

        return this;
    }

    @Override
    public void init() {
        thisEnemy.setSpeedX(0);
        canAttack = false;
        thisEnemy.setAttackTimer(80);
        thisEnemy.setCurrentAnimation(null);
    }
}
