package Player.States;

import Input.KeyEnum;
import Player.animations_enum;

public class Walk extends PlayerState{

    public Walk() {
        init();
    }

    @Override
    public PlayerState handleInput() {
        //prev = this;
        if(player.getHealth() <= 0)
            return new Ded();
        else if(input.getKey(KeyEnum.SHIFT) && timerDash == 0 && player.getStamina() >= 2) {
            return new Dash();
        }
        else if(input.getKey(KeyEnum.L) && player.isArmed() && player.getStamina() >= 1) {
            return new Attack();
        }
        else if(input.getKey(KeyEnum.SPACE)) {
            prev = new Idle();
            return new Jump();
        }
        else if(input.getKey(KeyEnum.CTRL)) {
            prev = new Idle();
            return new Crouch();
        }
        else if ((input.getKey(KeyEnum.A) && !input.getKey(KeyEnum.D))) {
            player.setSpeedX(-horizontalSpeed);
            player.setFacing(false);
            if(input.getKey(KeyEnum.W)) {
                player.setSpeedY(-5);
            }
            return this;
        }
        else if(input.getKey(KeyEnum.D) && !input.getKey(KeyEnum.A)) {
            player.setSpeedX(horizontalSpeed);
            player.setFacing(true);
            if(input.getKey(KeyEnum.W)) {
                player.setSpeedY(-5);
            }
            return this;
        }
        //else if(!input.getKey(KeyEnum.D) && !input.getKey(KeyEnum.A))
        prev = new Idle();
        return new Idle();
    }

    @Override
    public void init() {
        player.setCurrentAnimation(animations_enum.walk);
    }
}
