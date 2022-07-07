package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import graphics.loads.Images;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;

public class GameTileComponent extends TileComponent {

    private int mapX;
    private int mapY;

    private UnitComponent unitComponent;
    private CityComponent cityComponent;
    private GameMapComponent mapComponent;

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

    public void renderEmbeddedTypeOfBuilding(GUIContext guiContext, Graphics graphics) throws SlickException {
        Images.typesOfBuildingSpriteSheet.getSprite(this.getTile().typeOfBuilding.Type,0).drawEmbedded(this.x, this.y, this.width, this.height);
    }

    public void renderCity(GUIContext guiContext, Graphics graphics) throws SlickException {
        if(this.getTile().getCity() != null && this.cityComponent == null) {
            this.cityComponent = new CityComponent(this);
        }
        if(this.getTile().getCity() != null && this.cityComponent != null) {
            this.cityComponent.render(guiContext, graphics);
        }
        /*if(this.getTile().getCity() != null) {
            Images.city.draw(this.x, this.y, this.width, this.height);
        }*/
    }

    public void renderAdditionals(GUIContext guiContext, Graphics graphics) throws SlickException {
        //if(this.getTile().getTypeOfLand().Type > this.getTile().getMap().getTile(this.))
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
    public void translate(float dx, float dy) {
        super.translate(dx, dy);
        if(this.unitComponent != null) {
            this.unitComponent.translate(dx, dy);
        }
        if(this.cityComponent != null) {
            this.cityComponent.translate(dx, dy);
        }
    }

    public CityComponent getCityComponent() {
        return cityComponent;
    }

    public void setCityComponent(CityComponent cityComponent) {
        this.cityComponent = cityComponent;
    }

    public UnitComponent getUnitComponent() {
        return unitComponent;
    }

    public void setUnitComponent(UnitComponent unitComponent) {
        this.unitComponent = unitComponent;
    }

    /*@Override
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
    }*/

    public void mouseClickedSignalise(int button, int x, int y, int clickCount) {
        if(contains(x, y)) {
            this.mouseClicked = true;
            notifyListeners();
            System.out.println(this.listeners);
        }
        else {
            this.mouseClicked = false;
        }
    }

    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        this.mouseClicked = false;
        if(this.contains(newx, newy)) {
            this.mouseOver = true;
        }
        else {
            this.mouseOver = false;
        }
    }

    @Override
    public void scale(float sx, float sy, float px, float py) {
        super.scale(sx, sy, px, py);
        if(this.unitComponent != null) {
            this.unitComponent.setX(px + (this.unitComponent.getX() - px) * sx);
            this.unitComponent.setY(py + (this.unitComponent.getY() - py) * sy);
        }
        if(this.cityComponent != null) {
            this.cityComponent.setX(px + (this.cityComponent.getX() - px) * sx);
            this.cityComponent.setY(py + (this.cityComponent.getY() - py) * sy);
        }
    }



    public void setMapComponent(GameMapComponent mapComponent) {
        this.mapComponent = mapComponent;
    }

    public GameMapComponent getMapComponent() {
        return mapComponent;
    }

    public void update(GameContainer gameContainer, int delta) throws SlickException{
        if(this.getTile().getUnit() == null && this.unitComponent != null) this.unitComponent = null;
        if(this.getTile().getCity() == null && this.cityComponent != null) this.cityComponent = null;
        if(this.unitComponent != null && this.getTile().getUnit() != null) this.unitComponent.update(gameContainer, delta);
    }
}
