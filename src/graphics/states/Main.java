package graphics.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tests.ScalableTest;
import org.newdawn.slick.tests.StateBasedTest;

public class Main extends StateBasedGame {

    public Main() {
        super("ShevCiv");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenu());
        this.addState(new EditMap());
        this.addState(new GameState());
    }

    public static void main(String[] args) {
        try {
            //ScalableGame game = new ScalableGame(new Main(),1920,1080);

            AppGameContainer container = new AppGameContainer(new Main());
            container.setDisplayMode(1920,1080,false);
            container.setVSync(false);
            container.setShowFPS(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
