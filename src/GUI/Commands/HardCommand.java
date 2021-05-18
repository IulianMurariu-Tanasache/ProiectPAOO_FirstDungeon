package GUI.Commands;

import GameStates.GameState;
import GameStates.GameplayState;

public class HardCommand implements Command{

    @Override
    public void execute() {
        GameState.setDiff(1);
        GameState.setNext(new GameplayState());
    }
}
