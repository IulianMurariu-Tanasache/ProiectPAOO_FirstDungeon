package GUI.Commands;

import GameStates.GameState;

public class MusicOnCommand implements CheckBoxCommand{
    @Override
    public void execute(boolean val) {
        GameState.setMusicOn(val);
    }
}
