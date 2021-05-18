package SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SpriteSheet {

    //format:x,y,w,h
    protected BufferedImage image;
    protected String file;

    public String getFile() {
        return file;
    }

    //filename pt sheet si incarc aici
    public SpriteSheet(String bf, String file) throws IOException {
        image = ImageLoader.loadImage("Assets/SpriteSheets/" + file + ".png");
        this.file = "Assets/SpriteSheets/" + file + ".sheet";
    }

    public BufferedImage[] grabImages(String name, int howMany) throws IOException
    {
        BufferedImage[] rez = new BufferedImage[howMany];
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        while(!(line = br.readLine()).equals("END"))
        {
            if(line.contains(name))
            {
                int i = 1;
                line = br.readLine();
                while(i <= howMany){
                    if (line.contains(String.valueOf(i))) {
                        //System.out.println(line);
                        String val = line.substring(line.lastIndexOf(":") + 1);
                        String[] res = val.split(",");
                        int[] values = new int[res.length];
                        for (int j = 0; j < res.length; ++j) {
                            values[j] = Integer.parseInt(res[j]);
                        }
                        rez[i-1] = image.getSubimage(values[0],values[1],values[2],values[3]);
                        i++;
                    }
                    line = br.readLine();
                }
                return rez;
            }
        }
        return null;
    }

    public BufferedImage grabImage(String name) throws IOException
    {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        while(!(line = br.readLine()).equals("END"))
        {
            if(line.contains(name))
            {
                String val = line.substring(line.lastIndexOf(":"));
                String[] rez = val.split(",");
                int[] values = new int[rez.length];
                for(int j = 0; j < rez.length; ++j)
                {
                    values[j] = Integer.parseInt(rez[j]);
                }
                return image.getSubimage(values[0],values[1],values[2],values[3]);
            }
        }
        return null;
    }
}
