package graphics.components.tiledmap;

import Processing.City.City;
import Processing.TileMap.Tile;
import graphics.loads.Images;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

import java.util.LinkedList;

public class CityComponent {

    private float x;
    private float y;
    private GameTileComponent tileComponent;
    private City city;
    private Image cityImage;
    private CityState state;

    public CityComponent(GameTileComponent tileComponent) {
        this.tileComponent = tileComponent;
        this.city = tileComponent.getTile().city;
        this.x = tileComponent.getX();
        this.y = tileComponent.getY();
        this.cityImage = Images.city;
        this.state = CityState.IDLE;
    }

    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        switch (this.state) {
            case IDLE :
                this.cityImage.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                break;
            case SHOW_AREA:
                this.cityImage.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                for(Tile tile : ownedTiles) {
                    //System.out.println(tile);
                    if(tile!=null){
                        graphics.setColor(new Color(0, 1, 0, 0.2f));
                        graphics.fillRect(this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth(),
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight(),
                                this.tileComponent.getWidth(),
                                this.tileComponent.getHeight());
                        graphics.setColor(new Color(0.588f, 0.294f, 0, 1));
                        graphics.drawString(String.valueOf(tile.wealth.production),
                                this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth(),
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight());
                        graphics.setColor(Color.red);
                        graphics.drawString(String.valueOf(tile.wealth.food),
                                this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth(),
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight() + 20);
                        graphics.setColor(Color.orange);
                        graphics.drawString(String.valueOf(tile.wealth.money),
                                this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth(),
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight() + 40);
                        graphics.setColor(Color.magenta);
                        graphics.drawString(String.valueOf(tile.wealth.arcanumScience),
                                this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth() + 50,
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight());
                        graphics.setColor(Color.cyan);
                        graphics.drawString(String.valueOf(tile.wealth.engineeringScience),
                                this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth() + 50,
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight() + 20);
                        graphics.setColor(Color.white);
                        graphics.drawString(String.valueOf(tile.wealth.societyScience),
                                this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth() + 50,
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight() + 40);
                        if(tile.isProcessedByPeople) {
                            graphics.setColor(Color.gray);
                            graphics.fillRect(
                                    this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth() + 40,
                                    y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight() + 70,
                                    20,
                                    20
                            );
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private LinkedList<Tile> ownedTiles;

    public void showArea() {
        ownedTiles = city.ownedTiles;
        this.setState(CityState.SHOW_AREA);
    }

    public void buyTile(GameTileComponent gameTileComponent) {
        this.city.buyTile(gameTileComponent.getTile());
        showArea();
    }

    public boolean isInOwnedArea(GameTileComponent tileComponent) {
        for(Tile tile : ownedTiles) {
            if(tile == tileComponent.getTile()) return true;
        }
        return false;
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public GameTileComponent getTileComponent() {
        return tileComponent;
    }

    public City getCity() {
        return city;
    }

    public Image getCityImage() {
        return cityImage;
    }

    public void setState(CityState state) {
        this.state = state;
        if(state == CityState.SHOW_AREA) {
            this.tileComponent.getMapComponent().getCamera().scaleMap(100 / this.tileComponent.width, this.tileComponent.x + this.tileComponent.width / 2, this.tileComponent.y + this.tileComponent.height / 2);
            this.tileComponent.getMapComponent().getCamera().setScrollLocked(true);
        }
        else if (state == CityState.IDLE) {
            this.tileComponent.getMapComponent().getCamera().setScrollLocked(false);
        }
    }

    public CityState getState() {
        return state;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
