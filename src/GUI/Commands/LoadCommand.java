package GUI.Commands;

import GameStates.GameState;
import GameStates.GameplayState;

public class LoadCommand implements Command{
    @Override
    public void execute() {
        GameState.setToLoad(true);
        GameState.setNext(new GameplayState());
    }
}
