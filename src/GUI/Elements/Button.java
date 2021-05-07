package GUI.Elements;

import GUI.Commands.Command;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Button extends UI_Elemenent implements Interactable{

    private final String text;
    private Color textColor;
    private Color backColor;
    private final Command command;
    private BufferedImage img = null;

    public Button(int x, int y, int w, int h, String text, Command c) {
        rect = new Rectangle(x,y,w,h);
        command = c;
        this.text = text;
        visible = false;
        backColor = new Color(0,0,0,0);
    }

    @Override
    public void render(Graphics g) {
        if(!visible)
            return;
        if(img == null){
            g.setColor(backColor);
            g.fillRect(rect.x, rect.y,rect.width,rect.height);
            g.setColor(Color.BLACK);
            g.drawRect(rect.x, rect.y,rect.width,rect.height);
        }
        else {
            g.drawImage(img,rect.x,rect.y,null);
        }
        g.setColor(textColor);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text,rect.x - (fm.stringWidth(text) / 2) + (rect.width/2), rect.y + (rect.height/2) + fm.getDescent());
    }

    @Override
    public boolean isMouseOn(MouseEvent e) {
        if(!visible)
            return false;
        Rectangle cursor = new Rectangle(e.getX(),e.getY(),5,5);

        if(e.getButton() != MouseEvent.BUTTON1)
            return false;
        return rect.contains(cursor);
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isMouseOn(e)) {
            //backColor = backColor.darker();
            command.execute();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //if(isMouseOn(e))
            //backColor = backColor.brighter();
    }
}
