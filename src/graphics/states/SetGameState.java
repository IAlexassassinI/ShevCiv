package graphics.states;

import graphics.components.button.ButtonComponent;
import graphics.components.button.SelectButtonComponent;
import graphics.loads.Images;
import graphics.loads.Sounds;
import graphics.panels.EmptyPanel;
import graphics.panels.Orientation;
import graphics.panels.SelectionPanel;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SetGameState extends BasicGameState implements ComponentListener {

    public static final int ID = 4;

    public ButtonComponent startGame;
    public Image numberOfPlayersImage;
    public Image levelOfDifficultyImage;
    public SelectionPanel numberOfPlayers;
    public SelectionPanel levelOfDifficulty;

    public StateBasedGame stateBasedGame;
    public GameContainer gameContainer;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        this.numberOfPlayersImage = new Image("assets/graphics/number_of_players.png");
        this.levelOfDifficultyImage = new Image("assets/graphics/level_of_difficulty.png");
        this.startGame = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/start_game.png"), 710, 647, 500, 75);
        this.startGame.addListener(this);

        this.numberOfPlayers = new SelectionPanel(Orientation.HORIZONTAL, 875, 432, 170, 50, 170);
        this.numberOfPlayers.add(new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/1.png"), 0, 0, 50, 50), 50);
        this.numberOfPlayers.add(new EmptyPanel(0, 0, 10, 50, 10), 10);
        this.numberOfPlayers.add(new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/2.png"), 0, 0, 50, 50), 50);
        this.numberOfPlayers.add(new EmptyPanel(0, 0, 10, 50, 10), 10);
        this.numberOfPlayers.add(new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/3.png"), 0, 0, 50, 50), 50);

        this.levelOfDifficulty = new SelectionPanel(Orientation.HORIZONTAL, 815, 577, 290, 50, 290);
        this.levelOfDifficulty.add(new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/1.png"), 0, 0, 50, 50), 50);
        this.levelOfDifficulty.add(new EmptyPanel(0, 0, 10, 50, 10), 10);
        this.levelOfDifficulty.add(new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/2.png"), 0, 0, 50, 50), 50);
        this.levelOfDifficulty.add(new EmptyPanel(0, 0, 10, 50, 10), 10);
        this.levelOfDifficulty.add(new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/3.png"), 0, 0, 50, 50), 50);
        this.levelOfDifficulty.add(new EmptyPanel(0, 0, 10, 50, 10), 10);
        this.levelOfDifficulty.add(new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/4.png"), 0, 0, 50, 50), 50);
        this.levelOfDifficulty.add(new EmptyPanel(0, 0, 10, 50, 10), 10);
        this.levelOfDifficulty.add(new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/5.png"), 0, 0, 50, 50), 50);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Images.background.draw(0,0,1920,1080);
        this.numberOfPlayersImage.draw(660, 357, 600, 75);
        this.numberOfPlayers.render(gameContainer, graphics);
        this.levelOfDifficultyImage.draw(660, 502, 600, 75);
        this.levelOfDifficulty.render(gameContainer, graphics);
        this.startGame.render(gameContainer, graphics);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        super.mouseMoved(oldx, oldy, newx, newy);
        this.startGame.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.numberOfPlayers.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.levelOfDifficulty.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        this.startGame.mousePressedSignalise(button, x, y);
        this.numberOfPlayers.mousePressedSignalise(button, x, y);
        this.levelOfDifficulty.mousePressedSignalise(button, x, y);
        this.gameContainer.getInput().consumeEvent();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
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

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent == this.startGame) {
            try {
                ((GameState) this.stateBasedGame.getState(GameState.ID)).init(this.gameContainer, this.stateBasedGame, this.numberOfPlayers.getSelectedComponent().getId() + 1, this.levelOfDifficulty.getSelectedComponent().getId() + 1);
                this.stateBasedGame.enterState(GameState.ID);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }
}
