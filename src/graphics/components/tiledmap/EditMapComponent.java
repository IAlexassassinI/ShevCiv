package graphics.components.tiledmap;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class EditMapComponent extends MapComponent {

    private EditMode editMode;

    public EditMapComponent(GUIContext container, GameMap map, float x, float y) {
        super(container, map, x, y);
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        super.render(guiContext, graphics);
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    ((EditTileComponent) this.tileComponents[i][j]).renderFilter(guiContext, graphics);
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

    public void setEditMode(EditMode editMode) {
        this.editMode = editMode;
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                ((EditTileComponent)this.tileComponents[i][j]).setEditMode(editMode);
            }
        }
    }

    public EditMode getEditMode() {
        return editMode;
    }

    @Override
    protected void initTiles(GUIContext container) {
        Tile[][] tiles = this.map.getTiles();
        this.tileComponents = new EditTileComponent[this.map.getHeight()][this.map.getWidth()];
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j] = new EditTileComponent(container, tiles[i][j], this.x + i * TileComponent.STANDARD_WIDTH, this.y + j * TileComponent.STANDARD_HEIGHT);
                this.tileComponents[i][j].addListener(this);
            }
        }
    }
}
