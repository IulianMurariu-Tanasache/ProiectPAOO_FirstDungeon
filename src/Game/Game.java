package Game;

import Dungeon.Dungeon;
import GameObject.ID;
import Handler.Handler;
import Input.KeyInput;
import Player.Player;
import Player.States.PlayerState;
import SoundTrack.Music;
import SpriteSheet.MapSheet;

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

    /*
     *
     *  ATENTIUNE ! TASTA W VA FI FOLOSITA DOAR CAT TIMP JOCUL ESTE IN DEZVOLTARE. ESTE DOAR O MODALITATE DE TESTARE CARE VA FI SCOASA
     *  IN RELEASE.
     *
     */

    private boolean isRunning = false;  /*!< Flag ce retine starea jocului: Ruleaza sau nu.*/
    private Thread music_thread;
    private Thread UI_thread;
    private Thread game_thread;         /*!< game_thread-ul ce va fi creat cu loop-ul jocului.*/
    private Handler handler;            /*!< Obiect de tip Handler care ca retine toate obiectele joclului si se va ocupa de update si draw pentru fiecare.*/
    private KeyInput keyInput;          /*!< Obiect de tip KeyInput care se va ocupa de evenimentele legate de tastatura.*/

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
        Window.setInstance(this);
        keyInput = new KeyInput();
        this.addKeyListener(keyInput);
        PlayerState.setInput(keyInput);
        try {
            //map_sheet = new MapSheet("dungeon");
            Dungeon.newInstance(new MapSheet("dungeon"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler = new Handler();
        handler.add(new Player(70,300,2.5f, ID.Player,"adventurer1"));

        start();
    }

    /*! @fn private void start()
        @brief Metoda seteaza flag-ul de rulare si creeaza noul game_thread pentru loop.

     */
    private void start()
    {
        isRunning = true;
        game_thread = new Thread(this);
        /*try {
            music_thread = new Thread(Music.getInstance("Assets/soundtrack_game.wav"));
            music_thread.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }*/
        game_thread.start();
    }

    /*! @fn private void stop()
     *  @brief Metoda seteaza flagul de rualre pe false si asteapta ca game_thread-ul sa isi incheie executia
     */
    private void stop()
    {
        isRunning = false;
        try{
            //music_thread.join();
            game_thread.join();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
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
        double elapsed;
        while(isRunning)
        {
            long now = System.nanoTime();
            elapsed = now - lastTime;
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1)
            {
                tick();
                delta--;
            }
            render(elapsed);
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                //System.out.println(frames);
                frames = 0;
            }
        }
        stop();
    }

    /*! @fn private void tick()
     *  @brief Functia care se ocupa de update pentru obiectele jocului.
     *  Doar apeleaza functia tick() din Handler
     */
    private void tick()
    {
        handler.tick();
    }

    /*! @fn private void render(double elapsed)
     *  @brief Functia de desenare.
     *
     *  Se foloseste de un bufferSrtategy de 2 (poate 3). Goleste ecranul, cheama functia de render(Graphics, double) din Handler si afiseaza.
     *
     * @param elapsed Timpul trecut intre 2 apelari consecutive ale functiei. Util in limitarea animatiilor.
     *
     */
    private void render(double elapsed)
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g  = bs.getDrawGraphics();
        g.setColor(new Color(75,44,54));
        g.clearRect(0,0,1024,576);
        g.fillRect(0,0,1024, 576);

        Dungeon.getInstance().getRoom().render(g);
        handler.render(g, elapsed);

        bs.show();
        g.dispose();

    }

    /*! @fn main()
     * @brief E main-ul...doar creeaza un nou joc...
     * @param args
     */
    public static void main(String[] args)
    {
        new Game();
    }
}
