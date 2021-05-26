package Game;

import Enemies.Enemy;
import GameStates.GameState;
import Input.KeyInput;
import Player.States.PlayerState;
import SQLite.SQLite;
import SoundTrack.Music;
import SoundTrack.SoundManager;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

    Implementeaza interfata Runnable;
    Extinde clasa Canvas;
    Controleaza tot jocul.
 */
public class Game extends Canvas implements Runnable {

    private boolean isRunning = false;  /*!< Flag ce retine starea jocului: Ruleaza sau nu.*/
    private Thread music_thread;

    /*! @fn Game(String title, int width, int height) ar trebui sa pun parametrii XD
        @brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    Game()
    {
        /*try {
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Seagram_tfb.ttf")));
            //System.out.println(Arrays.toString(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }*/

        Window.setInstance(this);
        KeyInput keyInput = new KeyInput();
        this.addKeyListener(keyInput);
        PlayerState.setInput(keyInput);
        Enemy.loadAnimations();
        SoundManager.setSoundManager();
        SQLite.setInstance();
        GameState.setInstance(this);

        start();
    }

    /*! @fn private void start()
        @brief Metoda seteaza flag-ul de rulare si creeaza noul game_thread pentru loop.

     */
    private void start()
    {
        isRunning = true;
        try {
            music_thread = new Thread(Music.getInstance(new String[]{"Assets/40_1 .wav","Assets/40_1 (2).wav","Assets/40_1 (3).wav"}));
            music_thread.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /*! @fn private void stop()
     *  @brief Metoda seteaza flagul de rualre pe false si asteapta ca game_thread-ul sa isi incheie executia
     */
    private void stop()
    {
        isRunning = false;
        try{
            Music.getInstance(null).stop();
            music_thread.join(500);
        }catch(InterruptedException | UnsupportedAudioFileException | LineUnavailableException | IOException e)
        {
            e.printStackTrace();
        }
        SQLite.getInstance().saveSetari();
        SQLite.getInstance().closeConnection();
        Window.getInstance().getWindow().dispose();
    }

    /*! @fn public void run()
     *  @brief Metoda suprascrisa run() din Runnable.Aici are loc loop-ul jocului.
     *  amountOfTicks reprezitna framrate-ul dorit.
     *  Pe parcursul jocului se vor face calcule pentru a afla durata unei secvente de while, pentru a realiza update doar de 60 de ori pe secunda.
     *  Render se apeleaza cat de repede poate jocul - poate trebuie modificat - animatiile sunt limitate prin clasa Animation
     */
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(isRunning)
        {
            GameState.getInstance().nextState();
            long now = System.nanoTime();
            //elapsed = now - lastTime;
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1)
            {
                tick();
                delta--;
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                //System.out.println(frames);
                frames = 0;
            }
            isRunning = GameState.isRunning();
        }
        stop();
    }

    /*! @fn private void tick()
     *  @brief Functia care se ocupa de update pentru obiectele jocului.
     *  Doar apeleaza functia tick() din Handler
     */
    private void tick()
    {
        GameState.getInstance().tick();
    }

    /*! @fn private void render(double elapsed)
     *  @brief Functia de desenare.
     *
     *  Se foloseste de un bufferSrtategy de 2 (poate 3). Goleste ecranul, cheama functia de render(Graphics, double) din Handler si afiseaza.
     *
     * @param elapsed Timpul trecut intre 2 apelari consecutive ale functiei. Util in limitarea animatiilor.
     *
     */
    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g  = bs.getDrawGraphics();

        GameState.getInstance().render(g);

        bs.show();
        g.dispose();

    }

    public boolean isRunning() {
        return isRunning;
    }

    /*! @fn main()
     * @brief E main-ul...doar creeaza un nou joc...
     * @param args
     */
    public static void main(String[] args)
    {
        Thread game_thread;                         /*!< game_thread-ul ce va fi creat cu loop-ul jocului.*/
        game_thread = new Thread(new Game());
        game_thread.start();
        try {
            game_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
