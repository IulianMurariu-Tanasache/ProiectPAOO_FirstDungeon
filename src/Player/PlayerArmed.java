package Player;

import ChestiiRandom.MutableBoolean;
import Dungeon.Dungeon;
import Game.Window;
import GameObject.ID;
import GameStates.GameState;
import Player.States.*;
import Room.Room;
import SpriteSheet.Animation;

import java.awt.*;
import java.io.IOException;


public class PlayerArmed extends Player {

    public PlayerArmed(int x, int y, float scale, ID i, String sheetName) {

        super(x, y, scale, i);
        facing = true;
        armed = true;
        inAnimation = new MutableBoolean();
        speedY = 5;
        health = 6;
        stamina = 6;
        width = 22;
        gotHit = false;
        try {
            animations = new Animation[]{
                    new Animation(sheetName, "walk", 7),
                    new Animation(sheetName, "jump", 10),
                    new Animation(sheetName, "jump", 9),
                    new Animation(sheetName, "fall", 10),
                    new Animation(sheetName, "dash", 9),
                    new Animation(sheetName, "idle", 10),
                    new Animation(sheetName, "idle_crouch", 10),
                    new Animation(sheetName, "walk_crouch", 10),
                    new Animation(sheetName, "death", 10),
                    new Animation(sheetName, "attack1", 8),
                    new Animation(sheetName, "attack2", 8)
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

    /*@Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, (int)(width * scale), (int)(height * scale));
    }*/


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
                        GameState.setScore(GameState.getScore() + 10);
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
                                setHealth(health - 1);
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
                            if(state instanceof Attack) {
                                width = 22;
                                height = 29;
                                attacking = false;
                                stamina++;
                            }
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
}

