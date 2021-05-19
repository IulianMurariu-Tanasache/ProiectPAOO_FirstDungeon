package GUI.Commands;

import GameStates.GameState;
import GameStates.GameplayState;
import GameStates.MenuState;
import SQLite.NotLoadedException;

public class ToGameCommand  implements Command{

    @Override
    public void execute() {
        try {
            GameState.setNext(new GameplayState());
        } catch (NotLoadedException e) {
            GameState.setNext(new MenuState());
        }
    }
}