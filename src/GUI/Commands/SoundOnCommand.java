package GUI.Commands;

import GameStates.GameState;

public class SoundOnCommand implements CheckBoxCommand {

    @Override
    public void execute(boolean val) {
        GameState.setSoundOn(val);
    }
}
