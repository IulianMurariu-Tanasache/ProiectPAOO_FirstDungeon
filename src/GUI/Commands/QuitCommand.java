package GUI.Commands;

import GameStates.GameState;

public class QuitCommand implements Command{

    @Override
    public void execute() {
        GameState.setRunning(false);
    }
}
