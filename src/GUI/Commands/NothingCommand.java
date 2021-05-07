package GUI.Commands;

import GameStates.GameState;
import GameStates.MenuState;

public class NothingCommand  implements Command{

    @Override
    public void execute() {
        System.out.println("Pai...nimic!");
    }
}