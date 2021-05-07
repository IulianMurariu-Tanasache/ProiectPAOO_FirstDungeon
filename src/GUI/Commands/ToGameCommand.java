package GUI.Commands;

import GameStates.GameState;
import GameStates.GameplayState;
import GameStates.MenuState;

public class ToGameCommand  implements Command{

    @Override
    public void execute() {
        GameState.setNext(new GameplayState());
    }
}