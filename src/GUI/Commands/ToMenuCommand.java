package GUI.Commands;

import GameStates.GameState;
import GameStates.MenuState;

public class ToMenuCommand  implements Command{

    @Override
    public void execute() {
        GameState.setNext(new MenuState());
    }
}
