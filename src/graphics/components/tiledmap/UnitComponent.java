package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import Processing.Units.Unit;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

public class UnitComponent {

    private Unit unit;
    private UnitState state;

    private float x;
    private float y;
    //private int unitId;
    private GameTileComponent tileComponent;

    private Animation idleAnimation;
    private Animation movingAnimation;

    public UnitComponent(GameTileComponent tileComponent) {
        this.tileComponent = tileComponent;
        this.x = this.tileComponent.getX();
        this.y = this.tileComponent.getY();
        this.state = UnitState.IDLE;
        this.unit = this.tileComponent.getTile().getUnit();
        try {
            SpriteSheet idleSpriteSheet = new SpriteSheet("assets/graphics/units/" + this.unit.typeOfUnit.NameOfUnit + ".png", TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
            this.idleAnimation = new Animation(idleSpriteSheet, 0, 0, idleSpriteSheet.getHorizontalCount()-1, 0, true, 150, true);
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    private Tile[] movingArea;

    public void prepareToMove() {
        this.state = UnitState.PREPARE_TO_MOVE;
        movingArea = this.tileComponent.getTile().getUnit().getAllTilesInMoveRange();
        System.out.println(this.movingArea == null);
        for(Tile tile : this.movingArea) {
            System.out.println((tile.coordinates.x - this.tileComponent.getTile().coordinates.x) + " " + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y));
        }
        System.out.println(7);
        //System.out.println(movingArea);
    }

    public boolean isInMovingArea(Tile tile) {
        for(Tile t : this.movingArea) {
            if(t == tile) return true;
        }
        return false;
    }

    public void move(GameTileComponent tile) {
        if(!isInMovingArea(tile.getTile()))return;
        this.x = tile.getX();
        this.y = tile.getY();
        this.tileComponent.getTile().getUnit().move(tile.getTile());
        System.out.println("move");
        this.tileComponent.setUnitComponent(null);
        tile.setUnitComponent(this);
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        switch(this.state) {
            case IDLE:
                this.idleAnimation.draw(this.tileComponent.getX(), this.tileComponent.getY(), this.tileComponent.getWidth(), this.tileComponent.getHeight());
                break;
            case MOVING:
                break;
            case PREPARE_TO_MOVE:
                this.idleAnimation.draw(this.tileComponent.getX(), this.tileComponent.getY(), this.tileComponent.getWidth(), this.tileComponent.getHeight());
                graphics.setColor(new Color(0, 0, 1, 0.2f));
                for(Tile tile : movingArea) {
                    //System.out.println(tile);
                    if(tile!=null){
                        graphics.fillRect(this.tileComponent.getX() + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth(),
                                this.tileComponent.getY() + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight(),
                                this.tileComponent.getWidth(),
                                this.tileComponent.getHeight());
                    }
                }
                break;
            default:
                break;
        }
    }

    public void update(GameContainer gameContainer, int delta) throws SlickException{
        switch(this.state) {
            case IDLE:
                break;
            case MOVING:
                break;
            case PREPARE_TO_MOVE:
                break;
            default:
                break;
        }
    }

    public UnitState getState() {
        return state;
    }

    public void setState(UnitState state) {
        this.state = state;
    }
}
