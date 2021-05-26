package GUI.Commands;

import GameStates.GameState;
import GameStates.MenuState;

public class ToMenuCommand  implements Command{

    @Override
    public void execute() {
        GameState.setScore(0);
        GameState.setNext(new MenuState());
    }
}
