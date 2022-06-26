package graphics.components.tiledmap;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class GameMapComponent extends MapComponent {

    public GameMapComponent(GUIContext container, GameMap map, float x, float y) {
        super(container, map, x, y);
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        super.render(guiContext, graphics);
        renderTypeOfBuildings(guiContext, graphics);
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
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    ((GameTileComponent) this.tileComponents[i][j]).renderTypeOfBuildings(guiContext, graphics);
                }
            }
        }
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent instanceof EditTileComponent) {
            this.selectedTile = (TileComponent) abstractComponent;
            notifyListeners();
        }
    }

    @Override
    protected void initTiles(GUIContext container) {
        Tile[][] tiles = this.map.getTiles();
        this.tileComponents = new GameTileComponent[this.map.getHeight()][this.map.getWidth()];
        for (int i = 0; i < this.map.getHeight(); i++) {
            for (int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j] = new GameTileComponent(container, tiles[i][j], this.x + i * TileComponent.STANDARD_WIDTH, this.y + j * TileComponent.STANDARD_HEIGHT);
                this.tileComponents[i][j].addListener(this);
            }
        }
    }
}
