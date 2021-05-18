package GUI.Commands;

import GameStates.DiffSelectState;
import GameStates.GameState;

public class ToDiffPanelCommand implements Command{

    @Override
    public void execute() {
        GameState.setNext(new DiffSelectState());
    }
}
