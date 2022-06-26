package graphics.states;

import Processing.Game.Game;
import Processing.TileMap.GameMap;
import Processing.TileMap.TileUtils.TypeOfLand;
import graphics.components.camera.Camera;
import graphics.components.tiledmap.GameMapComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState implements ComponentListener {

    public static final int ID = 3;

    private Camera camera;
    private GameMapComponent mapComponent;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        GameMap map = new GameMap(20, 20);
        map.getTile(0, 0).setTypeOfLand(TypeOfLand.FlatLand);
        map.getTile(0, 1).setTypeOfLand(TypeOfLand.FlatLand);
        Game game = new Game(map, 1);
        this.mapComponent = new GameMapComponent(gameContainer, map, 20, 20);
        this.camera = new Camera(gameContainer, 20, 20, 1880, 960, this.mapComponent);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.camera.render(gameContainer,graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {

    }
}
