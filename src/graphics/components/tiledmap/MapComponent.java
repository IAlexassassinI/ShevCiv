package graphics.components.tiledmap;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class MapComponent extends AbstractComponent implements ComponentListener {

    private GameMap map;

    private float x;
    private float y;

    private float width;
    private float height;

    protected TileComponent[][] tileComponents;
    protected TileComponent selectedTile;

    private boolean locked;

    public MapComponent(GUIContext container, GameMap map, float x, float y) {
        super(container);
        this.map = map;
        this.x = x;
        this.y = y;
        init(container);
    }

    private void init(GUIContext container) {
        this.selectedTile = null;
        this.width = this.map.getWidth() * TileComponent.STANDARD_WIDTH;
        this.height = this.map.getHeight() * TileComponent.STANDARD_HEIGHT;
        this.tileComponents = new TileComponent[this.map.getHeight()][this.map.getWidth()];
        Tile[][] tiles = this.map.getTiles();
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j] = new TileComponent(container, tiles[i][j], this.x + i * TileComponent.STANDARD_WIDTH, this.y + j * TileComponent.STANDARD_HEIGHT);
                this.tileComponents[i][j].addListener(this);
            }
        }
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        //Renderer.get().glBegin(SGL.GL_QUADS);
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j].render(guiContext, graphics);
            }
        }
        //Renderer.get().glEnd();
    }

    public void scale(float k) {
        scale(k, this.x, this.y);
    }

    public void scale(float k, float px, float py) {
        this.width *= k;
        this.height *= k;
        this.x = px + (this.x - px) * k;
        this.y = py + (this.y - py) * k;
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j].scale(k, k, px, py);
            }
        }
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j].translate(dx, dy);
            }
        }
    }

    public TileComponent getSelectedTile() {
        return selectedTile;
    }

    @Override
    public void setLocation(int i, int i1) {

    }

    @Override
    public int getX() {
        return (int) this.x;
    }

    @Override
    public int getY() {
        return (int) this.y;
    }

    @Override
    public int getWidth() {
        return (int) this.width;
    }

    @Override
    public int getHeight() {
        return (int) this.height;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent instanceof TileComponent) {
            if(selectedTile == null) selectedTile = (TileComponent) abstractComponent;
            else if (selectedTile == abstractComponent) selectedTile = null;
            else {
                this.selectedTile.setSelected(false);
                this.selectedTile = (TileComponent) abstractComponent;
            }
            this.notifyListeners();
        }
    }
}
