package Player.States;

import GameStates.GameDedState;
import GameStates.GameState;
import Player.animations_enum;
import SoundTrack.SoundManager;

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
        SoundManager.getSoundManager().play("ded.wav");
    }
}
