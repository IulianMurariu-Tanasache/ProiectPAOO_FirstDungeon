package Player.States;

import Player.*;

public class Dash extends PlayerState{

    public Dash() {
        init();
    }

    @Override
    public PlayerState handleInput() {
        if(!player.getInAnimation().val || player.getSpeedX() == 0) {
            prev.init();
            return prev;
        }
        return this;
    }

    @Override
    public void init() {
        player.setStamina(player.getStamina() - 2);
        player.setCurrentAnimation(animations_enum.dash);
        if(player.isFacing())
            player.setSpeedX(2 * horizontalSpeed);
        else
            player.setSpeedX(-2 * horizontalSpeed);
        player.setSpeedY(0);
        timerDash = timerTime;
    }
}
