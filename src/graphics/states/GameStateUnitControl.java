package graphics.states;

import Processing.FileHandler.SaveLoadInterface;
import Processing.Game.Game;
import Processing.TileMap.GameMap;
import graphics.components.camera.Camera;
import graphics.components.tiledmap.GameMapComponent;
import graphics.components.tiledmap.GameTileComponent;
import graphics.loads.Sounds;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameStateUnitControl extends BasicGameState implements ComponentListener {

    public static final int ID = 3;

    private static Camera camera;
    private static GameMapComponent mapComponent;
    private static GameTileComponent unitTile;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        camera.render(gameContainer,graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        gameContainer.setMusicVolume(Sounds.musicVolume);
        gameContainer.setSoundVolume(Sounds.soundVolume);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        camera.mouseMovedSignalise(oldx, oldy, newx, newy);
        mapComponent.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        camera.mouseDraggedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        mapComponent.mouseClickedSignalise(button, x, y, clickCount);
    }

    @Override
    public void mousePressed(int button, int x, int y) {

    }



    @Override
    public void mouseWheelMoved(int newValue) {
        camera.mouseWheelMovedSignalise(newValue);
    }

    public static void setCamera(Camera camera) {
        GameStateUnitControl.camera = camera;
    }

    public static void setMapComponent(GameMapComponent mapComponent) {
        GameStateUnitControl.mapComponent = mapComponent;
    }

    public static void setUnitTile(GameTileComponent unitTile) {
        GameStateUnitControl.unitTile = unitTile;
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {

    }
}
