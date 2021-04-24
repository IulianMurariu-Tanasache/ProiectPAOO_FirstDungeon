package Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class RoomInterior extends Room{

    public RoomInterior(int type, int edge) throws IOException {
        this.type = type;
        Random rand = new Random();
        //filereader static pentru toate camerele??
        BufferedReader br = new BufferedReader(new FileReader("Assets/SpriteSheets/room.config"));
        while(!br.readLine().equals("room:" + type))
            ;
        config = new int[2][dimY][dimX];
        for(int i = 0 ; i < dimY; ++i) {
            String[] values = br.readLine().split("\\s+");
            for (int j = 0; j < dimX; ++j) {
                int what = Integer.parseInt(values[j]);
                config[0][i][j] = what % 10;
                if(rand.nextInt(2) == 0 && config[1][i][j] == 0)
                {
                    config[1][i][j] = what / 10;
                    if(i == dimY - 1 && config[0][i-1][j] == 4)//rama
                        config[1][i][j] = what/10;
                    else if(i == dimY - 1 && config[0][i-1][j] != 4)
                        config[1][i][j] = what/10;
                    if(config[1][i][j] == 9)//cuie sus
                        config[1][i+1][j] = 10;
                    else if(config[1][i][j] == 13)// stalp
                    {
                        config[1][i][j + 2] = 14;
                        for(int q = 0; q < 5; ++q)
                            config[1][i + q][j + 1] = 12;
                    }
                }
            }
        }
        //margini
        //alte camere??
        switch (edge) {//0 - default; 1 - stanga; 2 - dreapta
            case 2 -> {
                config[0][3][dimX - 1] = 2;
                config[0][4][dimX - 1] = 2;
                config[0][5][dimX - 1] = 2;
            }
            case 1 -> {
                config[0][3][0] = 1;
                config[0][4][0] = 1;
                config[0][5][0] = 1;
            }

        }
    }

}
