package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import Processing.Units.Ability.Colonize;
import Processing.Units.Ability.SpecialAbility;
import Processing.Units.Unit;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

import java.util.LinkedList;

public class UnitComponent {

    private Unit unit;
    private UnitState state;

    private float x;
    private float y;
    //private int unitId;
    private GameTileComponent tileComponent;

    private Animation idleRightAnimation;
    private Animation idleLeftAnimation;
    private Animation movingRightAnimation;
    private Animation movingLeftAnimation;

    private int attackingTime = 1000;
    private int movingTime = 1000;

    private boolean left = false;
    private boolean right = true;

    public UnitComponent(GameTileComponent tileComponent) {
        this.tileComponent = tileComponent;
        this.x = this.tileComponent.getX();
        this.y = this.tileComponent.getY();
        this.state = UnitState.IDLE;
        this.unit = this.tileComponent.getTile().getUnit();
        try {
            SpriteSheet idleRightSpriteSheet = new SpriteSheet("assets/graphics/units/" + this.unit.typeOfUnit.NameOfUnit + "IdleRight.png", TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
            SpriteSheet idleLeftSpriteSheet = new SpriteSheet("assets/graphics/units/" + this.unit.typeOfUnit.NameOfUnit + "IdleLeft.png", TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
            SpriteSheet movingRightSpriteSheet = new SpriteSheet("assets/graphics/units/" + this.unit.typeOfUnit.NameOfUnit + "MovingRight.png", TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
            SpriteSheet movingLeftSpriteSheet = new SpriteSheet("assets/graphics/units/" + this.unit.typeOfUnit.NameOfUnit + "MovingLeft.png", TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);

            this.idleRightAnimation = new Animation(idleRightSpriteSheet, 0, 0, idleRightSpriteSheet.getHorizontalCount()-1, 0, true, 150, true);
            this.idleLeftAnimation = new Animation(idleLeftSpriteSheet, 0, 0, idleLeftSpriteSheet.getHorizontalCount()-1, 0, true, 150, true);
            this.movingRightAnimation = new Animation(movingRightSpriteSheet, 0, 0, movingRightSpriteSheet.getHorizontalCount()-1, 0, true, 150, true);
            this.movingLeftAnimation = new Animation(movingLeftSpriteSheet, 0, 0, movingLeftSpriteSheet.getHorizontalCount()-1, 0, true, 150, true);
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    private Tile[] movingArea;
    private Tile[] attackingArea;

    public void prepareToMove() {
        this.state = UnitState.PREPARE_TO_MOVE;
        this.movingArea = this.unit.getAllTilesInMoveRange();
        /*System.out.println(this.movingArea == null);
        for(Tile tile : this.movingArea) {
            System.out.println((tile.coordinates.x - this.tileComponent.getTile().coordinates.x) + " " + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y));
        }
        System.out.println(7);*/
        //System.out.println(movingArea);
    }

    public void prepareToAttack() {
        this.state = UnitState.PREPARE_TO_ATTACK;
        if(this.unit.typeOfUnit.isRanged) {
            this.attackingArea = this.unit.prepareToShoot();
        }
        else {
            this.attackingArea = this.unit.prepareToMeleeAttack().toArray(new Tile[0]);
            /*LinkedList<Tile> attackingArea = this.unit.prepareToMeleeAttack();
            if(attackingArea != null) {

            }*/
        }
    }

    public void colonise() {
        if(((Colonize) this.unit.Abilities.get(0)).prepareFoundCiti()) {
            ((Colonize) this.unit.Abilities.get(0)).foundNewCiti();
        }
    }

    public boolean isInMovingArea(Tile tile) {
        for(Tile t : this.movingArea) {
            if(t == tile) return true;
        }
        return false;
    }

    public boolean isInAttackingArea(Tile tile) {
        for(Tile t : this.attackingArea) {
            if(t == tile) return true;
        }
        return false;
    }

    LinkedList<Tile> movingPath;

    public void attack(GameTileComponent tile) {
        this.state = UnitState.IDLE;
    }

    public void move(GameTileComponent tile) {
        if(!isInMovingArea(tile.getTile())){
            this.state = UnitState.IDLE;
            return;
        }
        //this.x = tile.getX();
        //this.y = tile.getY();
        this.movingPath = this.tileComponent.getTile().getUnit().move(tile.getTile());
        setState(UnitState.MOVING);
        //System.out.println(movingPath);
        //System.out.println("move");
        this.tileComponent.setUnitComponent(null);
        this.tileComponent = tile;
        tile.setUnitComponent(this);
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        switch(this.state) {
            case IDLE:
                if(left) {
                    this.idleLeftAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                else if (right){
                    this.idleRightAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                break;
            case MOVING:
                if(left) {
                    this.movingLeftAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                else if (right){
                    this.movingRightAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                break;
            case PREPARE_TO_MOVE:
                if(left) {
                    this.idleLeftAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                else if (right){
                    this.idleRightAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                graphics.setColor(new Color(0, 0, 1, 0.2f));
                for(Tile tile : movingArea) {
                    //System.out.println(tile);
                    if(tile!=null){
                        graphics.fillRect(this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth(),
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight(),
                                this.tileComponent.getWidth(),
                                this.tileComponent.getHeight());
                    }
                }
                break;
            case PREPARE_TO_ATTACK:
                if(left) {
                    this.idleLeftAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                else if (right){
                    this.idleRightAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                graphics.setColor(new Color(1, 0, 0, 0.2f));
                for(Tile tile : attackingArea) {
                    //System.out.println(tile);
                    if(tile!=null){
                        graphics.fillRect(this.x + (tile.coordinates.x - this.tileComponent.getTile().coordinates.x) * this.tileComponent.getWidth(),
                                y + (tile.coordinates.y - this.tileComponent.getTile().coordinates.y) * this.tileComponent.getHeight(),
                                this.tileComponent.getWidth(),
                                this.tileComponent.getHeight());
                    }
                }
            default:
                break;
        }
    }

    public void update(GameContainer gameContainer, int delta) throws SlickException{
        switch(this.state) {
            case IDLE:
                break;
            case MOVING:
                updateMoving(gameContainer, delta);
                break;
            case PREPARE_TO_MOVE:
                break;
            default:
                break;
        }
    }

    public void updateMoving(GameContainer gameContainer, int delta) throws SlickException{
        if(this.movingPath == null || this.movingPath.size() == 0){
            this.state = UnitState.IDLE;
            return;
        }
        if(!moveToTile(this.movingPath.getFirst(), gameContainer, delta)) {
            this.movingPath.removeFirst();
            updateMoving(gameContainer, delta);
        }
    }

    private boolean moveToTile(Tile tile, GameContainer gameContainer, int delta) {
        GameTileComponent destination = this.tileComponent.getMapComponent().getTileComponent(tile.coordinates.x, tile.coordinates.y);
        if(destination.getX() == this.x && destination.getY() == this.y)return false;
        float dwidth = this.tileComponent.getWidth() / (float)this.movingTime * (float)delta;
        float dheight = this.tileComponent.getHeight() / (float)this.movingTime * (float)delta;
        //System.out.println(this.tileComponent.getWidth() / this.movingTime);
        //System.out.println(destination.getX() + " " +this.x + " " +destination.getY() + " " +this.y);
        if(this.x < destination.getX()) {
            left = false;
            right = true;
            if(this.x + dwidth > destination.getX()) {
                this.x = destination.getX();
            }
            else this.x += dwidth;
        }
        else if(this.x > destination.getX()) {
            left = true;
            right = false;
            if(this.x - dwidth < destination.getX()) {
                this.x = destination.getX();
            }
            else this.x -= dwidth;
        }

        if(this.y < destination.getY()) {
            if(this.y + dheight > destination.getY()) {
                this.y = destination.getY();
            }
            else this.y += dheight;
        }
        else if(this.y > destination.getY()) {
            if(this.y - dheight < destination.getY()) {
                this.y = destination.getY();
            }
            else this.y -= dheight;
        }
        return true;
    }

    public Animation getCurrentAnimation() {
        if(left) {
            if(this.state == UnitState.MOVING) return movingLeftAnimation;
            else return idleLeftAnimation;
        }
        else if(right) {
            if(this.state == UnitState.MOVING) return movingRightAnimation;
            else return idleRightAnimation;
        }
        return idleLeftAnimation;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Unit getUnit() {
        return unit;
    }

    public UnitState getState() {
        return state;
    }

    public void setState(UnitState state) {
        this.state = state;
    }
}
