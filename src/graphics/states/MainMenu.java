package graphics.states;

import graphics.components.button.ButtonComponent;
import graphics.loads.Images;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState implements ComponentListener {

    public static final int ID = 1;

    private ButtonComponent newGameButton;
    private ButtonComponent editMapButton;
    private ButtonComponent exitButton;

    private StateBasedGame game;
    private GameContainer gameContainer;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.newGameButton = new ButtonComponent(gameContainer, Images.buttonNewGame, 710,540,500,75);
        this.newGameButton.addListener(this);
        this.editMapButton = new ButtonComponent(gameContainer, Images.buttonEditMap, 710,640,500,75);
        this.editMapButton.addListener(this);
        this.exitButton = new ButtonComponent(gameContainer, Images.buttonExit, 710, 740, 500, 75);
        this.exitButton.addListener(this);

        this.game = stateBasedGame;
        this.gameContainer = gameContainer;

        //stateBasedGame.addState(new EditMap());
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Images.background.draw(0,0,1920,1080);
        newGameButton.render(gameContainer, graphics);
        editMapButton.render(gameContainer, graphics);
        exitButton.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        this.exitButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.editMapButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.newGameButton.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        this.newGameButton.mousePressedSignalise(button, x, y);
        this.exitButton.mousePressedSignalise(button, x, y);
        this.editMapButton.mousePressedSignalise(button, x, y);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent instanceof ButtonComponent) {
            if(abstractComponent == this.newGameButton) {
                //game.enterState(GameState.ID);
                game.enterState(SetGameState.ID);
            }
            else if(abstractComponent == this.editMapButton) {
                game.enterState(EditMap.ID);
            }
            else if(abstractComponent == this.exitButton) {
                this.gameContainer.exit();
            }
        }
    }
}
