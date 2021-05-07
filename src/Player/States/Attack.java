package Player.States;

import Player.animations_enum;

public class Attack extends PlayerState{

    private static int i = 0;

    public Attack() {
        init();
    }

    @Override
    public PlayerState handleInput() {
        if(player.getHealth() <= 0)
        {
            player.setWidth(22);
            player.setHeight(29);
            player.setAttacking(false);
            return new Ded();
        }
        else if(!player.getInAnimation().val) {
            prev.init();
            player.setWidth(22);
            player.setHeight(29);
            player.setAttacking(false);
            return prev;
        }
        return this;
    }

    @Override
    public void init() {
        player.setWidth(26);
        player.setStamina(player.getStamina() - 1);
        player.setCurrentAnimation(animations_enum.attack1 + i);
        if(i == 0){
            player.setY((int) (player.getY() - 8 * player.getScale()));
            player.setHeight(36);
        }
        player.setAttacking(true);
        i = (i + 1) % 2;
        player.setSpeedX(0);
    }
}
