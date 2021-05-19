package GUI.Commands;

import GameStates.GameState;
import GameStates.GameplayState;
import GameStates.MenuState;
import SQLite.NotLoadedException;

public class EasyCommand implements Command{

    @Override
    public void execute() {
        GameState.setDiff(0);
        try {
            GameState.setNext(new GameplayState());
        } catch (NotLoadedException e) {
            GameState.setNext(new MenuState());
        }
    }
}