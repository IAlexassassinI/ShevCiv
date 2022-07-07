package graphics.states;

import Processing.FileHandler.SaveLoadInterface;
import Processing.Game.Game;
import Processing.TileMap.GameMap;
import Processing.TileMap.TileUtils.TypeOfLand;
import Processing.Units.Unit;
import Processing.Units.UnitPattern;
import Processing.Utilits.TileFinder.LightPlay;
import graphics.components.button.ButtonComponent;
import graphics.components.camera.Camera;
import graphics.components.tiledmap.CityState;
import graphics.components.tiledmap.GameMapComponent;
import graphics.components.tiledmap.GameTileComponent;
import graphics.components.tiledmap.UnitState;
import graphics.panels.UnitControlPanel;
import org.newdawn.slick.*;
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

    private UnitControlPanel unitControlPanel;

    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;

    private int button;

    private Game game;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        //GameMap map = new GameMap(20, 20);
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        GameMap map = SaveLoadInterface.LoadGameMapFromFile("assets/saved_maps/map.txt");
        //map.getTile(0, 0).setTypeOfLand(TypeOfLand.FlatLand);
        //map.getTile(0, 1).setTypeOfLand(TypeOfLand.FlatLand);

        Game game = new Game(map, 2, 0, 50, 2);
        Unit worker = new Unit(UnitPattern.Worker, game.getCurrentPlayer(), map.getTile(6,4));
        //LightPlay.addToPlayerVision(worker);
        map.getTile(6,4).setUnit(worker);

        this.game = game;

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
            this.unitControlPanel.render(gameContainer, graphics);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if(isUnitConrol && (this.unitControlPanel.isExit() || this.unitControlPanel.getUnitComponent().getUnit().currentHitPoints <= 0)) {
            this.isUnitConrol = false;
        }
        this.mapComponent.update(gameContainer, delta);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if(isUnitConrol && this.unitControlPanel.contains(newx, newy)) {
            this.unitControlPanel.mouseMovedSignalise(oldx, oldy, newx, newy);
            return;
        }
        this.camera.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.mapComponent.mouseMovedSignalise(oldx, oldy, newx, newy);
        if(this.isUnitConrol) {
            goButton.mouseMovedSignalise(oldx, oldy, newx, newy);
            exitButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        if(isUnitConrol && this.unitControlPanel.contains(newx, newy)) {
            return;
        }
        this.camera.mouseDraggedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        this.button = button;
        if(isUnitConrol && this.unitControlPanel.contains(x, y)) {
            return;
        }
        this.mapComponent.mouseClickedSignalise(button, x, y, clickCount);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if(isUnitConrol && this.unitControlPanel.contains(x, y)) {
            this.unitControlPanel.mousePressedSignalise(button, x, y);
            return;
        }
    }

    @Override
    public void mouseWheelMoved(int newValue) {
        this.camera.mouseWheelMovedSignalise(newValue);
    }

    @Override
    public void keyPressed(int key, char c) {
        if(key == Input.KEY_ESCAPE) {
            this.stateBasedGame.enterState(MainMenu.ID);
            return;
        }
        if(key == Input.KEY_SPACE) {
            this.game.players[this.game.currentPlayer].doEndTurn();
        }

    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        //System.out.println(1);
        if(abstractComponent instanceof GameMapComponent) {
            if(isUnitConrol) {
                return;
            }
            if(((GameMapComponent) abstractComponent).getSelectedTile().getTile().getUnit() != null && button == Input.MOUSE_LEFT_BUTTON) {
                isUnitConrol = true;
                if(this.unitControlPanel == null)this.unitControlPanel = new UnitControlPanel(this.gameContainer, this.mapComponent, ((GameTileComponent)((GameMapComponent) abstractComponent).getSelectedTile()).getUnitComponent());
                else {
                    this.unitControlPanel.setUnitComponent(((GameTileComponent)((GameMapComponent) abstractComponent).getSelectedTile()).getUnitComponent());
                }
                //this.camera.setWidth(1360);
                currentUnitTile = (GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile();
            }
            if(((GameMapComponent) abstractComponent).getSelectedTile().getTile().getCity() != null && button == Input.MOUSE_RIGHT_BUTTON) {
                if(((GameTileComponent)((GameMapComponent) abstractComponent).getSelectedTile()).getCityComponent().getState() == CityState.IDLE) {
                    ((GameTileComponent)((GameMapComponent) abstractComponent).getSelectedTile()).getCityComponent().showArea();
                }
                else if(((GameTileComponent)((GameMapComponent) abstractComponent).getSelectedTile()).getCityComponent().getState() == CityState.SHOW_AREA) {
                    ((GameTileComponent)((GameMapComponent) abstractComponent).getSelectedTile()).getCityComponent().setState(CityState.IDLE);
                }
                //this.camera.setWidth(1360);
            }
            /*else if(this.currentUnitTile != null && this.currentUnitTile.getUnitComponent().isInMovingArea(((GameMapComponent) abstractComponent).getSelectedTile().getTile())) {
                //((GameMapComponent) abstractComponent).getSelectedTile().getTile()
                this.currentUnitTile.getUnitComponent().setState(UnitState.IDLE);
                this.currentUnitTile.getUnitComponent().move((GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile());
                this.currentUnitTile = (GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile();
            }

            else {
                this.camera.setWidth(1880);
                if(this.currentUnitTile != null) {
                    this.currentUnitTile.getUnitComponent().setState(UnitState.IDLE);
                    this.currentUnitTile = null;
                }
                isUnitConrol = false;
            }*/
        }
        else if(abstractComponent instanceof ButtonComponent) {
            if(abstractComponent == this.goButton) {
                this.currentUnitTile.getUnitComponent().prepareToMove();
                System.out.println(5);
            }
        }
    }
}
