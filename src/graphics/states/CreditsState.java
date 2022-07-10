package graphics.states;

import Processing.Player.Player;
import graphics.components.button.ButtonComponent;
import graphics.loads.Images;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CreditsState extends BasicGameState implements ComponentListener {

    public static int ID = 7;

    private ButtonComponent nextButton;
    private Image creditsImage;
    private StateBasedGame stateBasedGame;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        nextButton = new ButtonComponent(gameContainer, Images.next, 1700, 960, 200, 100);

        nextButton.addListener(this);
        this.stateBasedGame = stateBasedGame;
        this.creditsImage = new Image("assets/graphics/TitleScreen.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.creditsImage.draw(0,0,1920,1080);
        nextButton.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    public CreditsState() {
        super();
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        super.mouseMoved(oldx, oldy, newx, newy);
        this.nextButton.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        this.nextButton.mousePressedSignalise(button, x, y);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if (abstractComponent == nextButton) {
            this.stateBasedGame.enterState(MainMenu.ID);
        }
    }
}
