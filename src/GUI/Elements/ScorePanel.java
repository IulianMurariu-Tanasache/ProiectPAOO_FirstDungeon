package GUI.Elements;

import GameStates.GameState;

import java.awt.*;

public class ScorePanel extends Panou{

    private Text[] textlist;
    private int[] scores;

    public ScorePanel(int x, int y, int w, int h) {
        super(x, y, w, h);
        backColor = new Color(180,180,180,75);
        textlist = new Text[7];
        scores = GameState.getScores();
        for(int i = 0; i < textlist.length; ++i)
        {
            textlist[i] = new Text(400,rect.y + 30 + i * 48,(i+1) + ". Score: " + scores[i]);
            textlist[i].setTextColor(Color.BLACK);
            textlist[i].setSize(25);
            add(textlist[i]);
        }

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible) {
            updateScore();
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    public void updateScore() {
        for(int i = 0; i < textlist.length; ++i)
        {
            if((GameState.getScore() < scores[i] || scores[i] == 0) && GameState.getScore() != 0) {
                for(int j = textlist.length - 1; j >= i + 1; --j)
                {
                    scores[j] = scores[j - 1];
                    textlist[j].setText((j+1) + ". Score: " + scores[j]);
                }
                scores[i] = GameState.getScore();
                textlist[i].setText((i+1) + ". Score: " + scores[i]);
                return;
            }
        }
    }

}
