package SpriteSheet;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapSheet extends SpriteSheet{

    private Tile[] tiles;

    public MapSheet(String sheet) throws IOException {
        super(sheet,sheet);
        BufferedReader br = new BufferedReader(new FileReader(this.file));
        try {
            if(!br.readLine().contains("animated=false"))
            {
                throw new Exception("Map sheet with animation!??!");
            }
            else {
                String l = br.readLine();
                int number = Integer.parseInt(l);
                tiles = new Tile[number];
                int i = 0;
                while (i < number) {
                    l = br.readLine();
                    String val = l.substring(l.lastIndexOf(":") + 1);
                    String nume_tile = l.substring(0,l.lastIndexOf(":"));
                    String[] res = val.split(",");
                    int[] values = new int[res.length];
                    for (int j = 0; j < res.length; ++j) {
                        values[j] = Integer.parseInt(res[j]);
                    }
                    tiles[i] = new Tile(nume_tile,image.getSubimage(values[0],values[1],values[2],values[3]),values[4]);
                    if(values[4] == 1) {
                        tiles[i].setBounds(new Rectangle(values[5],values[6],values[7],values[8]));
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tile getTileByName(String name)
    {
        for(Tile t : tiles)
        {
            if(t.getName().equals(name))
                return t;
        }
        return null;
    }

    public Tile getTileByIndex(int i)
    {
        return tiles[i];
    }

}
