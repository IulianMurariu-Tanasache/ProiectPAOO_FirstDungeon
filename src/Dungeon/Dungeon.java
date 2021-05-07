package Dungeon;

import GameObject.Comoara;
import GameObject.ID;
import Room.*;
import SpriteSheet.MapSheet;

import java.io.IOException;
import java.util.Random;

public class Dungeon {

    private Room[][] rooms;
    private int dimX = 5;
    private final int dimY = 5;
    private boolean outside;
    private Room[] out;
    private int indexOut;
    private int cRow;
    private int cColumn;
    private static Dungeon instance = null;

    private Dungeon(MapSheet map) throws IOException {
        Room.setSheet(map);
        Room.loadBack();

        int i;
        int j = 0;
        rooms = new Room[dimY][dimX + 1];
        for(Room[] rr : rooms)
            for(Room r : rr)
                r = null;
        Random rand = new Random();
        i = rand.nextInt(dimY - 2) + 1;
        int dir;
        int tip = rand.nextInt(4);
        rooms[i][j] = new RoomInterior(tip ,0);
        int oldtip = tip;
        int olddir = 1;
        cRow = i;
        cColumn = 0;

        //tipuri de camera: 0 - stanga/dreapta; 1 - +jos; 2 - all; 3 - +sus
        //dir: 0 - jos, 1 - stanga, 2 - sus

        while (j <=  dimX - 1) {
            dir = generateDir(i,oldtip,rand);
            if(olddir == dir)
                dir = 1;

            if (dir == 1) {
                ++j;
                rooms[i][j] = new RoomInterior(tip, 0);
            } else if (dir == 0) {
                i++;
                if(i == dimY - 1)
                    tip = 3;
                else if (oldtip == 1 || oldtip == 2)
                    tip = rand.nextInt(2) + 2;
                rooms[i][j] = new RoomInterior(tip,0);
            } else {
                i--;
                if(i == 0)
                    tip = 1;
                else if(oldtip == 2 || oldtip == 3)
                    tip = rand.nextInt(2) + 1;
                rooms[i][j] = new RoomInterior(tip,0);
            }

            oldtip = tip;
            //olddir e inversul lui dir
            switch(dir) {
                default -> olddir = dir;
                case 0 -> olddir = 2;
                case 2 -> olddir = 0;
            }
        }

        rooms[i][j] = new RoomInterior(0,2);
        //rooms[i][j].add(new Comoara(896,375,0.9f, ID.Comoara));

        //tipuri de camera: 0 - stanga/dreapta; 1 - +jos; 2 - all; 3 - +sus
        //dir: 0 - jos, 1 - stanga, 2 - sus
        for(i = 0; i < dimY; ++i)
            for(j = 0; j < dimX; ++j) {
                if(rooms[i][j] == null) {
                    if(i == 0) {
                        if(rooms[i+1][j] != null && rooms[i+1][j].getType() >= 2)
                            tip = 1;
                        else if(rooms[i+1][j] != null && rooms[i+1][j].getType() < 2)
                            tip = 0;
                        else if(rooms[i+1][j] == null)
                            tip = rand.nextInt(2);
                        if(j == dimX - 1)
                            rooms[i][j] = new RoomInterior(tip, 2);
                        else if(j == 0)
                            rooms[i][j] = new RoomInterior(tip, 1);
                        else
                            rooms[i][j] = new RoomInterior(tip, 0);
                    }
                    else if(i == dimY - 1) {
                        if(rooms[i-1][j].getType() == 1 || rooms[i-1][j].getType() == 2)
                            tip = 3;
                        else
                            tip = 0;
                        if(j == dimX - 1)
                            rooms[i][j] = new RoomInterior(tip, 2);
                        else if(j == 0)
                            rooms[i][j] = new RoomInterior(tip, 1);
                        else
                            rooms[i][j] = new RoomInterior(tip, 0);
                    }
                    else {
                        if((rooms[i+1][j] != null && (rooms[i+1][j].getType() == 3 || rooms[i+1][j].getType() == 2)) && (rooms[i-1][j].getType() == 1 || rooms[i-1][j].getType() == 2))
                            tip = 2;
                        else if(rooms[i+1][j] != null && (rooms[i+1][j].getType() == 3 || rooms[i+1][j].getType() == 2))
                            tip = 1;
                        else if(rooms[i-1][j].getType() == 1 || rooms[i-1][j].getType() == 2)
                            tip = 3;
                        else
                            tip = 0;
                        if(j == dimX - 1)
                            rooms[i][j] = new RoomInterior(tip, 2);
                        else if(j == 0)
                            rooms[i][j] = new RoomInterior(tip, 1);
                        else
                            rooms[i][j] = new RoomInterior(tip, 0);
                    }
               }
            }
        out = new Room[2];
        out[0] = new RoomOutdoor (4);
        out[1] = new RoomOutdoor(5);
        out[1].add(new Comoara(896,375,0.9f, ID.Comoara));
        outside = true;
        indexOut = 0;
        dimX++;
    }

    //tipuri de camera: 0 - stanga/dreapta; 1 - +jos; 2 - all; 3 - +sus
    //dir: 0 - jos, 1 - stanga, 2 - sus
    private int generateDir(int i, int oldtip, Random rand) {
        switch (oldtip) {
            case 0 -> {
                return 1;
            }
            case 1 -> {
                if(i == dimY-1)
                    return 1;
                return rand.nextInt(2);
            }
            case 2 -> {
                if(i == 0 || i == dimY - 1)
                    return 1;
                return rand.nextInt(3);
            }
            case 3 -> {
                if(i == 0)
                    return 1;
                return 1 + rand.nextInt(2);
            }
        }
        return 1;
    }

    public Room getRoom()
    {
        if(outside) {
            return out[indexOut];
        }
        return rooms[cRow][cColumn];
    }

    public boolean nextRoom(int l, int c)
    {
        if(outside) {
            if(c == 1)
                indexOut++;
            else if(c == -1) {
                if(indexOut <= 0)
                    return true;
                indexOut--;
            }
            if(l != 0)
                return true;
            if(indexOut > 1)
                outside = false;
            return false;
        }
        boolean end = false;
        cRow += l;
        cColumn += c;
        if(cRow >= dimY){
            cRow = dimY - 1;
            end = true;
        }
        else if(cRow < 0){
            cRow = 0;
            end = true;
        }
        if(cColumn >= dimX){
            cColumn = dimX - 1;
            end = true;
        }
        else if(cColumn < 0){
            cColumn = 0;
            outside = true;
            indexOut = 1;
            end = false;
        }
        return end;
    }

    public static Dungeon getInstance() {
        return instance;
    }

    public static void newInstance(MapSheet ms) throws IOException {
        instance = new Dungeon(ms);
    }
}
