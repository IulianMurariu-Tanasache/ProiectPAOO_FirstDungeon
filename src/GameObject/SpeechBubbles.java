package GameObject;

import Player.Player;

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
        triggered = true;
    }

    public void tick() {
        Rectangle playerBounds = Player.getInstance().getBounds();
        if(playerBounds.intersects(triggerZone))
        {
            triggered = true;
        }
        else
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
