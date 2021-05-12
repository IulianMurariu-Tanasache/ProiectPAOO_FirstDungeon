package Room;

import Enemies.Enemy;
import GameObject.*;
import GameStates.GameState;
import SpriteSheet.MapSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Room {

    protected int[][][] config;
    protected final int dimX = 16;
    protected final int dimY = 9;
    protected int type;
    protected static MapSheet sheet;
    protected static BufferedImage[] backOut = null;
    protected ArrayList<GameObject> objects;
    private Queue<GameObject> toRemove = new LinkedList<GameObject>();
    protected boolean toBeLocked;
    protected boolean isLocked;

    public static void loadBack() {
        if(backOut != null)
            return;
        backOut = new BufferedImage[2];
        try {
            backOut[0] = ImageIO.read(new File("Assets/SpriteSheets/Background_2.png"));
            backOut[1] = ImageIO.read(new File("Assets/SpriteSheets/Background_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g)
    {
        BufferedImage imag;
        g.setColor(Color.WHITE);
        for(int i = 0; i < dimY; ++i)
            for(int j = 0; j < dimX; ++j)
            {
                imag = sheet.getTileByIndex(config[0][i][j]).getImg();
                g.drawImage(imag, j * 64, i * 64, 64, 64, null);
                /*if(sheet.getTileByIndex(config[0][i][j]).isSolid())
                {
                    Rectangle rect = sheet.getTileByIndex(config[0][i][j]).getBounds();
                    g.drawRect(j * 64 + rect.x,i * 64 + rect.y,rect.width,rect.height);
                }*/
            }
        for(int i = 0; i < dimY; ++i)
            for(int j = 0; j < dimX; ++j)
                if (config[1][i][j] != 0) {
                    imag = sheet.getTileByIndex(config[1][i][j]).getImg();
                    g.drawImage(imag,j * 64,i * 64,null);
                    /*if(sheet.getTileByIndex(config[1][i][j]).isSolid())
                    {
                        Rectangle rect = sheet.getTileByIndex(config[1][i][j]).getBounds();
                        g.drawRect(j * 64 + rect.x,i * 64 + rect.y,rect.width,rect.height);
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
        if(isLocked && !hasEnemies())
            isLocked = false;
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
            rect.x += j * 64;
            rect.y += i * 64;
            return rect;
        }
        if (config[1][i][j] != 0 && sheet.getTileByIndex(config[1][i][j]).isSolid())
        {
            Rectangle rect = new Rectangle(sheet.getTileByIndex(config[1][i][j]).getBounds());
            rect.x += j * 64;
            rect.y += i * 64;
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
}
