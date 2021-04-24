package Handler;

import GameObject.GameObject;
import java.awt.*;
import java.util.ArrayList;

public class Handler {

    private ArrayList<GameObject> objectList = new ArrayList<>();

    public void tick(){
        for (GameObject obj : objectList)
        {
            obj.tick();
        }
    }

    public void render(Graphics g, double elapsed)
    {
        for (GameObject obj : objectList)
        {
            obj.render(g,elapsed);
        }
    }

    public void add(GameObject o)
    {
        objectList.add(o);
    }

    public void remove(GameObject o)
    {
        objectList.remove(o);
    }

}
