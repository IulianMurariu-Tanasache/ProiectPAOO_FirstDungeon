package GUI.Elements;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class UI_Elemenent {

    protected static Font font;
    protected boolean visible;
    protected Rectangle rect;

    public static void setFont(Font font) {
        UI_Elemenent.font = font;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public abstract void render(Graphics g);

    public void setPosition(int x, int y){rect.x = x; rect.y = y;}

    public Rectangle getRect(){return rect;}

}
