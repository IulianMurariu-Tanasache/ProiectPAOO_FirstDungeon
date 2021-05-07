package GUI.Commands;

import GameStates.GameState;

public class BackCommand implements Command{

    @Override
    public void execute() {
        GameState.prevState();
    }
}
