package Enemies;

import ChestiiRandom.ChestiiStatice;
import ChestiiRandom.MutableBoolean;
import Dungeon.Dungeon;
import Enemies.States.EnemyState;
import Enemies.States.Patrol;
import Game.Window;
import GameObject.GameObject;
import GameObject.ID;
import Player.Player;
import Room.Room;
import SpriteSheet.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/*! \class Enemy
    \brief Clasa abstracta care extinde GameObject si este baza pentru inamici.

    Functia render trebuie implementata in orice clasa derivata.
 */
public abstract class Enemy extends GameObject{

    protected Animation currentAnimation;
    protected MutableBoolean inAnimation;
    protected BufferedImage img;
    protected EnemyState state;
    protected Rectangle playerBounds;
    protected int health;
    protected int startX;
    protected int attackTimer;
    public static Animation[] skeleton_anim;
    public static Animation[] goblin_anim;
    public static Animation[] eye_anim;
    public static Animation[] slime_anim;
    public static Animation[] ciuperca_anim;
    protected boolean hitPlayer;
    protected boolean nothingUnder;
    protected int height;
    protected int width;

    public static void loadAnimations() {
        try {
            skeleton_anim = new Animation[]{
                    new Animation("skeleton", "walk", 10),
                    new Animation("skeleton", "attack", 12)
            };
            goblin_anim = new Animation[]{
                    new Animation("goblinSheet", "walk", 10),
                    new Animation("goblinSheet", "attack", 12)
            };
            eye_anim = new Animation[]{
                    new Animation("eyeSheet", "walk", 9),
                    new Animation("eyeSheet", "attack", 11)
            };
            ciuperca_anim = new Animation[]{
                    new Animation("ciupercaSheet", "idle", 9),
                    new Animation("ciupercaSheet", "attack", 9)
            };
            slime_anim = new Animation[]{
                    new Animation("slimeSheet", "walk", 9),
                    new Animation("slimeSheet", "attack", 9)
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Enemy(int x, int y, ID i) {
        super(x, y, i);
       /* if(new Random().nextInt(2) == 0) {
            facing = false;
            this.x = x + EnemyState.getDistanceToPatrol();
        }
        else {*/
        facing = true;
        this.x = x;
        startX = x;
        inAnimation = new MutableBoolean();
        health = 3;
        hitPlayer = false;
        nothingUnder = false;

        //state
        state = new Patrol(this);
    }

    public Enemy(int x, int y, float scale, ID i) {
        this(x,y,i);
        this.scale = scale;
    }

    public abstract void render(Graphics g);

    public int getStartX() {
        return startX;
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

        this.x += speedX;

        if(attackTimer > 0)
            attackTimer--;
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
                if(i == endY && tileBounds == null) {
                    if((speedX > 0 && j == endX - 1) || (speedX < 0 && j == startX + 1)) {
                        nothingUnder = true;
                    }
                }
            }
        if(nothingUnder) {
            facing = !facing;
            speedX = -speedX;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y, width, height);
    }

    public EnemyState getState() { return state; }

    public int getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(int attackTimer) {
        this.attackTimer = attackTimer;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        if(currentAnimation == null)
            this.currentAnimation = null;
        else
            this.currentAnimation = new Animation(currentAnimation);
    }

    public MutableBoolean getInAnimation() {
        return inAnimation;
    }

    public void setHitPlayer(boolean hitPlayer) {
        this.hitPlayer = hitPlayer;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public boolean isNothingUnder() {
        return nothingUnder;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }
}

