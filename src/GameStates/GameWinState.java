package GameStates;

import GUI.Elements.UI_Elemenent;
import Game.Window;

import java.awt.*;


/*! \class GameWinStaet
    \brief Clasa care extinde GameState. Reprezinta starea jocului atunci cand jucatorul castiga.
 */
public class GameWinState extends GameState{

    private final int timerConst = 120;
    private int timer;

    @Override
    public void init() {
        timer = 0;
        //menuBack.setVisible(true);
        WinPanel.setVisible(true);
        if(diff == 1) {
            score /= 2;
        }
    }


    @Override
    public void render(Graphics g) {

        g.clearRect(0,0, Window.getInstance().getWidth(),Window.getInstance().getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(0,0,Window.getInstance().getWidth(),Window.getInstance().getHeight());

        //menuBack.render(g);
        for(UI_Elemenent ui : GameUI)
            ui.render(g);
        WinPanel.render(g);
    }

    @Override
    public void tick() {
        timer++;
        if(timer > timerConst)
            GameState.setNext(new MenuState());
    }

    @Override
    public void clearUI() {
        //menuBack.setVisible(false);
        WinPanel.setVisible(false);
    }
}
