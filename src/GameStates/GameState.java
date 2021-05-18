package GameStates;

import ChestiiRandom.ChestiiStatice;
import GUI.Commands.*;
import GUI.Elements.Button;
import GUI.Elements.*;
import Game.Game;
import Game.Window;
import Input.MouseListener;
import SQLite.SQLite;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class GameState {

    protected static GameState prev = null;
    protected static GameState next = null;
    protected static MenuParallax menuBack;
    protected static Panou pausedPanel;
    protected static Panou deathPanel;
    protected static Panou WinPanel;
    protected static Panou diffPanel;
    protected static ArrayList<UI_Elemenent> MenuUI = new ArrayList<>();
    protected static ArrayList<UI_Elemenent> GameUI = new ArrayList<>();
    protected static ArrayList<UI_Elemenent> SettingsUI = new ArrayList<>();
    protected static ArrayList<UI_Elemenent> LeaderboardUI = new ArrayList<>();
    private static GameState instance = null;
    private static boolean musicOn;
    private static boolean soundOn;
    private static boolean running;
    protected static boolean foundTreasure;
    protected static int score;
    protected static int diff;
    protected static boolean toLoad = false;
    protected static int[] scores;

    public static boolean isToLoad() {
        return toLoad;
    }

    public static void setToLoad(boolean toLoad) {
        GameState.toLoad = toLoad;
    }

    public static GameState getInstance() {
        return instance;
    }

    public static void load(ResultSet set, ResultSet scoreSet) {
        try {
            musicOn = set.getBoolean("musicOn");
            soundOn = set.getBoolean("soundOn");

            while(scoreSet.next()) {
                GameState.scores[scoreSet.getInt("pos")] = scoreSet.getInt("score");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void setInstance(Game game) {
        GameState.scores = new int[7];
        try {
            SQLite.getInstance().loadGameState();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        running = true;
        foundTreasure = false;
        UI_Elemenent.setFont(new Font("Cooper Black",Font.BOLD,24));
        Window window = Window.getInstance();

        MouseListener mouseInput = new MouseListener();
        game.addMouseListener(mouseInput);

        //MenuUI
        menuBack = new MenuParallax("Assets/SpriteSheets");

        MenuUI.add(menuBack);

        Button idk = new Button(window.getWidth() / 2 - 150 - 40,200,150,70,"START",new ToDiffPanelCommand());
        mouseInput.add(idk);
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        MenuUI.add(idk);

        idk = new Button(window.getWidth() / 2 +40,200,150,70,"CONTINUE",new LoadCommand());
        mouseInput.add(idk);
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        MenuUI.add(idk);

        idk = new Button(window.getWidth() / 2 - 150 - 40,300,150,70,"SETTINGS",new ToSettingsCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        MenuUI.add(idk);
        mouseInput.add(idk);

        idk = new Button(window.getWidth() / 2 +40,300,150,70,"SCORE",new ToLeaderBoardCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        MenuUI.add(idk);
        mouseInput.add(idk);

        idk = new Button(window.getWidth() / 2 - 75,400,150,70,"QUIT",new QuitCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        MenuUI.add(idk);
        mouseInput.add(idk);

        Text title = new Text(180,150, "FIRST DUNGEON");
        title.setSize(75);
        title.setTextColor(ChestiiStatice.mov);
        MenuUI.add(title);

        //difficulty select panel
        diffPanel = new Panou(332,70,360,375);
        diffPanel.setBackColor(new Color(14, 176, 106,180));
        diffPanel.setVisible(false);

        title = new Text(333,140, "Select difficulty");
        title.setSize(41);
        title.setTextColor(ChestiiStatice.mov);
        diffPanel.add(title);

        idk = new Button(432,169,160,70,"EASY", new EasyCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        diffPanel.add(idk);

        idk = new Button(432,261,160,70,"HARD", new HardCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        diffPanel.add(idk);

        idk = new Button(432,353,160,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        diffPanel.add(idk);

       // GameUI
        //panou pause
        pausedPanel = new Panou(332,70,360,375);
        pausedPanel.setBackColor(new Color(14, 176, 106,180));
        //GameUI.add(pausedPanel);

        title = new Text(340,130, "GAME PAUSED");
        title.setSize(42);
        title.setTextColor(ChestiiStatice.mov);
        pausedPanel.add(title);

        idk = new Button(432,169,160,70,"UNPAUSE", new BackCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        pausedPanel.add(idk);

        idk = new Button(432,261,160,70,"SAVE", new SaveComand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        pausedPanel.add(idk);

        idk = new Button(432,353,160,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        pausedPanel.add(idk);

        //panou death
        deathPanel = new Panou(332,70,360,375);
        deathPanel.setBackColor(new Color(34, 156, 126,180));
        //GameUI.add(deathPanel);

        title = new Text(380,130, "YOU DIED!");
        title.setSize(42);
        title.setTextColor(ChestiiStatice.mov);
        deathPanel.add(title);

        idk = new Button(432,169,160,70,"RESTART", new ToGameCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        deathPanel.add(idk);

        idk = new Button(432,353,160,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        deathPanel.add(idk);

        //panou Win
        WinPanel = new Panou(332,70,360,375);
        WinPanel.setBackColor(ChestiiStatice.verzui);
        //GameUI.add(WinPanel);

        title = new Text(380,130, "YOU WON!");
        title.setSize(42);
        title.setTextColor(ChestiiStatice.mov);
        WinPanel.add(title);

        idk = new Button(432,169,160,70,"START AGAIN", new ToDiffPanelCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        WinPanel.add(idk);

        idk = new Button(432,353,160,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        WinPanel.add(idk);

        //GameUI.add(title);

        StatsBar stats = new StatsBar(20,20);
        stats.setVisible(false);
        GameUI.add(stats); //0

        idk = new Button(900,60,100,60,"PAUSE",new PausePanelCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        GameUI.add(idk); //1

        title = new Text(835,40, "Score: 0");
        title.setSize(35);
        title.setTextColor(ChestiiStatice.verzui);
        GameUI.add(title); //2

        //setari UI
        SettingsUI.add(menuBack);

        CheckBox box = new CheckBox(100,100,70,70,"MUSIC", 30, new MusicOnCommand());
        mouseInput.add(box);
        box.setChecked(musicOn);
        SettingsUI.add(box);

        box = new CheckBox(100,200,70,70,"SOUND EFFECTS", 30, new SoundOnCommand());
        mouseInput.add(box);
        box.setChecked(soundOn);
        SettingsUI.add(box);

        idk = new Button(window.getWidth() / 2 - 75,400,150,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        SettingsUI.add(idk);

        //LeaderboardUI

        LeaderboardUI.add(menuBack);

        idk = new Button(window.getWidth() / 2 - 75,450,150,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(ChestiiStatice.verzui);
        mouseInput.add(idk);
        LeaderboardUI.add(idk);//1

        title = new Text(380,40, "Leaderboard");
        title.setSize(45);
        title.setTextColor(ChestiiStatice.mov);
        LeaderboardUI.add(title); //2

        ScorePanel scorePanou = new ScorePanel(0,100,1024,340);
        LeaderboardUI.add(scorePanou); //3

        title = new Text(130,80, "The goal is to achieve the smallest score possible.");
        title.setSize(25);
        title.setTextColor(ChestiiStatice.mov);
        LeaderboardUI.add(title); //2

        instance = new MenuState();
        instance.init();
    }

    public static void setDiff(int i) {
        diff = i;
    }

    public static int getDiff() {
        return diff;
    }

    public abstract void init();
    public abstract void render(Graphics g);
    public abstract void tick();
    public abstract void clearUI();

    public void nextState() {
        if(next != null)
        {
            instance.clearUI();
            prev = instance;
            instance = next;
            next = null;
            instance.init();
        }
    }

    public static void prevState() {
        next = prev;
    }

    public static void setNext(GameState next) {
        GameState.next = next;
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        GameState.running = running;
    }

    public static boolean isMusicOn() {
        return musicOn;
    }

    public static void setMusicOn(boolean musicOn) {
        GameState.musicOn = musicOn;
    }

    public static boolean isSoundOn() {
        return soundOn;
    }

    public static void setSoundOn(boolean soundOn) {
        GameState.soundOn = soundOn;
    }

    public static boolean isFoundTreasure() {
        return foundTreasure;
    }

    public static void setFoundTreasure(boolean foundTreasure) {
        GameState.foundTreasure = foundTreasure;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameState.score = score;
    }

    public static int[] getScores() {
        return scores;
    }
}
