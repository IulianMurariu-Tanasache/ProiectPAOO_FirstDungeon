package GUI.Elements;

import java.awt.*;

public class Text extends UI_Elemenent{

    private String text;
    private Color textColor;
    private int size;

    public Text(int x, int y, String text) {
        rect = new Rectangle(x,y,1,1);
        this.text = text;
        size = 0;
        visible = false;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void render(Graphics g) {
        if(!visible)
            return;

        g.setColor(textColor);
        if(size != 0){
            g.setFont(font.deriveFont((float)size));
        }
        g.drawString(text, rect.x, rect.y);
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return size;
    }
}
