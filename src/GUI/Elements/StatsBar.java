package GUI.Elements;

import Player.Player;
import SpriteSheet.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StatsBar extends UI_Elemenent {

    //in vector: 0-plin, 1-jumate, 2-gol
    private BufferedImage[] inima;
    private BufferedImage[] stamina;
    private int health;
    private int energy;

    public StatsBar(int x, int y) {
        rect = new Rectangle(x,y,1,1);
        health = 6;
        energy = 6;
        visible = false;
        try {
            SpriteSheet sheet = new SpriteSheet("inimi","inimi");
            inima = sheet.grabImages("inimi",3);
            stamina = sheet.grabImages("stamina",3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        if(!visible)
            return;
        BufferedImage[] toDraw = new BufferedImage[]{inima[2],inima[2],inima[2]};
        Player player = Player.getInstance();
        this.health = player.getHealth();
        this.energy = player.getStamina();
        if(health == 1)
            toDraw[0] = inima[1];
        else if(health > 1){
            toDraw[0] = inima[0];
            if(health == 3)
                toDraw[1] = inima[1];
            else if(health > 3){
                toDraw[1] = inima[0];
                if(health == 5)
                    toDraw[2] = inima[1];
                else if(health > 5)
                    toDraw[2] = inima[0];
            }
        }

        for(int i = 0; i < 3; i++)
            g.drawImage(toDraw[i], 2 + rect.x + (toDraw[i].getWidth() * 3 + 10) * i, rect.y, toDraw[i].getWidth() * 3, toDraw[i].getHeight() * 3,null);

        toDraw = new BufferedImage[]{stamina[2],stamina[2],stamina[2]};
        if(energy == 1)
            toDraw[0] = stamina[1];
        else if(energy > 1){
            toDraw[0] = stamina[0];
            if(energy == 3)
                toDraw[1] = stamina[1];
            else if(energy > 3){
                toDraw[1] = stamina[0];
                if(energy == 5)
                    toDraw[2] = stamina[1];
                else if(energy > 5)
                    toDraw[2] = stamina[0];
            }
        }

        for(int i = 0; i < 3; i++)
            g.drawImage(toDraw[i], rect.x + (toDraw[i].getWidth() * 3 + 7) * i, rect.y + inima[0].getHeight()*3 + 10, toDraw[i].getWidth() * 3, toDraw[i].getHeight() * 3,null);
    }
}
