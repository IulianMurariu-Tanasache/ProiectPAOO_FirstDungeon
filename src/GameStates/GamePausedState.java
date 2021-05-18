package GameStates;

import java.awt.*;


public class GamePausedState extends GameplayState{

    public GamePausedState(){
        super(true);
    }

    @Override
    public void init() {
        super.init();
        pausedPanel.setVisible(true);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        pausedPanel.render(g);
    }

    //    @Override
//    public void render(Graphics g) {
//
//        g.clearRect(0,0, Window.getInstance().getWidth(), Window.getInstance().getHeight());
//        g.setColor(ChestiiStatice.visiniu);
//        g.fillRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());
//
//        Dungeon.getInstance().getRoom().render(g);
//        Player.getInstance().render(g);
//
//        for(GameObject obj : Dungeon.getInstance().getRoom().getObjects())
//            obj.render(g);
//
//        for(UI_Elemenent ui : GameUI)
//            ui.render(g);
//        pausedPanel.render(g);
//
//    }

    @Override
    public void tick() {
    }

    @Override
    public void clearUI() {
        super.clearUI();
        pausedPanel.setVisible(false);
    }
}
