package SpriteSheet;

import ChestiiRandom.MutableBoolean;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Animation extends SpriteSheet{

    private BufferedImage[] frames;
    private int current;
    private final double speed;
    private static double delta;

    public Animation(String sheet, String name, int speed) throws IOException {
        super(sheet, sheet);
        current = 0;
        this.speed = 1000000000 / (double)speed;
        delta = 0;
        BufferedReader br = new BufferedReader(new FileReader(this.file));
        try {
            if(!br.readLine().contains("animated=true"))
            {
               throw new Exception("Animating sheet without animation!!");
            }
            else{
                String l = br.readLine();
                while(!l.contains(name)) {
                    l = br.readLine();
                }
                frames = grabImages(name,1,Integer.parseInt(l.substring(l.lastIndexOf(":") + 1,l.lastIndexOf(">"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getCurrentFrame(double elapsed, MutableBoolean in)
    {
        BufferedImage rez = frames[current];
        delta += elapsed / speed;
        if(delta >= 1)
        {
            current++;
            delta--;
        }
        if(current >= frames.length)
        {
            current = 0;
            in.val = false;
        }
        return rez;
    }

}


