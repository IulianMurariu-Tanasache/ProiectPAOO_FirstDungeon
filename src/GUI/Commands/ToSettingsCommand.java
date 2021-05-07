package GUI.Commands;

import GameStates.GameState;
import GameStates.SettingsState;

public class ToSettingsCommand implements Command {
    @Override
    public void execute() {
        GameState.setNext(new SettingsState());
    }
}
