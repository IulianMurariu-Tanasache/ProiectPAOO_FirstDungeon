package GUI.Commands;

import GameStates.GameState;
import GameStates.LeaderboardState;

public class ToLeaderBoardCommand implements Command{

    @Override
    public void execute() {
        GameState.setNext(new LeaderboardState());
    }
}
