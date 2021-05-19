package GameStates;

import java.awt.*;

/*! \class GameDedState
    \brief Clasa care extinde GameplayState. Reprezinta starea jocului in momentul in care jucatorul moare.
 */
public class GameDedState extends GameplayState{

    public GameDedState() {
        super(true);
    }

    @Override
    public void init() {
        super.init();
        deathPanel.setVisible(true);
        score = 0;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        deathPanel.render(g);
    }

    @Override
    public void tick() {
    }

    @Override
    public void clearUI() {
        super.clearUI();
        deathPanel.setVisible(false);
    }
}
