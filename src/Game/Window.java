package Game;

import javax.swing.*;
import java.awt.*;

/*! \class Window
    \brief Clasa care genereaza fereastra jocului.

    Se foloseste de JFrame.
 */
public class Window {

    private final int width;
    private final int height;//!< Dimensiunile ferestrei.
    private static Window instance = null;
    private JFrame window;

    public int getWidth() {
        return instance.width;
    }

    public int getHeight() {
        return instance.height;
    }

    /*! @fn Window(int width, int height, String title, Game game)
        @brief Constructor de initializare al clasei Window

        Initializeaza o fereastra noua si "pune" jocul pe ea.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
        \param game Clasa cu jocul care va fi pusa pe fereastra.
     */
    private Window(int width, int height, String title, Game game)
    {
        this.width = width;
        this.height = height;

        window = new JFrame(title);

        window.setPreferredSize(new Dimension(width,height));
        window.setMinimumSize(new Dimension(width,height));
        window.setMaximumSize(new Dimension(width,height));
        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        game.setBounds(0,0,width,height);
        window.add(game);
        game.setVisible(true);

        window.pack();
        window.setVisible(true);
    }

    public static Window getInstance() {
        return instance;
    }

    public static void setInstance(Game game) {
            instance = new Window(1024,576,"First Dungeon", game);
    }

    public Rectangle getBounds() {
        return new Rectangle(0,0,width,height);
    }

    public JFrame getWindow() {
        return window;
    }
}