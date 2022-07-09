package graphics.components.tiledmap;

import Processing.Game.Game;
import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import graphics.loads.Images;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class GameMapComponent extends MapComponent {

    private Game game;

    public GameMapComponent(GUIContext container, GameMap map, float x, float y) {
        super(container, map, x, y);
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        super.render(guiContext, graphics);
        renderTypeOfBuildings(guiContext, graphics);
        renderCity(guiContext, graphics);
        renderUnits(guiContext, graphics);
        renderFilters(guiContext, graphics);
    }

    public void renderUnits(GUIContext guiContext, Graphics graphics) throws SlickException {
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    ((GameTileComponent) this.tileComponents[i][j]).renderUnit(guiContext, graphics);
                }
            }
        }
    }

    public void renderFilters(GUIContext guiContext, Graphics graphics) throws SlickException {
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    ((GameTileComponent) this.tileComponents[i][j]).renderFilter(guiContext, graphics);
                }
            }
        }
    }

    public void renderTypeOfBuildings(GUIContext guiContext, Graphics graphics) throws SlickException {
        Images.typesOfBuildingSpriteSheet.startUse();
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    ((GameTileComponent) this.tileComponents[i][j]).renderEmbeddedTypeOfBuilding(guiContext, graphics);
                }
            }
        }
        Images.typesOfBuildingSpriteSheet.endUse();
    }

    public void renderCity(GUIContext guiContext, Graphics graphics) throws SlickException {
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    ((GameTileComponent) this.tileComponents[i][j]).renderCity(guiContext, graphics);
                }
            }
        }
    }

    public GameTileComponent getTileComponent(int tileX, int tileY) {
        return (GameTileComponent) this.tileComponents[tileY][tileX];
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent instanceof GameTileComponent) {
            this.selectedTile = (TileComponent) abstractComponent;
            notifyListeners();
        }
    }

    public void update(GameContainer gameContainer, int delta) throws SlickException{
        for (int i = 0; i < this.map.getHeight(); i++) {
            for (int j = 0; j < this.map.getWidth(); j++) {
                ((GameTileComponent)this.tileComponents[i][j]).update(gameContainer, delta);
            }
        }
    }

    @Override
    protected void initTiles(GUIContext container) {
        Tile[][] tiles = this.map.getTiles();
        this.tileComponents = new GameTileComponent[this.map.getHeight()][this.map.getWidth()];
        for (int i = 0; i < this.map.getHeight(); i++) {
            for (int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j] = new GameTileComponent(container, tiles[i][j], this.x + j * TileComponent.STANDARD_WIDTH, this.y + i * TileComponent.STANDARD_HEIGHT);
                this.tileComponents[i][j].addListener(this);
                ((GameTileComponent)this.tileComponents[i][j]).setMapComponent(this);
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
