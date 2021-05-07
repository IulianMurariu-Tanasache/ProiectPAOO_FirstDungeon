package Enemies.States;

import Enemies.Enemy;

import java.awt.*;

public abstract class EnemyState  {

    protected final int horizontalSpeed = 2;
    protected static final int distanceToFollow = 150;
    protected static final int distanceToAttack = 35;
    protected static final int distanceToPatrol = 200;
    protected  static final int height = 40;
    protected Enemy thisEnemy;

    public abstract EnemyState update(Rectangle playerBounds);
    public abstract void init();

}
