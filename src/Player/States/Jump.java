package Player.States;

import Input.KeyEnum;
import Player.*;

public class Jump extends PlayerState{

    public Jump()
    {
        init();
    }

    @Override
    public PlayerState handleInput() {
        if(player.getHealth() <= 0)
            return new Ded();
        else if(PlayerState.gravity() == 0) {
            prev = this;
            return new Fall();
        }
        else if (input.getKey(KeyEnum.SHIFT) && timerDash == 0 && player.getStamina() >= 2) {
            prev = this;
            return new Dash();
        }
        else if(input.getKey(KeyEnum.CTRL)) {
            prev = this;
            return new Crouch();
        }
        else if ((input.getKey(KeyEnum.A) && !input.getKey(KeyEnum.D))) {
            player.setSpeedX(-horizontalSpeed);
            player.setFacing(false);
            return this;
        }
        else if(input.getKey(KeyEnum.D) && !input.getKey(KeyEnum.A)) {
            player.setSpeedX(horizontalSpeed);
            player.setFacing(true);
            return this;
        }
        player.setSpeedX(0);
        return this;
    }

    @Override
    public void init() {
        player.setCurrentAnimation(animations_enum.jump);
        player.setSpeedY(-verticalSpeed);
        PlayerState.gravity();
    }
}
