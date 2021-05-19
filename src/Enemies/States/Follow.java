package Enemies.States;

import Enemies.Enemy;

import java.awt.*;

/*! \class Follow
    \brief Clasa care extinde EnemyState si gestioneaza urmarirea jucatorului de catre inamici.
 */
public class Follow extends EnemyState{

    public Follow(Enemy enemy) {
        thisEnemy = enemy;
        init();
    }

    @Override
    public EnemyState update(Rectangle playerBounds) {
        int x = thisEnemy.getX();
        if(!(((playerBounds.x >= x - distanceToFollow && playerBounds.x < x) || (playerBounds.x < x + distanceToFollow && playerBounds.x > x)) && (Math.abs(playerBounds.y + playerBounds.height - (thisEnemy.getY() + thisEnemy.getHeight())) < height) && (playerBounds.y < thisEnemy.getY() + thisEnemy.getHeight())))
        {
            return new GoToStart(thisEnemy);
        }
        if((((playerBounds.x >= x - distanceToAttack && playerBounds.x < x) || (playerBounds.x < x + thisEnemy.getWidth() + distanceToAttack && playerBounds.x > x)) && (Math.abs(playerBounds.y + playerBounds.height - (thisEnemy.getY() + thisEnemy.getHeight())) < height) && (playerBounds.y < thisEnemy.getY() + thisEnemy.getHeight())))
            return new Attack(thisEnemy);
        if(!thisEnemy.isNothingUnder()) {
            if (playerBounds.x < x) {
                thisEnemy.setFacing(false);
                thisEnemy.setSpeedX(-horizontalSpeed);
            } else {
                thisEnemy.setFacing(true);
                thisEnemy.setSpeedX(horizontalSpeed);
            }
        }
        return this;
    }

    @Override
    public void init() {

    }
}
