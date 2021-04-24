package Player.States;

import Input.KeyEnum;
import Player.*;

public class Crouch extends PlayerState{

    public Crouch() {
        init();
    }

    @Override
    public PlayerState handleInput() {
        if(input.getKey(KeyEnum.SPACE)) {
            player.setHeight(29);
            player.setY(player.getY() - (int)(8 * player.getScale()));
            input.setKey(KeyEnum.CTRL, false);
            prev = this;
            return new Jump();
        }
        if(player.getSpeedY() != 0 && PlayerState.gravity() == 0) {
            player.setSpeedY(verticalSpeed);
        }
        else if(input.getKey(KeyEnum.CTRL)) {
            if ((input.getKey(KeyEnum.A) && !input.getKey(KeyEnum.D))) {
                player.setSpeedX(-horizontalSpeed / 2);
                player.setFacing(false);
                player.setCurrentAnimation(animations_enum.walk_crouch);
                return this;
            } else if (input.getKey(KeyEnum.D) && !input.getKey(KeyEnum.A)) {
                player.setSpeedX(horizontalSpeed / 2);
                player.setFacing(true);
                player.setCurrentAnimation(animations_enum.walk_crouch);
                return this;
            } else if((!input.getKey(KeyEnum.D) && !input.getKey(KeyEnum.A)) || (input.getKey(KeyEnum.D) && input.getKey(KeyEnum.A))) {
                player.setCurrentAnimation(animations_enum.idle_crouch);
                player.setSpeedX(0);
                return this;
            }
        }

        if(player.getSpeedY() == 0) {
            player.setHeight(29);
            player.setY(player.getY() - (int)(8 * player.getScale()));
            prev = this;
            return new Idle();
        }
        else
            return this;
    }

    @Override
    public void init() {
        player.setCurrentAnimation(animations_enum.idle_crouch);
        player.setHeight(21);
        input.setKey(KeyEnum.CTRL, true);
        player.setY(player.getY() + (int)(4 * player.getScale()));
    }
}
