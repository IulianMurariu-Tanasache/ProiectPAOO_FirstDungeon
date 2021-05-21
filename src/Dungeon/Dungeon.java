package Dungeon;

import Enemies.Ciuperca;
import Enemies.Eye;
import Enemies.Scheletron;
import Enemies.Slime;
import GameObject.*;
import GameStates.GameState;
import GameStates.GameWinState;
import Room.Room;
import Room.RoomInterior;
import Room.RoomOutdoor;
import SQLite.NotLoadedException;
import SpriteSheet.MapSheet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/*! \class Dungeon
    \brief Clasa care se ocupa cu generarea si gestiunea intregii temnite.

    Clasa se ocupa de generarea procedurala a intregii temnitei, dar nu a camerelor individuale. Are si functie de incarcare din SQL pentru a continua un joc deja niceput.
    Este singleton.
 */
public class Dungeon {

    private Room[][] rooms;
    private int dimX = 5;
    private int dimY = 5;
    private boolean outside;
    private Room[] out;
    private int indexOut;
    private int cRow;
    private int cColumn;
    private static Dungeon instance = null;

    public Dungeon() {

    }

    public static void load(ResultSet dungeonSet,ResultSet roomSet,ResultSet objectsSet, MapSheet map) throws NotLoadedException {
        try {
            Room.setSheet(map);
            instance = new Dungeon();
            try {
                instance.dimX = dungeonSet.getInt("dimX");
                instance.dimY = dungeonSet.getInt("dimY");
                if(instance.dimX == 0 || instance.dimY == 0)
                    throw new NotLoadedException();
                instance.outside = dungeonSet.getBoolean("outside");
                instance.indexOut = dungeonSet.getInt("indexOut");
                instance.cRow = dungeonSet.getInt("cRow");
                instance.cColumn = dungeonSet.getInt("cColumn");
                instance.out = new Room[2];
                instance.out[0] = new RoomOutdoor(4);
                instance.out[1] = new RoomOutdoor(5);
            } catch (SQLException e) {
                throw new NotLoadedException();
            }
            roomSet.next();
            objectsSet.next();
            instance.rooms = new Room[instance.dimY][instance.dimX];
            for(int i = 0; i < instance.dimY; ++i)
                for(int j = 0; j < instance.dimX; ++j) {
                    int room = i * (instance.dimX) + j;
                    try {
                        while (room > objectsSet.getInt("whatRoom"))
                            objectsSet.next();
                    } catch (SQLException e) {
                        System.out.println("ObjectsList done!");
                    }
                    if(room == roomSet.getInt("whatRoom")) {
                        String[] con = roomSet.getString("conf").split(",");
                        int[][][] conf = new int[2][9][16];
                        for (int q = 0; q < 9; ++q) {
                            for (int k = 0; k < 16; ++k) {
                                String[] temp = con[q * 16 + k].split(" ");
                                conf[0][q][k] = Integer.parseInt(temp[0]);
                                conf[1][q][k] = Integer.parseInt(temp[1]);
                            }
                        }

                        ArrayList<GameObject> objList = new ArrayList<GameObject>();
                        try{
                            while(objectsSet.getInt("whatRoom") == room) {
                                switch (objectsSet.getInt("id")) {
                                    case 5://ciuperca
                                        objList.add(new Ciuperca(objectsSet.getInt("startX"), objectsSet.getInt("y"), 1.8f, ID.Enemy_Ciuperca));
                                        break;
                                    case 3://eye
                                        objList.add(new Eye(objectsSet.getInt("startX"), objectsSet.getInt("y"), 1.5f, ID.Enemy_Eye));
                                        break;
                                    case 2://goblin
                                        objList.add(new Eye(objectsSet.getInt("startX"), objectsSet.getInt("y"), 1.7f, ID.Enemy_Goblin));
                                        break;
                                    case 4://slime
                                        objList.add(new Slime(objectsSet.getInt("startX"), objectsSet.getInt("y"), 2.9f, ID.Enemy_Slime));
                                        break;
                                    case 1://skeleton
                                        objList.add(new Scheletron(objectsSet.getInt("startX"), objectsSet.getInt("y"), 1.6f, ID.Enemy_Skeleton));
                                        break;
                                    case 6://comoara
                                        objList.add(new Comoara(objectsSet.getInt("startX"), objectsSet.getInt("y"), 0.9f, ID.Comoara));
                                        break;
                                    case 8://inima
                                        objList.add(new Inima(objectsSet.getInt("startX"), objectsSet.getInt("y"), 2.8f, ID.Inima));
                                        break;
                                }
                                objectsSet.next();
                            }
                        } catch(SQLException e) {
                            System.out.println("ObjectsList done!");
                        } finally {
                            instance.rooms[i][j] = new RoomInterior(roomSet.getInt("type"),roomSet.getBoolean("toBeLocked"), conf, objList);
                            roomSet.next();
                        }
                    }
                }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private Dungeon(MapSheet map) throws IOException {
        Room.setSheet(map);

        if(GameState.getDiff() == 1) {
            dimX = 7;
            dimY = 7;
        }

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
        System.out.println(i + " " + j);
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
                else
                    tip = rand.nextInt(2) + 2;
            } else {
                i--;
                if(i == 0)
                    tip = 1;
                else
                    tip = rand.nextInt(2) + 1;
                rooms[i][j] = new RoomInterior(tip,0);
            }

            if(j == dimX - 1)
                rooms[i][j] = new RoomInterior(tip, 2);
            else if(j == 0)
                rooms[i][j] = new RoomInterior(tip, 1);
            else
                rooms[i][j] = new RoomInterior(tip, 0);

            oldtip = tip;
            //olddir e inversul lui dir
            switch(dir) {
                default -> olddir = dir;
                case 0 -> olddir = 2;
                case 2 -> olddir = 0;
            }
        }

        System.out.println(i + " " + j);
        rooms[i][j - 1] = new RoomInterior(tip, 0);
        rooms[i][j] = new RoomInterior(0,2);
        rooms[i][j].clear();
        rooms[i][j].add(new Comoara(896,375,0.9f, ID.Comoara));
        rooms[i][j].add(new SpeechBubbles(60, 320, 200, 100, 0.32f, "Assets/SpeechBubbles/attack_bubble.gif"));

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
                        else if(rooms[i+1][j] != null && (rooms[i+1][j].getType() == 3 || rooms[i+1][j].getType() == 2) && !(rooms[i-1][j].getType() == 1 || rooms[i-1][j].getType() == 2))
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
        outside = true;
        indexOut = 0;
        dimX++;
    }

    //tipuri de camera: 0 - stanga/dreapta; 1 - +jos; 2 - all; 3 - +sus
    //dir: 0 - jos, 1 - stanga, 2 - sus
    private int generateDir(int i, int oldtip, Random rand) {
        switch (oldtip) {
            case 0, 2 -> {
                if(i == dimY-1)
                    return rand.nextInt(2) + 1;
                if(i == 0)
                    return rand.nextInt(2);
                return rand.nextInt(3);
            }
            case 1 -> {
                if(i == dimY-1)
                    return 1;
                return rand.nextInt(2);
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
            if(outside && GameState.isFoundTreasure()) {
                GameState.setNext(new GameWinState());
            }
            return false;
        }
        boolean end = false;
        if(rooms[cRow][cColumn].isLocked()) {
            return true;
        }
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
       /* if(!outside && Player.getInstance().isArmed() && rooms[cRow][cColumn].isToBeLocked()) {
            rooms[cRow][cColumn].setToBeLocked(false);
            rooms[cRow][cColumn].setLocked(true);

        }*/
        return end;
    }

    public static Dungeon getInstance() {
        return instance;
    }

    public static void newInstance(MapSheet ms) throws IOException {
        instance = new Dungeon(ms);
    }

    public boolean isOutside() {
        return outside;
    }

    public Room[][] getRooms() {
        return rooms;
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public int getIndexOut() {
        return indexOut;
    }

    public int getcRow() {
        return cRow;
    }

    public int getcColumn() {
        return cColumn;
    }
}
