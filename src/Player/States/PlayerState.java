package Player.States;

import Input.KeyInput;
import Player.Player;

public abstract class PlayerState {
    protected static KeyInput input;
    protected static final int verticalSpeed = 5;
    protected static double TimerVerticalSpeed = 0;
    protected static final int horizontalSpeed = 5;
    protected static final int timerTime = 100;
    protected static int timerDash = 0;
    protected static PlayerState prev = null;
    protected static Player player = null;

    public static void setInput(KeyInput input) {
        PlayerState.input = input;
    }
    public abstract PlayerState handleInput();
    public abstract void init();

    public static void timerPass() {
        if(timerDash > 0)
            timerDash--;
    }

    public static PlayerState getPrev() {
        return prev;
    }

    public static double gravity() {
        if(TimerVerticalSpeed <= 0)
        {
            TimerVerticalSpeed = 5f;
            return 0;
        }
        else
            TimerVerticalSpeed -= 0.15f;
        return TimerVerticalSpeed;
    }

    public static void setPrev(PlayerState prev) {
        PlayerState.prev = prev;
    }

    public static KeyInput getInput() {
        return input;
    }

    public static void setTimerVerticalSpeed(double timerVerticalSpeed) {
        TimerVerticalSpeed = timerVerticalSpeed;
    }

    public static void setPlayer(Player player) {
        PlayerState.player = player;
    }
}
