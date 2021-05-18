package Room;

import ChestiiRandom.ChestiiStatice;
import Enemies.Enemy;
import GameObject.GameObject;
import GameStates.GameState;
import Player.Player;
import SoundTrack.SoundManager;
import SpriteSheet.MapSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Room {

    protected int[][][] config;
    protected final int dimX = 16;
    protected final int dimY = 9;
    protected int type;
    protected static MapSheet sheet;
    protected ArrayList<GameObject> objects;
    private Queue<GameObject> toRemove = new LinkedList<GameObject>();
    protected boolean toBeLocked;
    protected boolean isLocked;

    public void render(Graphics g)
    {
        BufferedImage imag;
        g.setColor(Color.WHITE);
        for(int i = 0; i < dimY; ++i)
            for(int j = 0; j < dimX; ++j)
            {
                imag = sheet.getTileByIndex(config[0][i][j]).getImg();
                g.drawImage(imag, j * ChestiiStatice.tileDimension, i * ChestiiStatice.tileDimension, ChestiiStatice.tileDimension, ChestiiStatice.tileDimension, null);
                /*if(sheet.getTileByIndex(config[0][i][j]).isSolid())
                {
                    Rectangle rect = sheet.getTileByIndex(config[0][i][j]).getBounds();
                    g.drawRect(j * ChestiiStatice.tileDimension + rect.x,i * ChestiiStatice.tileDimension + rect.y,rect.width,rect.height);
                }*/
            }
        for(int i = 0; i < dimY; ++i)
            for(int j = 0; j < dimX; ++j)
                if (config[1][i][j] != 0) {
                    imag = sheet.getTileByIndex(config[1][i][j]).getImg();
                    g.drawImage(imag,j * ChestiiStatice.tileDimension,i * ChestiiStatice.tileDimension,null);
                    /*if(sheet.getTileByIndex(config[1][i][j]).isSolid())
                    {
                        Rectangle rect = sheet.getTileByIndex(config[1][i][j]).getBounds();
                        g.drawRect(j * ChestiiStatice.tileDimension + rect.x,i * ChestiiStatice.tileDimension + rect.y,rect.width,rect.height);
                    }*/
                }

    }

    public void tick(){
        for(GameObject obj : objects)
            obj.tick();
        while(!toRemove.isEmpty()) {
            if(toRemove.peek() instanceof Enemy)
                GameState.setScore(GameState.getScore() - 5);
            objects.remove(toRemove.poll());
        }
        if(isToBeLocked() && hasEnemies() && Player.getInstance().isArmed() && Player.getInstance().getX() < 900 && Player.getInstance().getX() > 100 && Player.getInstance().getY() < 500 && Player.getInstance().getY() > 100) {
            toBeLocked = false;
            isLocked = true;
            SoundManager.getSoundManager().play("gate.wav");
            config[1][3][0] = 19;
            config[1][3][dimX - 1] = 19;
            if(type == 2 || type == 3)
                config[1][0][1] = 20;
            if(type == 2 || type == 1)
                config[1][dimY - 2][dimX - 5] = 20;
        }
        if(isLocked && !hasEnemies())
        {
            isLocked = false;
            config[1][3][0] = 0;
            config[1][3][dimX - 1] = 0;
            config[1][0][1] = 0;
            config[1][dimY - 2][dimX - 5] = 0;
        }
    }

    public int getType() {
        return type;
    }

    public Rectangle getBoundsOfTile(int i, int j)
    {
        if(i < 0 || j < 0 || i >= dimY || j >= dimX)
            return null;
        if(sheet.getTileByIndex(config[0][i][j]).isSolid()) {
            Rectangle rect = new Rectangle(sheet.getTileByIndex(config[0][i][j]).getBounds());
            rect.x += j * ChestiiStatice.tileDimension;
            rect.y += i * ChestiiStatice.tileDimension;
            return rect;
        }
        if (config[1][i][j] != 0 && sheet.getTileByIndex(config[1][i][j]).isSolid())
        {
            Rectangle rect = new Rectangle(sheet.getTileByIndex(config[1][i][j]).getBounds());
            rect.x += j * ChestiiStatice.tileDimension;
            rect.y += i * ChestiiStatice.tileDimension;
            return rect;
        }
        return null;
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public static void setSheet(MapSheet sheet) {
        Room.sheet = sheet;
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public void add(GameObject obj) {
        objects.add(obj);
    }

    public void remove(GameObject obj) {
        toRemove.add(obj);
    }

    public String getTilebyName(int i, int j, int k) {
        if(i < 0 || j < 0 || i >= dimY || j >= dimX)
            return null;
        return sheet.getTileByIndex(config[k][i][j]).getName();
    }

    public boolean hasEnemies() {
        for(GameObject obj : objects)
            if(obj.isEnemy())
                return true;
        return false;
    }

    public boolean isToBeLocked() {
        return toBeLocked;
    }

    public void setToBeLocked(boolean toBeLocked) {
        this.toBeLocked = toBeLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int[][][] getConfig() {
        return config;
    }

    public void setConfig(int[][][] config) {
        this.config = config;
    }
}
