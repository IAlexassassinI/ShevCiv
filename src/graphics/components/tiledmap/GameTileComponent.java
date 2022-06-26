package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

public class GameTileComponent extends TileComponent {

    private int mapX;
    private int mapY;

    private UnitComponent unitComponent;

    public GameTileComponent(GUIContext container, Tile tile, float x, float y) {
        super(container, tile, x, y);
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        super.render(guiContext, graphics);
        renderFilter(guiContext, graphics);
    }

    public void renderUnit(GUIContext guiContext, Graphics graphics) throws SlickException {
        if(this.getTile().getUnit() != null && this.unitComponent == null) {
            this.unitComponent = new UnitComponent(this);
        }
        if(this.getTile().getUnit() != null && this.unitComponent != null) {
            this.unitComponent.render(guiContext, graphics);
        }
    }

    public void renderTypeOfBuildings(GUIContext guiContext, Graphics graphics) throws SlickException {

    }

    public void renderFilter(GUIContext guiContext, Graphics graphics) throws SlickException {
        if(this.mouseClicked) {
            graphics.setColor(this.mouseDownColor);
            graphics.fillRect(this.x, this.y, this.width, this.height);
        }
        else if(this.mouseOver) {
            graphics.setColor(this.mouseOverColor);
            graphics.fillRect(this.x, this.y, this.width, this.height);
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if(button == Input.MOUSE_LEFT_BUTTON && contains(x, y)) {
            this.mouseClicked = true;
            notifyListeners();
        }
        else {
            this.mouseClicked = false;
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        this.mouseClicked = false;
        if(this.contains(newx, newy)) {
            this.mouseOver = true;
        }
        else {
            this.mouseOver = false;
        }
    }

}
