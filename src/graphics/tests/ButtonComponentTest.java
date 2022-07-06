package graphics.tests;

import graphics.components.button.ButtonComponent;
import org.newdawn.slick.*;

public class ButtonComponentTest extends BasicGame {

    ButtonComponent buttonComponent;

    public ButtonComponentTest(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        buttonComponent = new ButtonComponent(gameContainer, new Image("assets/graphics/fortest.png"), 0, 0, 600, 100);
        buttonComponent.setLocked(true);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        buttonComponent.render(gameContainer, graphics);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        super.mouseMoved(oldx, oldy, newx, newy);
        buttonComponent.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        this.buttonComponent.mousePressedSignalise(button, x, y);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new ButtonComponentTest(""));
        app.setDisplayMode(1280, 720, false);
        app.start();
    }
}
