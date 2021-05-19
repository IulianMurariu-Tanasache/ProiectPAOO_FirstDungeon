package Room;

import ChestiiRandom.ChestiiStatice;
import Enemies.*;
import GameObject.GameObject;
import GameObject.ID;
import GameObject.Inima;
import GameStates.GameState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RoomInterior extends Room{

    public RoomInterior(int type, boolean toLock, int[][][] con, ArrayList<GameObject> list) {
        toBeLocked = toLock;
        this.type = type;
        config = con;
        objects = list;
    }

    public RoomInterior(int type, int edge) throws IOException {
        toBeLocked = false;
        isLocked = false;
        objects = new ArrayList<>();
        this.type = type;
        Random rand = new Random();
        //filereader static pentru toate camerele??
        BufferedReader br = new BufferedReader(new FileReader("Assets/SpriteSheets/room.config"));
        while(!br.readLine().equals("room:" + type))
            ;
        config = new int[2][dimY][dimX];
        boolean inimaPeCamera = false;
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
                int toSpawn = new Random().nextInt(14);
                if(i > 2 && j > 2 && i < dimY - 1 && j < dimX - 2 && (config[0][i][j-1] == 3 || config[1][i][j-1] == 10)  && (config[0][i][j] == 3 || config[1][i][j] == 10) && getBoundsOfTile(i - 1,j) == null && getBoundsOfTile(i - 1,j - 1) == null && getBoundsOfTile(i - 2,j) == null && getBoundsOfTile(i - 2,j - 1) == null && getBoundsOfTile(i - 1,j - 2) == null && getBoundsOfTile(i - 2,j - 2) == null) {
                    switch (toSpawn) {
                        case 7 -> objects.add(new Scheletron((j-1) * ChestiiStatice.tileDimension + 10, ChestiiStatice.tileDimension * (i-1) - 18,1.6f, ID.Enemy_Skeleton));
                        case 11 -> objects.add(new Goblin((j-1)*ChestiiStatice.tileDimension +10, ChestiiStatice.tileDimension*(i-1) - 2, 1.7f, ID.Enemy_Goblin));
                        case 13 -> objects.add(new Ciuperca((j-1) * ChestiiStatice.tileDimension + 10, ChestiiStatice.tileDimension * (i-1) - 30,1.8f, ID.Enemy_Ciuperca));
                        case 4 -> objects.add(new Slime((j-1)*ChestiiStatice.tileDimension+10, ChestiiStatice.tileDimension*(i-1) + 9, 2.9f,ID.Enemy_Slime));
                        case 5 -> objects.add(new Eye((j-1)*ChestiiStatice.tileDimension+10, ChestiiStatice.tileDimension*(i-2) + 30, 1.5f,ID.Enemy_Eye));
                    }

                }
                if(i > 2 && j > 2 && j < dimX - 2 && i < dimY - 2 && getBoundsOfTile(i,j) == null && toSpawn == 5 && !inimaPeCamera && GameState.getDiff() == 0)
                {
                    inimaPeCamera = true;
                    objects.add(0,new Inima((j) * ChestiiStatice.tileDimension, ChestiiStatice.tileDimension * (i) + 5, 2.8f, ID.Inima));
                }
                if(toSpawn == 1 && hasEnemies()) {
                    toBeLocked = true;
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
