package GUI.Commands;

import GameStates.GameState;
import GameStates.GameplayState;

public class EasyCommand implements Command{

    @Override
    public void execute() {
        GameState.setDiff(0);
        GameState.setNext(new GameplayState());
    }
}
