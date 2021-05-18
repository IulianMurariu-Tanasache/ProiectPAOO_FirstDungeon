package SQLite;

import Dungeon.Dungeon;
import Enemies.Enemy;
import GameObject.GameObject;
import GameStates.GameState;
import Player.Player;
import Player.States.*;
import Room.*;
import SpriteSheet.MapSheet;

import java.sql.*;

public class SQLite {

    private Connection c;
    private static SQLite instance = null;


    public static SQLite getInstance() {
        return instance;
    }

    public static void setInstance() {
        instance = new SQLite();
    }

    private SQLite() {
        c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:gameSaveTable.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void saveSetari() {
        try {
            String statemant;

            statemant = "UPDATE gameState SET "
            + "musicOn = ? , "
            + "soundOn = ?";

            PreparedStatement stm = c.prepareStatement(statemant);

            stm.setString(1, GameState.isMusicOn() ? "1" : "0");
            stm.setString(2, GameState.isSoundOn() ? "1" : "0");
            stm.executeUpdate();

            statemant = "DELETE FROM scores";
            Statement st = c.createStatement();
            st.execute(statemant);

            statemant = "INSERT INTO scores (score,pos) VALUES (?,?)";

            stm = c.prepareStatement(statemant);

            for(int i = 0; i < GameState.getScores().length; ++i)
            {
                stm.setString(1, "" + GameState.getScores()[i]);
                stm.setString(2, "" + i);
                stm.executeUpdate();
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Dungeon d = Dungeon.getInstance();
            Player p = Player.getInstance();

            System.out.println("Saving...");

            //stm.execute("INSERT INTO dungeon (dimX,dimY,outside,indexOut,cRow,cColumn) VALUES (5,5,1,0,0,0)");
            String statemant = "UPDATE dungeon SET dimX = ? , dimY = ? , outside = ? , indexOut = ? , cRow = ? , cColumn = ?";
            PreparedStatement stm = c.prepareStatement(statemant);

            stm.setString(1,"" + d.getDimX());
            stm.setString(2,"" + d.getDimY());
            stm.setString(3,d.isOutside() ? "1" : "0");
            stm.setString(4,"" + d.getIndexOut());
            stm.setString(5,"" + d.getcRow());
            stm.setString(6,"" + d.getcColumn());
            stm.executeUpdate();

            statemant = "UPDATE player SET armed = ? , "
                    + "state = ? , "
                    + "health = ? , "
                    + "stamina = ? , "
                    + "x = ? , "
                    + "y = ?";
            stm = c.prepareStatement(statemant);

            String state = "undefined";
            if(p.getState() instanceof Attack || p.getState() instanceof  Dash)
                state = "Idle";
            if(p.getState() instanceof Crouch)
                state = "Crouch";
            if(p.getState() instanceof Idle)
                state = "Idle";
            if(p.getState() instanceof Jump)
                state = "Jump";
            if(p.getState() instanceof Walk)
                state = "Walk";
            if(p.getState() instanceof Fall)
                state = "Fall";

            stm.setString(1,p.isArmed() ? "1" : "0");
            stm.setString(2,state);//de facut;
            stm.setString(3,"" + p.getHealth());
            stm.setString(4,"" + p.getStamina());
            stm.setString(5,"" + p.getX());
            stm.setString(6,"" + p.getY());
            stm.executeUpdate();

            statemant = "UPDATE gameState SET "
                    //+ "musicOn = ? , "
                    //+ "soundOn = ? , "
                    + "treasureFound = ? , "
                    + "score = ? , "
                    + "diff = ?";
            stm = c.prepareStatement(statemant);

            stm.setString(1,GameState.isFoundTreasure() ? "1" : "0");
            stm.setString(2,"" + GameState.getScore());
            stm.setString(3,"" + GameState.getDiff());
            stm.executeUpdate();

            Statement executat = c.createStatement();
            statemant = "DELETE FROM room";
            executat.execute(statemant);
            statemant = "DELETE FROM gameObject";
            executat.execute(statemant);

            PreparedStatement stm1 = c.prepareStatement("INSERT INTO room (type,toBeLocked,whatRoom,conf) VALUES (?,?,?,?)");
            PreparedStatement stm3 = c.prepareStatement("INSERT INTO gameObject (y,id,health,whatRoom,startX) VALUES (?,?,?,?,?)");

            Room[][] r = d.getRooms();
            for(int i = 0; i < d.getDimY(); ++i) {
                for(int j = 0; j < d.getDimX(); ++j) {
                    System.out.print(".");
                    if (r[i][j] != null) {

                        int room = i * (d.getDimX()) + j;
                        stm1.setString(1, "" + r[i][j].getType());
                        stm1.setString(2, r[i][j].isToBeLocked() ? "1" : "0");
                        stm1.setString(3, "" + room);

                        String con = "";
                        int[][][] conf = r[i][j].getConfig();
                        for (int q = 0; q < 9; ++q) {
                            System.out.print(",");
                            for (int k = 0; k < 16; ++k) {
                                con += conf[0][q][k] + " " +  conf[1][q][k] + ",";
                            }
                        }

                        stm1.setString(4,con);
                        stm1.execute();

                        for (GameObject obj : r[i][j].getObjects()) {

                            System.out.print("?");
                            stm3.setString(1, "" + obj.getY());
                            stm3.setString(2, "" + obj.getId().ordinal());

                            int h = 0;
                            int startX = 0;
                            if (obj.isEnemy()) {
                                h = ((Enemy) obj).getHealth();
                                startX = ((Enemy) obj).getStartX();
                            }
                            else
                                startX = obj.getX();

                            stm3.setString(3, "" + h);
                            stm3.setString(4, "" + room);
                            stm3.setString(5, "" + startX);
                            stm3.execute();
                        }
                    }
                }
            }

            System.out.println("Saved!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void loadGameState() throws SQLException {

        String statemant = "SELECT musicOn,soundOn FROM gameState";
        Statement stm = c.createStatement();
        ResultSet set = stm.executeQuery(statemant);

        Statement altst = c.createStatement();
        statemant = "SELECT * FROM scores";
        ResultSet scoreSet = altst.executeQuery(statemant);

        GameState.load(set,scoreSet);
    }

    public void loadGame(MapSheet map) throws SQLException {

        System.out.println("Loading...");

        String statemant = "SELECT treasureFound,score,diff FROM gameState";
        Statement stm = c.createStatement();
        ResultSet set = stm.executeQuery(statemant);

        GameState.setDiff(set.getInt("diff"));
        GameState.setScore(set.getInt("score"));
        GameState.setFoundTreasure(set.getBoolean("treasureFound"));

        statemant = "SELECT * FROM dungeon";
        Statement st1 = c.createStatement();
        ResultSet dungeonSet = st1.executeQuery(statemant);
        statemant = "SELECT * FROM room";
        Statement st2 = c.createStatement();
        ResultSet roomSet = st2.executeQuery(statemant);
        statemant = "SELECT * FROM gameObject";
        Statement st3 = c.createStatement();
        ResultSet objSet = st3.executeQuery(statemant);

        Dungeon.load(dungeonSet,roomSet,objSet, map);

        statemant = "SELECT * FROM player";
        set = stm.executeQuery(statemant);
        Player.load(set);

        st1.close();
        st2.close();
        st3.close();
        stm.close();
    }

    public void closeConnection() {
        try {
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
