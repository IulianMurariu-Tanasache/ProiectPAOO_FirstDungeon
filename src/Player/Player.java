package Player;

import ChestiiRandom.MutableBoolean;
import Dungeon.Dungeon;
import GUI.Elements.StatsBar;
import Game.Window;
import GameObject.GameObject;
import GameObject.ID;
import GameStates.GameState;
import Observer.Observer;
import Player.States.Fall;
import Player.States.Idle;
import Player.States.Jump;
import Player.States.PlayerState;
import Room.Room;
import SpriteSheet.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends GameObject {

    protected Animation[] animations;
    protected Animation currentAnimation;
    protected MutableBoolean inAnimation;
    protected BufferedImage img;

    protected PlayerState state;
    protected int height = 29;
    protected int health;
    protected int width;
    protected int stamina;
    protected boolean gotHit;
    protected StatsBar statsBar;
    protected int timerDamageCuie;
    protected final int timerDamageCuieConstanta = 80;
    protected boolean armed;
    protected boolean attacking = false;

    protected static Player instance = null;

    public Player(int x, int y, float scale, ID i) {
        super(x,y,scale,i);
        armed = false;
    }

    public static void setInstance(int x, int y, float scale, ID i, String sheetName) {
        if(!GameState.isFoundTreasure())
            instance = new Player(x,y,scale,i,sheetName);
        else
            instance = new PlayerArmed(x,y,scale,i,sheetName);
    }
    public static Player getInstance() {
        return instance;
    }

    private Player(int x, int y, float scale, ID i, String sheetName) {

        super(x, y, scale, i);
        facing = true;
        armed = false;
        inAnimation = new MutableBoolean();
        speedY = 5;
        health = 6;
        stamina = 6;
        width = 22;
        gotHit = false;
        try {
            animations = new Animation[]{
                    new Animation(sheetName, "walk", 7),
                    new Animation(sheetName, "run", 7),
                    new Animation(sheetName, "jump", 7),
                    new Animation(sheetName, "fall", 7),
                    new Animation(sheetName, "dash", 9),
                    new Animation(sheetName, "idle", 7),
                    new Animation(sheetName, "idle_crouch", 7),
                    new Animation(sheetName, "walk_crouch", 7),
                    new Animation(sheetName, "death", 10)
            };
            img = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlayerState.setPlayer(this);
        state = new Idle();
        PlayerState.setPrev(state);
        timerDamageCuie = timerDamageCuieConstanta;
    }

    @Override
    public void tick() {

        if(gotHit){
            gotHit = false;
            health-=2;
        }

        state = state.handleInput();
        mapCollision();

        this.x += speedX;
        this.y += speedY;

        PlayerState.timerPass();

        statsBar.updateObserver(this);

        for(GameObject obs : Dungeon.getInstance().getRoom().getObjects())
            if(obs instanceof Observer)
                ((Observer)obs).updateObserver(this);
    }

    @Override
    public void render(Graphics g, double elapsed) {
        if(currentAnimation != null)
            img = currentAnimation.getCurrentFrame(elapsed, inAnimation);
        else
            img = animations[animations_enum.death].getImage(3);
        if(!facing)
            g.drawImage(img, x + (int)(img.getWidth() * scale), y, (int)(img.getWidth() * scale * -1), (int)(img.getHeight() * scale), null);
        else
            g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
        g.drawRect(x, y, (int)(width * scale), (int)(height * scale));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, (int)(width * scale), (int)(height * scale));
    }


    @Override
    public void mapCollision() {
        Dungeon dungeon = Dungeon.getInstance();
        Room r = dungeon.getRoom();
        boolean updateRoom = false;
        int startX = this.x / 64 - 1;
        int startY = this.y / 64 - 1;
        if(startX < 0)
           startX = 0;
        if(startY < 0)
           startY = 0;
        int endX = (int)(this.x + 25*scale) / 64 + 1;
        int endY = (int)(this.y + 30*scale) / 64 + 1;
        if(endX >= r.getDimX())
           endX = r.getDimX() - 1;
        if(endY >= r.getDimY())
           endY = r.getDimY() - 1;
        Rectangle tileBounds = null;
        Rectangle playerBounds = getBounds();
        Rectangle ecran = Window.getInstance().getBounds();
        boolean hitCuie = false;
        if(speedY == 0)
            speedY = 5;
        boolean hit;
        for(int i = startY; i <= endY; ++i)
           for(int j = startX; j <= endX; ++j)
           {
               hit = false;
               playerBounds.y = this.y + speedY;
               playerBounds.x = this.x + speedX;
               if(!ecran.contains(playerBounds)) {
                   if (playerBounds.x + playerBounds.width >= ecran.width) {
                       if (!dungeon.nextRoom(0, 1)) {
                           this.x = 1;
                           updateRoom = true;
                       }
                       //y = 200;
                       speedX = speedY = 0;
                   } else if (playerBounds.x <= 0) {
                       if (!dungeon.nextRoom(0, -1)) {
                           this.x = ecran.width - playerBounds.width - 1;
                           updateRoom = true;
                       }
                       speedX = speedY = 0;
                       playerBounds.x = this.x;
                   } else if (playerBounds.y <= 0) {
                        speedX = speedY = 0;
                        if (!dungeon.nextRoom(-1, 0)) {
                            this.x = 770;
                            this.y = 500;
                            updateRoom = true;
                            PlayerState.setTimerVerticalSpeed(0);
                            state = new Jump();
                        }
                   } else if (playerBounds.y + playerBounds.height >= 576) {
                       speedX = speedY = 0;
                       if (!dungeon.nextRoom(1, 0)) {
                           //y = 70;
                           this.x = 130;
                           updateRoom = true;
                           this.y = playerBounds.height + 10;
                           state = new Fall();
                       }
                   }
                   if(updateRoom)
                   {
                        updateRoom = false;
                        r = dungeon.getRoom();
                        if(stamina < 6)
                            stamina++;
                   }
               }
               tileBounds = r.getBoundsOfTile(i,j);
               if((tileBounds != null && playerBounds.intersects(tileBounds))){
                   playerBounds.y = this.y;
                   if(!playerBounds.intersects(tileBounds)) {
                       speedY = 0;
                       if(r.getTilebyName(i, j, 1).equals("cuie_sus") || r.getTilebyName(i, j, 1).equals("cuie_jos")) {
                           timerDamageCuie++;
                           hitCuie = true;
                           if(timerDamageCuie >= timerDamageCuieConstanta)
                           {
                               timerDamageCuie = 0;
                               health--;
                           }
                       }
                       hit = true;
                   }
                   if(!hit) {
                       playerBounds.y = this.y + speedY;
                       playerBounds.x = this.x;
                       speedX = 0;
                       if (playerBounds.intersects(tileBounds)) {
                           speedY = 0;
                       }
                       playerBounds.y = this.y;
                       if (playerBounds.intersects(tileBounds)) {
                           state = PlayerState.getPrev();
                           state.init();
                           playerBounds = getBounds();

                       }
                   }
               }
           }
        if(!hitCuie) {
            timerDamageCuie = timerDamageCuieConstanta;
        }

    }

    public MutableBoolean getInAnimation() {
        return inAnimation;
    }

    public void setCurrentAnimation(int animation) {
        if(animation < 0)
            this.currentAnimation = null;
        else {
            this.currentAnimation = animations[animation];
            inAnimation.val = true;
        }
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int s) {
        stamina = s;
    }

    public void setGotHit(boolean gotHit) {
        if(!this.gotHit)
            this.gotHit = gotHit;
    }

    public void setHealth(int health) {
        if(health > 0 && health < 7)
            this.health = health;
    }

    public void addStatsBar(StatsBar stats){
        statsBar = stats;
    }

    public boolean isArmed() {
        return armed;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        if(!armed)
            attacking = false;
        else
            this.attacking = attacking;
    }

    /*public void setState(PlayerState state) {
        this.state = state;
    }*/
}

