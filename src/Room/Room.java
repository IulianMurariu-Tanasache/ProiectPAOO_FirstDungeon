package Room;

import SpriteSheet.MapSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Room {

    protected int[][][] config;
    protected final int dimX = 16;
    protected final int dimY = 9;
    protected int type;
    protected static MapSheet sheet;
    protected static BufferedImage[] backOut = null;

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
        for(int i = 0; i < dimY; ++i)
            for(int j = 0; j < dimX; ++j)
            {
                imag = sheet.getTileByIndex(config[0][i][j]).getImg();
                g.drawImage(imag, j * 64, i * 64, 64, 64, null);
                /*if(sheet.getTileByIndex(config[0][i][j]).isSolid())
                {
                    g.drawRect(j * 64, i * 64, 64, 64);
                }*/
            }
        for(int i = 0; i < dimY; ++i)
            for(int j = 0; j < dimX; ++j)
                if (config[1][i][j] != 0) {
                    imag = sheet.getTileByIndex(config[1][i][j]).getImg();
                    g.drawImage(imag,j * 64,i * 64,null);
                    /*if(sheet.getTileByIndex(config[1][i][j]).isSolid())
                    {
                        g.drawRect(j * 64, i * 64, imag.getWidth(), imag.getHeight());
                    }*/
                }
    }

    public int getType() {
        return type;
    }

    public Rectangle getBoundsOfTile(int i, int j)
    {
        if(i < 0 || j < 0 || i >= dimY || j >= dimX)
            return null;
        if(sheet.getTileByIndex(config[0][i][j]).isSolid())
            return new Rectangle(j * 64, i * 64, 64, 64);
        if (config[1][i][j] != 0 && sheet.getTileByIndex(config[1][i][j]).isSolid())
            return new Rectangle(j * 64, i * 64, sheet.getTileByIndex(config[1][i][j]).getImg().getWidth(), sheet.getTileByIndex(config[1][i][j]).getImg().getHeight());
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
}
