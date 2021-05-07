package Player.States;

import Input.KeyEnum;
import Player.*;

public class Idle extends PlayerState {

    public Idle()
    {
        init();
    }

    @Override
    public PlayerState handleInput() {
        //prev = this;
        if(player.getHealth() <= 0)
            return new Ded();
        else if(input.getKey(KeyEnum.SHIFT) && timerDash == 0 && player.getStamina() >= 2) {
            prev = this;
            return new Dash();
        }
        else if(input.getKey(KeyEnum.L) && player.isArmed()) {
            return new Attack();
        }
        else if (input.getKey(KeyEnum.SPACE)) {
            prev = this;
            return new Jump();
        }
        else if (input.getKey(KeyEnum.CTRL)) {
            prev = this;
            return new Crouch();
        }
        else if ((input.getKey(KeyEnum.A) && !input.getKey(KeyEnum.D)) || (input.getKey(KeyEnum.D) && !input.getKey(KeyEnum.A))) {
            prev = this;
            return new Walk();
        }
        else if(input.getKey(KeyEnum.W)) {
            player.setSpeedY(-5);
        }
        return this;
    }

    @Override
    public void init() {
        PlayerState.TimerVerticalSpeed = 0;
        player.setCurrentAnimation(animations_enum.idle);
        player.setSpeedY(0);
        player.setSpeedX(0);
    }
}
