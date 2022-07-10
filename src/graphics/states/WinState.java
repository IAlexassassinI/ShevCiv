package graphics.states;

import Processing.Player.Player;
import graphics.components.button.ButtonComponent;
import graphics.loads.Images;
import graphics.loads.Sounds;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WinState extends BasicGameState implements ComponentListener {

    public static int ID = 5;

    private ButtonComponent nextButton;
    private Player winPlayer;
    private Image winImage;
    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        nextButton = new ButtonComponent(gameContainer, Images.next, 1700, 960, 200, 100);
        this.gameContainer = gameContainer;
        nextButton.addListener(this);
        this.stateBasedGame = stateBasedGame;
    }

    public void init(Player player) throws SlickException {
        winPlayer = player;
        winImage = new Image("assets/graphics/WinScreen" + player.race + ".png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        winImage.draw(0,0,1920,1080);
        nextButton.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        gameContainer.setMusicVolume(Sounds.musicVolume);
        gameContainer.setSoundVolume(Sounds.soundVolume);
    }

    @Override
    public void keyPressed(int key, char c) {
        if(key == Input.KEY_RIGHT) {
            Sounds.musicVolume = Math.min(1f, Sounds.musicVolume + 0.05f);
        }
        if(key == Input.KEY_LEFT) {
            Sounds.musicVolume = Math.max(0, Sounds.musicVolume - 0.05f);
        }
        if(key == Input.KEY_UP) {
            Sounds.soundVolume = Math.min(1f, Sounds.soundVolume + 0.05f);
        }
        if(key == Input.KEY_DOWN) {
            Sounds.soundVolume = Math.max(0, Sounds.soundVolume - 0.05f);
        }
    }

    public WinState() {
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
        gameContainer.getInput().consumeEvent();
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if (abstractComponent == nextButton) {
            Sounds.creditsSound.play();
            this.stateBasedGame.enterState(CreditsState.ID);
        }
    }
}
