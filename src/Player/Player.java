package Player;

import ChestiiRandom.MutableBoolean;
import Dungeon.Dungeon;
import GameObject.*;
import Game.Window;
import Player.States.*;
import Room.Room;
import SpriteSheet.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends GameObject {

    private Animation[] animations;
    private Animation currentAnimation;
    private MutableBoolean inAnimation;
    private BufferedImage img;
    private boolean facing; // false - stanga || true - dreapta
    private PlayerState state;
    private int height = 29;

    public Player(int x, int y, float scale, ID i, String sheetName) {

        super(x, y, scale, i);
        facing = true;
        inAnimation = new MutableBoolean();
        speedY = 5;
        try {
            animations = new Animation[]{
                    new Animation(sheetName, "walk", 6),
                    new Animation(sheetName, "run", 6),
                    new Animation(sheetName, "jump", 6),
                    new Animation(sheetName, "fall", 6),
                    new Animation(sheetName, "dash", 9),
                    new Animation(sheetName, "idle", 6),
                    new Animation(sheetName, "idle_crouch", 6),
                    new Animation(sheetName, "walk_crouch", 6),
                    new Animation(sheetName, "death", 6)
            };
            img = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlayerState.setPlayer(this);
        state = new Idle();
        PlayerState.setPrev(state);
    }

    @Override
    public void tick() {

        state = state.handleInput();
        mapCollision();

        this.x += speedX;
        this.y += speedY;

        PlayerState.timerPass();
    }

    @Override
    public void render(Graphics g, double elapsed) {
        img = currentAnimation.getCurrentFrame(elapsed, inAnimation);
        if(!facing)
            g.drawImage(img, x + (int)(img.getWidth() * scale), y, (int)(img.getWidth() * scale * -1), (int)(img.getHeight() * scale), null);
        else
            g.drawImage(img, x, y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
        //g.drawRect(x, y, (int)(22 * scale), (int)(height * scale));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, (int)(22 * scale), (int)(height * scale));
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
                            //y = 511 - height;
                            //x = 833;
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
                   }
               }
               tileBounds = r.getBoundsOfTile(i,j);
               if((tileBounds != null && playerBounds.intersects(tileBounds))){
                   playerBounds.y = this.y;
                   if(!playerBounds.intersects(tileBounds)) {
                       speedY = 0;
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
    }

    public MutableBoolean getInAnimation() {
        return inAnimation;
    }

    public void setCurrentAnimation(int animation) {
        this.currentAnimation = animations[animation];
        inAnimation.val = true;
    }

    public boolean isFacing() {
        return facing;
    }

    public void setFacing(boolean facing) {
        this.facing = facing;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

