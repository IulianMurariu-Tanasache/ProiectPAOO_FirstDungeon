package SpriteSheet;

import ChestiiRandom.MutableBoolean;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Animation{

    private BufferedImage[] frames;
    private int current;
    private final double speed;
    private double delta;

    public Animation(String sheet, String name, int speed) throws IOException {
        //super(sheet, sheet);
        SpriteSheet s = new SpriteSheet(sheet, sheet);
        current = 0;
        //this.speed = 1000000000 / (double)speed;
        this.speed = speed;
        delta = 0;
        BufferedReader br = new BufferedReader(new FileReader(s.getFile()));
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
                frames = s.grabImages(name,Integer.parseInt(l.substring(l.lastIndexOf(":") + 1,l.lastIndexOf(">"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Animation(Animation animation) {
        this.frames = animation.frames;
        speed = animation.speed;
        current = 0;
        delta = 0;
    }

    public BufferedImage getCurrentFrame(double elapsed, MutableBoolean in)
    {
        in.val = true;
        BufferedImage rez = frames[current];
        delta += elapsed;
        if(delta > speed)
        {
            current++;
            delta-=speed;
        }
        if(current >= frames.length)
        {
            current = 0;
            in.val = false;
        }
        return rez;
    }

    public BufferedImage getImage(int i) {
        return frames[i];
    }
}


