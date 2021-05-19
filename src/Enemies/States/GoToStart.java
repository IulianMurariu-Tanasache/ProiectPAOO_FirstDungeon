package Enemies.States;

import Enemies.Enemy;

import java.awt.*;

/*! \class GoToStart
    \brief Clasa care extinde EnemyState si gestioneaza intoarcerea inamicilor la punctul lor de plecare.

    Inamicii au o zona de patrulat si ei trebuie sa se intoarca in zona lor cand nu mai pot urmari jucatorul sau acesta iese din zona lor de urmarire.
 */
public class GoToStart extends EnemyState{

    public GoToStart(Enemy enemy) {
        thisEnemy = enemy;
        init();
    }

    @Override
    public EnemyState update(Rectangle playerBounds) {
        int x = thisEnemy.getX();
        if(((playerBounds.x >= x - distanceToFollow && playerBounds.x < x) || (playerBounds.x < x + distanceToFollow && playerBounds.x > x)) && (Math.abs(playerBounds.y + playerBounds.height - (thisEnemy.getY() + thisEnemy.getHeight())) < height) && (playerBounds.y < thisEnemy.getY() + thisEnemy.getHeight()))
            return new Follow(thisEnemy);
        if(Math.abs(thisEnemy.getStartX() - x) < 6)
            return new Patrol(thisEnemy);
        if(x > thisEnemy.getStartX())
        {
            thisEnemy.setFacing(false);
            thisEnemy.setSpeedX(-horizontalSpeed);
        }
        else {
            thisEnemy.setFacing(true);
            thisEnemy.setSpeedX(horizontalSpeed);
        }
        return this;
    }

    @Override
    public void init() {

    }
}
