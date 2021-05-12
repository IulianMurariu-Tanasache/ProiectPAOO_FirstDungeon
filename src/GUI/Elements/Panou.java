package GUI.Elements;

import java.awt.*;
import java.util.ArrayList;

public class Panou extends UI_Elemenent{

    protected ArrayList<UI_Elemenent> elements = new ArrayList<>();
    protected Color backColor;

    public Panou(int x, int y, int w, int h) {
        rect = new Rectangle(x,y,w,h);
        visible = false;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        for(UI_Elemenent el : elements)
            el.setVisible(visible);
    }

    public void add(UI_Elemenent e) {
        elements.add(e);
    }

    @Override
    public void render(Graphics g) {
        if(!visible)
            return;

        g.setColor(backColor);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.BLACK);
        g.drawRect(rect.x, rect.y,rect.width,rect.height);

        for(UI_Elemenent e : elements)
            e.render(g);
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }
}
