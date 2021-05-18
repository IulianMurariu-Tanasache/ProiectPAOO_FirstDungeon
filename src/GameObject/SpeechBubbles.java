package GameObject;

import GameStates.GameState;
import Player.Player;
import SoundTrack.SoundManager;

import javax.swing.*;
import java.awt.*;

public class SpeechBubbles extends GameObject{

    private ImageIcon gif;
    private Rectangle triggerZone;
    private boolean triggered;

    public SpeechBubbles(int x, int y, int w, int h, float scale, String file) {
        super(x,y,scale,ID.bubble);
        triggerZone = new Rectangle(x,y,w,h);
        gif = new ImageIcon(file);
        triggered = false;
    }

    public void tick() {
        if(GameState.isFoundTreasure())
            return;
        Rectangle playerBounds = Player.getInstance().getBounds();
        if(playerBounds.intersects(triggerZone) && !triggered)
        {
            triggered = true;
            SoundManager.getSoundManager().play("Voice1.wav");
        }
        else if(!playerBounds.intersects(triggerZone) && triggered)
        {
            triggered = false;
        }
    }

    @Override
    public void mapCollision() {
        //lel
    }

    @Override
    public Rectangle getBounds() {
        return triggerZone;
    }

    public void render(Graphics g) {
        if(GameState.isFoundTreasure())
            return;
        Image img = gif.getImage();
        img = gif.getImage();
        if(!triggered)
        {
            img.flush();
            return;
        }
        g.drawImage(img,triggerZone.x,triggerZone.y,(int)(img.getWidth(null) * scale),(int)(img.getHeight(null) * scale),null);
    }
}
