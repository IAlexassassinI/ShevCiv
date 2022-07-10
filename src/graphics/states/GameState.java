package graphics.states;

import Processing.FileHandler.SaveLoadInterface;
import Processing.Game.Game;
import Processing.TileMap.GameMap;
import Processing.Units.Unit;
import Processing.Units.UnitPattern;
import Processing.Utilits.TileFinder.LightPlay;
import graphics.components.button.ButtonComponent;
import graphics.components.camera.Camera;
import graphics.components.tiledmap.GameMapComponent;
import graphics.components.tiledmap.GameTileComponent;
import graphics.loads.Images;
import graphics.panels.CityControlPanel;
import graphics.panels.TechTreePanel;
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

    private boolean isUnitControl = false;
    private boolean isCityControl = false;
    private boolean isTechTreeControl = false;

    private ButtonComponent goButton;
    private ButtonComponent exitButton;

    private CityControlPanel cityControlPanel;
    private UnitControlPanel unitControlPanel;
    private TechTreePanel techTreePanel;

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

    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame, int numberOfPlayers, int levelOfDifficulty) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        GameMap map = SaveLoadInterface.LoadGameMapFromFile("assets/saved_maps/map.txt");
        //map.getTile(0, 0).setTypeOfLand(TypeOfLand.FlatLand);
        //map.getTile(0, 1).setTypeOfLand(TypeOfLand.FlatLand);

        Game game = new Game(map, numberOfPlayers, 0, levelOfDifficulty * 10, levelOfDifficulty);
        Unit worker = new Unit(UnitPattern.ElvenMage, game.getCurrentPlayer(), map.getTile(6,4));
        System.out.println(UnitPattern.ElvenMage.projectile.name);
        map.getTile(6,4).setUnit(worker);
        LightPlay.addToPlayerVision(worker);

        this.game = game;

        this.mapComponent = new GameMapComponent(gameContainer, map, 20, 20);
        this.mapComponent.setGame(this.game);
        this.mapComponent.addListener(this);
        this.camera = new Camera(gameContainer, 20, 20, 1880, 960, this.mapComponent);

        this.cityControlPanel = new CityControlPanel(gameContainer, mapComponent);
        this.unitControlPanel = new UnitControlPanel(gameContainer, mapComponent);

        goButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/techtree.png"), 20, 20, 75, 75);
        goButton.addListener(this);
        exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/exit.png"), 1400, 120, 500, 75);
        exitButton.addListener(this);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Images.background.draw(0,0,1920,1080);
        this.camera.render(gameContainer,graphics);
        graphics.setColor(Color.white);
        graphics.drawString("Turn: " + this.game.year,20, 990);
        graphics.drawString("Current player: " + (this.game.currentPlayer + 1), 20, 1020);
        graphics.drawString("Player race: " + this.game.getCurrentPlayer().race, 220, 1020);
        graphics.drawString("Resources per turn: ", 420, 1020);
        graphics.setColor(Color.orange);
        //System.out.println(gameContainer.getDefaultFont().getWidth("Resources per turn: "));
        graphics.drawString(String.valueOf(this.game.getCurrentPlayer().moneyIncome), 591, 1020);
        graphics.setColor(Color.cyan);
        graphics.drawString(String.valueOf(this.game.getCurrentPlayer().engineeringScienceIncome), 691, 1020);
        graphics.setColor(Color.magenta);
        graphics.drawString(String.valueOf(this.game.getCurrentPlayer().arcanumScienceIncome), 791, 1020);
        graphics.setColor(Color.white);
        graphics.drawString(String.valueOf(this.game.getCurrentPlayer().societyScienceIncome), 891, 1020);

        graphics.drawString("Accumulated resources: ", 1020, 1020);
        graphics.setColor(Color.orange);
        //System.out.println(gameContainer.getDefaultFont().getWidth("Accumulated resources: "));
        graphics.drawString(String.valueOf(this.game.getCurrentPlayer().money), 1220, 1020);
        graphics.setColor(Color.cyan);
        graphics.drawString(String.valueOf(this.game.getCurrentPlayer().engineeringScience), 1320, 1020);
        graphics.setColor(Color.magenta);
        graphics.drawString(String.valueOf(this.game.getCurrentPlayer().arcanumScience), 1420, 1020);
        graphics.setColor(Color.white);
        graphics.drawString(String.valueOf(this.game.getCurrentPlayer().societyScience), 1520, 1020);

        if(this.isUnitControl) {
            this.unitControlPanel.render(gameContainer, graphics);
        }
        else if(this.isCityControl) {
            this.cityControlPanel.render(gameContainer, graphics);
        }
        else if(this.isTechTreeControl) {
            this.techTreePanel.render(gameContainer, graphics);
        }
        if(!isTechTreeControl) {
            this.goButton.render(gameContainer, graphics);
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if(isUnitControl && (this.unitControlPanel.isExit() || this.unitControlPanel.getUnitComponent().getUnit().currentHitPoints <= 0)) {
            this.isUnitControl = false;
        }
        else if(isCityControl && (this.cityControlPanel.isExit())) {
            this.isCityControl = false;
        }
        else if(isCityControl) {
            this.cityControlPanel.update(gameContainer, delta);
        }
        else if(isTechTreeControl && this.techTreePanel.isExit()) {
            this.isTechTreeControl = false;
        }
        this.mapComponent.update(gameContainer, delta);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if(isUnitControl && this.unitControlPanel.contains(newx, newy)) {
            this.unitControlPanel.mouseMovedSignalise(oldx, oldy, newx, newy);
            return;
        }
        else if(this.isCityControl && this.cityControlPanel.contains(newx, newy)) {
            this.cityControlPanel.mouseMovedSignalise(oldx, oldy, newx, newy);
            return;
        }
        else if(this.isTechTreeControl) {
            this.techTreePanel.mouseMovedSignalise(oldx, oldy, newx, newy);
            return;
        }
        this.camera.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.mapComponent.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.goButton.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        if(isUnitControl && this.unitControlPanel.contains(newx, newy)) {
            return;
        }
        else if(this.isCityControl && this.cityControlPanel.contains(newx, newy)) {
            return;
        }
        else if(this.isTechTreeControl) {
            return;
        }
        this.camera.mouseDraggedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        this.button = button;
        if(isUnitControl && this.unitControlPanel.contains(x, y)) {
            return;
        }
        else if(this.isCityControl && this.cityControlPanel.contains(x, y)) {
            return;
        }
        else if(this.isTechTreeControl) {
            return;
        }
        this.mapComponent.mouseClickedSignalise(button, x, y, clickCount);
        this.gameContainer.getInput().consumeEvent();
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if(isUnitControl && this.unitControlPanel.contains(x, y)) {
            this.unitControlPanel.mousePressedSignalise(button, x, y);
            this.gameContainer.getInput().consumeEvent();
            return;
        }
        else if(this.isCityControl && this.cityControlPanel.contains(x, y)) {
            this.cityControlPanel.mousePressedSignalise(button, x, y);
            this.gameContainer.getInput().consumeEvent();
            return;
        }
        else if(this.isTechTreeControl) {
            this.techTreePanel.mousePressedSignalise(button, x, y);
            this.gameContainer.getInput().consumeEvent();
            return;
        }
        else if(this.goButton.contains(x, y)) {
            this.goButton.mousePressedSignalise(button, x, y);
            this.gameContainer.getInput().consumeEvent();
        }
    }

    @Override
    public void mouseWheelMoved(int newValue) {
        if(this.isTechTreeControl) {
            return;
        }
        this.camera.mouseWheelMovedSignalise(newValue);
    }

    @Override
    public void keyPressed(int key, char c) {
        if(key == Input.KEY_ESCAPE) {
            this.stateBasedGame.enterState(MainMenu.ID);
            return;
        }
        if(key == Input.KEY_SPACE && !isCityControl && !isUnitControl && !isTechTreeControl) {
            this.game.players[this.game.currentPlayer].doEndTurn();
            //cityControlPanel.updateCreationList();
        }

    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(isUnitControl || isCityControl || isTechTreeControl) {
            return;
        }
        if(abstractComponent instanceof GameMapComponent) {
            if(((GameMapComponent) abstractComponent).getSelectedTile().getTile().getCity() != null && button == Input.MOUSE_RIGHT_BUTTON && this.game.getCurrentPlayer() == ((GameMapComponent) abstractComponent).getSelectedTile().getTile().getCity().owner) {
                isCityControl = true;
                this.cityControlPanel.setCityComponent(((GameTileComponent)((GameMapComponent) abstractComponent).getSelectedTile()).getCityComponent());
            }
            else if(((GameMapComponent) abstractComponent).getSelectedTile().getTile().getUnit() != null && button == Input.MOUSE_LEFT_BUTTON && this.game.getCurrentPlayer() == ((GameMapComponent) abstractComponent).getSelectedTile().getTile().getUnit().owner) {
                isUnitControl = true;
                this.unitControlPanel.setUnitComponent(((GameTileComponent)((GameMapComponent) abstractComponent).getSelectedTile()).getUnitComponent());
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
                isTechTreeControl = true;
                if(this.techTreePanel == null) {
                    this.techTreePanel = new TechTreePanel(this.gameContainer, this.game.getCurrentPlayer());
                }
                else {
                    this.techTreePanel.setPlayer(this.game.getCurrentPlayer());
                }
            }
        }
    }
}
