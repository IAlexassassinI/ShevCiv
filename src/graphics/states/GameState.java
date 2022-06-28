package graphics.states;

import Processing.FileHandler.SaveLoadInterface;
import Processing.Game.Game;
import Processing.TileMap.GameMap;
import Processing.TileMap.TileUtils.TypeOfLand;
import graphics.components.button.ButtonComponent;
import graphics.components.camera.Camera;
import graphics.components.tiledmap.GameMapComponent;
import graphics.components.tiledmap.GameTileComponent;
import graphics.components.tiledmap.UnitState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState implements ComponentListener {

    public static final int ID = 3;

    private Camera camera;
    private GameMapComponent mapComponent;

    private boolean isUnitConrol;
    private GameTileComponent currentUnitTile;

    private ButtonComponent goButton;
    private ButtonComponent exitButton;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        //GameMap map = new GameMap(20, 20);
        GameMap map = SaveLoadInterface.LoadGameMapFromFile("assets/saved_maps/map.txt");
        //map.getTile(0, 0).setTypeOfLand(TypeOfLand.FlatLand);
        //map.getTile(0, 1).setTypeOfLand(TypeOfLand.FlatLand);
        Game game = new Game(map, 1);
        this.mapComponent = new GameMapComponent(gameContainer, map, 20, 20);
        this.mapComponent.addListener(this);
        this.camera = new Camera(gameContainer, 20, 20, 1880, 960, this.mapComponent);

        goButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/go.png"), 1400, 20, 500, 75);
        goButton.addListener(this);
        exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/exit.png"), 1400, 120, 500, 75);
        exitButton.addListener(this);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.camera.render(gameContainer,graphics);
        if(this.isUnitConrol) {
            goButton.render(gameContainer, graphics);
            exitButton.render(gameContainer, graphics);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        this.camera.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.mapComponent.mouseMovedSignalise(oldx, oldy, newx, newy);
        if(this.isUnitConrol) {
            goButton.mouseMovedSignalise(oldx, oldy, newx, newy);
            exitButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        this.camera.mouseDraggedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        this.mapComponent.mouseClickedSignalise(button, x, y, clickCount);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if(this.isUnitConrol) {
            goButton.mousePressedSignalise(button, x, y);
            exitButton.mousePressedSignalise(button, x, y);
        }
    }

    @Override
    public void mouseWheelMoved(int newValue) {
        this.camera.mouseWheelMovedSignalise(newValue);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        System.out.println(1);
        if(abstractComponent instanceof GameMapComponent) {
            if(((GameMapComponent) abstractComponent).getSelectedTile().getTile().getUnit() != null) {
                isUnitConrol = true;
                this.camera.setWidth(1360);
                currentUnitTile = (GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile();
            }
            else if(this.currentUnitTile != null && this.currentUnitTile.getUnitComponent().isInMovingArea(((GameMapComponent) abstractComponent).getSelectedTile().getTile())) {
                //((GameMapComponent) abstractComponent).getSelectedTile().getTile()
                this.currentUnitTile.getUnitComponent().setState(UnitState.IDLE);
                this.currentUnitTile.getUnitComponent().move((GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile());
                this.currentUnitTile = (GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile();
            }
            /*if(isUnitConrol && this.currentUnitTile != null) {
                this.currentUnitTile.getUnitComponent().move(this.currentUnitTile);
            }*/
            else {
                this.camera.setWidth(1880);
                if(this.currentUnitTile != null) {
                    this.currentUnitTile.getUnitComponent().setState(UnitState.IDLE);
                    this.currentUnitTile = null;
                }
                isUnitConrol = false;
            }
        }
        else if(abstractComponent instanceof ButtonComponent) {
            if(abstractComponent == this.goButton) {
                this.currentUnitTile.getUnitComponent().prepareToMove();
                System.out.println(5);
            }
        }
    }
}
