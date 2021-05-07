package GUI.Elements;

import GUI.Commands.CheckBoxCommand;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CheckBox extends UI_Elemenent implements Interactable{

    private Text text;
    private boolean checked;
    private Color color;
    private int thickness;
    private CheckBoxCommand comand;

    public CheckBox(int x, int y, int w, int h, String txt, int sizeText, CheckBoxCommand command) {
        rect = new Rectangle(x,y,w,h);
        text = new Text(x + w + 10, y + h / 2, txt);
        thickness = (int) Math.ceil((float)w / 10);
        text.setSize(sizeText);
        checked = false;
        color = new Color(11,132,80);
        text.setTextColor(new Color(70,12,89));
        this.comand = command;
        visible = false;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        text.setVisible(visible);
    }

    @Override
    public void render(Graphics g) {
        if(!visible)
            return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(thickness));
        g.setColor(color);
        g2.drawRect(rect.x, rect.y,rect.width,rect.height);
        if(checked)
        {
            g.setColor(color.darker());
            g.fillRect(rect.x + thickness,rect.y + thickness,rect.width - thickness * 2,rect.height - thickness * 2);
        }

        text.render(g);
        g2.setStroke(new BasicStroke(1));
    }


    public Text getText() {
        return text;
    }

    public boolean isChecked() {
        return checked;
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isMouseOn(e))
        {
            checked = !checked;
            comand.execute(checked);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    public void setColor(Color color) {
        this.color = color;
    }
}
