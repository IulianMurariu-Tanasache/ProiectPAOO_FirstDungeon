package Player.States;

import GameStates.GameDedState;
import GameStates.GamePausedState;
import GameStates.GameState;
import Player.*;

public class Ded extends PlayerState{

    public Ded() {
        init();
    }

    @Override
    public PlayerState handleInput() {
        if (!player.getInAnimation().val) {
            player.setCurrentAnimation(-1);
            GameState.setNext(new GameDedState());
        }
        return this;
    }

    @Override
    public void init() {
        player.setCurrentAnimation(animations_enum.death);
        player.setSpeedX(0);
    }
}
