package Room;

import ChestiiRandom.ChestiiStatice;
import Enemies.Slime;
import GameObject.ID;
import GameObject.SpeechBubbles;
import GameStates.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RoomOutdoor extends Room{

    public RoomOutdoor(int type) throws IOException {
        toBeLocked = false;
        isLocked = false;
        objects = new ArrayList<>();
        this.type = type;
        Random rand = new Random();
        BufferedReader br = new BufferedReader(new FileReader("Assets/SpriteSheets/room.config"));
        while (!br.readLine().equals("room:" + type))
            ;
        config = new int[2][dimY][dimX];
        for (int i = 0; i < dimY; ++i) {
            String[] values = br.readLine().split("\\s+");
            for (int j = 0; j < dimX; ++j) {
                int what = Integer.parseInt(values[j]);
                config[0][i][j] = what % 10;
                if (rand.nextInt(2) == 0 && config[1][i][j] == 0) {
                    config[1][i][j] = what / 10;
                    if (i == dimY - 1 && config[0][i - 1][j] == 4)//rama
                        config[1][i][j] = what / 10;
                    else if (i == dimY - 1 && config[0][i - 1][j] != 4)
                        config[1][i][j] = what / 10;
                    if (config[1][i][j] == 9)//cuie sus
                        config[1][i + 1][j] = 10;
                    else if (config[1][i][j] == 13)// stalp
                    {
                        config[1][i][j + 2] = 14;
                        for (int q = 0; q < 5; ++q)
                            config[1][i + q][j + 1] = 12;
                    }
                }
            }
        }

        if (GameState.getDiff() == 0) {
            if (type == 4) {
                //objects.add(new Slime(270,390,2.9f, ID.Enemy_Slime));
                objects.add(new SpeechBubbles(20, 320, 260, 100, 0.32f, "Assets/SpeechBubbles/start_bubble.gif"));
                objects.add(new SpeechBubbles(330, 320, 180, 100, 0.32f, "Assets/SpeechBubbles/jump_bubble.gif"));
                objects.add(new SpeechBubbles(520, 150, 220, 100, 0.32f, "Assets/SpeechBubbles/stats_bubble.gif"));
            }
            if (type == 5) {
                objects.add(new SpeechBubbles(50, 320, 180, 100, 0.32f, "Assets/SpeechBubbles/dash_bubble.gif"));
                objects.add(new SpeechBubbles(430, 320, 280, 100, 0.32f, "Assets/SpeechBubbles/crouch_bubble.gif"));
            }
        }
    }

    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);
        BufferedImage imag;
        for(int i = 0; i < dimY; ++i)
            for(int j = 0; j < dimX; ++j)
            {
                if(config[0][i][j] != 0) {
                    imag = sheet.getTileByIndex(config[0][i][j]).getImg();
                    g.drawImage(imag, j * ChestiiStatice.tileDimension, i * ChestiiStatice.tileDimension, ChestiiStatice.tileDimension, ChestiiStatice.tileDimension, null);
                    /*if(sheet.getTileByIndex(config[0][i][j]).isSolid())
                    {
                        g.drawRect(j * ChestiiStatice.tileDimension, i * ChestiiStatice.tileDimension, ChestiiStatice.tileDimension, ChestiiStatice.tileDimension);
                    }*/
                }
            }
        for(int i = 0; i < dimY; ++i)
            for(int j = 0; j < dimX; ++j)
                if (config[1][i][j] != 0) {
                    imag = sheet.getTileByIndex(config[1][i][j]).getImg();
                    g.drawImage(imag,j * ChestiiStatice.tileDimension,i * ChestiiStatice.tileDimension,null);
                    if(sheet.getTileByIndex(config[1][i][j]).isSolid())
                    {
                        g.drawRect(j * ChestiiStatice.tileDimension, i * ChestiiStatice.tileDimension, imag.getWidth(), imag.getHeight());
                    }
                }
    }

    public Rectangle getBoundsOfTile(int i, int j)
    {
        if(i < 0 || j < 0 || i >= dimY || j >= dimX)
            return null;
        if(config[0][i][j] == 0)
            return null;
        if(sheet.getTileByIndex(config[0][i][j]).isSolid())
            return new Rectangle(j * ChestiiStatice.tileDimension, i * ChestiiStatice.tileDimension, ChestiiStatice.tileDimension, ChestiiStatice.tileDimension);
        if (config[1][i][j] != 0 && sheet.getTileByIndex(config[1][i][j]).isSolid())
            return new Rectangle(j * ChestiiStatice.tileDimension, i * ChestiiStatice.tileDimension, sheet.getTileByIndex(config[1][i][j]).getImg().getWidth(), sheet.getTileByIndex(config[1][i][j]).getImg().getHeight());
        return null;
    }

}
