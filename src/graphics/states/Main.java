package graphics.states;

import graphics.loads.Sounds;
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
        new Sounds();
        this.addState(new MainMenu());
        this.addState(new EditMap());
        this.addState(new GameState());
        this.addState(new SetGameState());
        this.addState(new WinState());
        this.addState(new LoseState());
        this.addState(new CreditsState());
    }

    public static void main(String[] args) {
        try {
            //ScalableGame game = new ScalableGame(new Main(),1920,1080);

            AppGameContainer container = new AppGameContainer(new Main());
            container.setDisplayMode(1920,1080,true);
            //container.setVSync(false);
            container.setTargetFrameRate(60);
            container.setShowFPS(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
