package graphics.components.tiledmap;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import graphics.components.camera.Camera;
import graphics.loads.Images;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class MapComponent extends AbstractComponent implements ComponentListener {

    protected GameMap map;

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    protected TileComponent[][] tileComponents;
    protected TileComponent selectedTile;

    protected Camera camera;

    private boolean locked;

    public MapComponent(GUIContext container, GameMap map, float x, float y) {
        super(container);
        this.map = map;
        this.x = x;
        this.y = y;
        init(container);
    }

    protected void init(GUIContext container) {
        this.selectedTile = null;
        this.width = this.map.getWidth() * TileComponent.STANDARD_WIDTH;
        this.height = this.map.getHeight() * TileComponent.STANDARD_HEIGHT;
        initTiles(container);
    }

    protected void initTiles(GUIContext container) {
        Tile[][] tiles = this.map.getTiles();
        this.tileComponents = new TileComponent[this.map.getHeight()][this.map.getWidth()];
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j] = new TileComponent(container, tiles[i][j], this.x + j * TileComponent.STANDARD_WIDTH, this.y + i * TileComponent.STANDARD_HEIGHT);
                this.tileComponents[i][j].addListener(this);
            }
        }
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    this.tileComponents[i][j].render(guiContext, graphics);
                }
            }
        }
        Images.typesOfLandSpriteSheet.startUse();
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    this.tileComponents[i][j].renderEmbeddedTypeOfLand(guiContext, graphics);
                }
            }
        }
        Images.typesOfLandSpriteSheet.endUse();
        Images.typesOfFloraSpriteSheet.startUse();
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    this.tileComponents[i][j].renderEmbeddedTypeOfFlora(guiContext, graphics);
                }
            }
        }
        Images.typesOfFloraSpriteSheet.endUse();
        Images.resourcesSpriteSheet.startUse();
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                if(this.camera == null || this.camera.containsTileComponent(this.tileComponents[i][j])) {
                    this.tileComponents[i][j].renderEmbeddedResource(guiContext, graphics);
                }
            }
        }
        Images.resourcesSpriteSheet.endUse();
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

    public void setCamera(Camera camera) {
        this.camera = camera;
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j].setCamera(camera);
            }
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void mouseClickedSignalise(int button, int x, int y, int clickCount) {
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j].mouseClickedSignalise(button, x, y, clickCount);
            }
        }
    }

    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        for(int i = 0; i < this.map.getHeight(); i++) {
            for(int j = 0; j < this.map.getWidth(); j++) {
                this.tileComponents[i][j].mouseMovedSignalise(oldx, oldy, newx, newy);
            }
        }
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
            System.out.println(3);
            this.notifyListeners();
        }
    }
}
