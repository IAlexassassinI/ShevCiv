import org.newdawn.slick.*;

public class TestWindow extends BasicGame {
    public TestWindow(String title) {
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

    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new TestWindow("testMap"));
        app.setDisplayMode(1280, 720, false);
        app.start();
    }
}
