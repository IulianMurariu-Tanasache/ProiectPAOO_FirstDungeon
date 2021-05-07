package GUI.Commands;

import GameStates.GamePausedState;
import GameStates.GameState;

public class PausePanelCommand implements Command{

    @Override
    public void execute() {
        GameState.setNext(new GamePausedState());
    }
}
