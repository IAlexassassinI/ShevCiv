package graphics.tests;

import org.newdawn.slick.*;

public class TextTest extends BasicGame {
    public TextTest(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        //graphics.setColor(Color.white);
        //System.out.println(gameContainer.getDefaultFont().getHeight("String"));
        ////graphics.drawString("String", 0, 0);
        //graphics.drawString("String", 0, 0 + gameContainer.getDefaultFont().getHeight("String"));
        //graphics.draw
        System.out.println(gameContainer.getDefaultFont().getHeight("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"));
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new TextTest(""));
        app.setDisplayMode(1280, 720, false);
        app.setVSync(true);
        app.setShowFPS(false);
        app.start();
        //app.start();
    }
}
