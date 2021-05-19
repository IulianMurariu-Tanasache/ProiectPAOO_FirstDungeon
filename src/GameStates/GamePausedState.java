package GameStates;

import java.awt.*;

/*! \class GamePausedState
    \brief Clasa care extinde GameplayState. Reprezinta momentul in care jocul este pus in pauza.
 */
public class GamePausedState extends GameplayState{

    public GamePausedState(){
        super(true);
    }

    @Override
    public void init() {
        super.init();
        pausedPanel.setVisible(true);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        pausedPanel.render(g);
    }

    @Override
    public void tick() {
    }

    @Override
    public void clearUI() {
        super.clearUI();
        pausedPanel.setVisible(false);
    }
}
