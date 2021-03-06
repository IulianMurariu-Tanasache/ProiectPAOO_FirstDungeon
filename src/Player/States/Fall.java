package Player.States;

import Input.KeyEnum;
import Player.*;

public class Fall extends PlayerState{

    public Fall()
    {
        init();
    }

    @Override
    public PlayerState handleInput() {
        if(input.getKey(KeyEnum.SHIFT) && timerDash == 0) {
            prev = this;
            return new Dash();
        }
        else if(player.getSpeedY() == 0) {
            prev = this;
            return new Idle();
        }
        else if ((input.getKey(KeyEnum.A) && !input.getKey(KeyEnum.D))) {
            player.setSpeedX(-horizontalSpeed);
            player.setFacing(false);
            //return this;
        }
        else if(input.getKey(KeyEnum.D) && !input.getKey(KeyEnum.A)) {
            player.setSpeedX(horizontalSpeed);
            player.setFacing(true);
            //return this;
        }
        else
            player.setSpeedX(0);
        return this;
    }

    @Override
    public void init() {
        player.setCurrentAnimation(animations_enum.fall);
        player.setSpeedY(verticalSpeed);
        PlayerState.TimerVerticalSpeed = 0;
    }
}