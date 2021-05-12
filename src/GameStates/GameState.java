package GameStates;

import GUI.Commands.*;
import GUI.Elements.*;
import Game.*;
import Input.MouseListener;
import SpriteSheet.SpriteSheet;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GameState {

    protected static GameState prev = null;
    protected static GameState next = null;
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

    public static GameState getInstance() {
        return instance;
    }

    public static void setInstance(Game game) {
        running = true;
        UI_Elemenent.setFont(new Font("Cooper Black",Font.BOLD,24));
        Window window = Window.getInstance();

        MouseListener mouseInput = new MouseListener();
        game.addMouseListener(mouseInput);

        //MenuUI
        try {
            MenuUI.add(new Imagine(0,0, window.getWidth(), window.getHeight(), new SpriteSheet("menuBack","")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button idk = new Button(window.getWidth() / 2 - 150 - 40,200,150,70,"START",new ToGameCommand());
        mouseInput.add(idk);
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        MenuUI.add(idk);

        idk = new Button(window.getWidth() / 2 +40,200,150,70,"CONTINUE",new NothingCommand());
        mouseInput.add(idk);
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        MenuUI.add(idk);

        idk = new Button(window.getWidth() / 2 - 150 - 40,300,150,70,"SETTINGS",new ToSettingsCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        MenuUI.add(idk);

        mouseInput.add(idk);
        idk = new Button(window.getWidth() / 2 +40,300,150,70,"SCORE",new ToLeaderBoardCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        MenuUI.add(idk);

        mouseInput.add(idk);
        idk = new Button(window.getWidth() / 2 - 75,400,150,70,"QUIT",new QuitCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        MenuUI.add(idk);
        mouseInput.add(idk);

        Text title = new Text(180,150, "FIRST DUNGEON");
        title.setSize(75);
        title.setTextColor(new Color(70,12,89));
        MenuUI.add(title);

       // GameUI
        //panou pause 0
        Panou panel = new Panou(332,70,360,375);
        panel.setBackColor(new Color(14, 176, 106,180));
        GameUI.add(panel);

        title = new Text(340,130, "GAME PAUSED");
        title.setSize(42);
        title.setTextColor(new Color(70,12,89));
        panel.add(title);

        idk = new Button(432,169,160,70,"UNPAUSE", new BackCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        mouseInput.add(idk);
        panel.add(idk);

        idk = new Button(432,261,160,70,"SAVE", new NothingCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        mouseInput.add(idk);
        panel.add(idk);

        idk = new Button(432,353,160,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        mouseInput.add(idk);
        panel.add(idk);

        //panou death 1
        panel = new Panou(332,70,360,375);
        panel.setBackColor(new Color(34, 156, 126,180));
        GameUI.add(panel);

        title = new Text(380,130, "YOU DIED!");
        title.setSize(42);
        title.setTextColor(new Color(70,12,89));
        panel.add(title);

        idk = new Button(432,169,160,70,"RESTART", new ToGameCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        mouseInput.add(idk);
        panel.add(idk);

        idk = new Button(432,353,160,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        mouseInput.add(idk);
        panel.add(idk);

        StatsBar stats = new StatsBar(20,20);
        stats.setVisible(false);
        GameUI.add(stats); //2

        idk = new Button(900,60,100,60,"PAUSE",new PausePanelCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        mouseInput.add(idk);
        GameUI.add(idk); //3

        title = new Text(835,40, "Score: 0");
        title.setSize(35);
        title.setTextColor(new Color(11,132,80));
        GameUI.add(title); //4

        //setari UI
        try {
            SettingsUI.add(new Imagine(0,0, window.getWidth(), window.getHeight(), new SpriteSheet("menuBack","")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CheckBox box = new CheckBox(100,100,70,70,"MUSIC", 30, new MusicOnCommand());
        mouseInput.add(box);
        SettingsUI.add(box);

        box = new CheckBox(100,200,70,70,"SOUND EFFECTS", 30, new SoundOnCommand());
        mouseInput.add(box);
        SettingsUI.add(box);

        idk = new Button(window.getWidth() / 2 - 75,400,150,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        mouseInput.add(idk);
        SettingsUI.add(idk);

        //LeaderboardUI

        try {
            LeaderboardUI.add(new Imagine(0,0, window.getWidth(), window.getHeight(), new SpriteSheet("menuBack","")));
        } catch (IOException e) {
            e.printStackTrace();
        }//0

        idk = new Button(window.getWidth() / 2 - 75,450,150,70,"MENU", new ToMenuCommand());
        idk.setTextColor(Color.WHITE);
        idk.setBackColor(new Color(11,132,80));
        mouseInput.add(idk);
        LeaderboardUI.add(idk);//1

        title = new Text(380,40, "Leaderboard");
        title.setSize(45);
        title.setTextColor(new Color(70,12,89));
        LeaderboardUI.add(title); //2

        ScorePanel scorePanou = new ScorePanel(0,100,1024,340);
        LeaderboardUI.add(scorePanou); //3

        title = new Text(130,80, "The goal is to achieve the smallest score possible.");
        title.setSize(25);
        title.setTextColor(new Color(70,12,89));
        LeaderboardUI.add(title); //2

        instance = new MenuState();
        instance.init();
    }

    public abstract void init();
    public abstract void render(Graphics g);
    public abstract void tick();
    public abstract void clearUI();

    public void nextState() {
        if(next != null)
        {
            clearUI();
            this.clearUI();
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
}
