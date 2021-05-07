package Enemies.States;

import Enemies.Enemy;

import java.awt.*;

public class Patrol extends EnemyState{

    public Patrol(Enemy enemy) {
        thisEnemy = enemy;
        init();
    }

    @Override
    public EnemyState update(Rectangle playerBounds) {
        int x = thisEnemy.getX();
        if(((playerBounds.x >= x - distanceToFollow && playerBounds.x < x) || (playerBounds.x < x + distanceToFollow && playerBounds.x > x)) && (Math.abs(playerBounds.y + playerBounds.height - (thisEnemy.getY() + thisEnemy.getHeight())) < height) && (playerBounds.y < thisEnemy.getY() + thisEnemy.getHeight()))
            return new Follow(thisEnemy);
        if(x - thisEnemy.getStartX() >= distanceToPatrol || (thisEnemy.isFacing() && thisEnemy.getSpeedX() == 0)) {
            thisEnemy.setFacing(false);
            thisEnemy.setSpeedX(-horizontalSpeed);
        }
        if(x - thisEnemy.getStartX() <= 0 || (!thisEnemy.isFacing() && thisEnemy.getSpeedX() == 0)) {
            thisEnemy.setFacing(true);
            thisEnemy.setSpeedX(horizontalSpeed);
        }
        return this;
    }

    @Override
    public void init() {
    }

}
