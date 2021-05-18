package Enemies.States;

import Enemies.Enemy;

import java.awt.*;

public abstract class EnemyState  {

    protected final int horizontalSpeed = 2;
    protected static final int distanceToFollow = 150;
    protected static final int distanceToAttack = 40;
    protected static final int distanceToPatrol = 200;
    protected  static final int height = 40;
    protected Enemy thisEnemy;

    public static int getDistanceToAttack() {
        return distanceToAttack;
    }

    public abstract EnemyState update(Rectangle playerBounds);
    public abstract void init();

    public static int getDistanceToPatrol() {
        return distanceToPatrol;
    }
}
