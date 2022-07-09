package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.Ability.Colonize;
import Processing.Units.Ability.ConstructSomethingOnTile;
import Processing.Units.Ability.GetCargoSmall;
import Processing.Units.Ability.SpecialAbility;
import Processing.Units.Projectile;
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

    private int movingTime = 1000;
    private int attackingTime = movingTime * 2 + 1000;
    private int currentAttackingTime = 0;

    private ProjectileComponent projectileComponent;

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

            this.idleRightAnimation = new Animation(idleRightSpriteSheet, 0, 0, idleRightSpriteSheet.getHorizontalCount()-1, 0, true, 200, true);
            this.idleLeftAnimation = new Animation(idleLeftSpriteSheet, 0, 0, idleLeftSpriteSheet.getHorizontalCount()-1, 0, true, 200, true);
            this.movingRightAnimation = new Animation(movingRightSpriteSheet, 0, 0, movingRightSpriteSheet.getHorizontalCount()-1, 0, true, 200, true);
            this.movingLeftAnimation = new Animation(movingLeftSpriteSheet, 0, 0, movingLeftSpriteSheet.getHorizontalCount()-1, 0, true, 200, true);
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    private Tile[] movingArea;
    private Tile[] attackingArea;
    private Tile[] cargoArea;

    public void prepareToMove() {
        this.setState(UnitState.PREPARE_TO_MOVE);
        this.movingArea = this.unit.getAllTilesInMoveRange();
    }

    public void prepareToMoveWithoutVisualization() {
        this.setState(UnitState.IDLE);
        this.movingArea = this.unit.getAllTilesInMoveRange();
    }

    public void prepareToAttack() {
        this.setState(UnitState.PREPARE_TO_ATTACK);
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

    public void prepareToAttackWithoutVisualization() {
        this.setState(UnitState.IDLE);
        if(this.unit.typeOfUnit.isRanged) {
            this.attackingArea = this.unit.prepareToShoot();
        }
        else {
            this.attackingArea = this.unit.prepareToMeleeAttack().toArray(new Tile[0]);
        }
    }

    public void relocateCargo(GameTileComponent tileComponent) {
        if(isInCargoArea(tileComponent.getTile())) {
            ((GetCargoSmall) this.unit.Abilities.get(0)).relocateCargo(tileComponent.getTile());
        }
        setState(UnitState.IDLE);
    }

    public void prapareCargo() {
        this.setState(UnitState.PREPARE_CARGO);
        this.cargoArea = ((GetCargoSmall) this.unit.Abilities.get(0)).prepareCargo().toArray(new Tile[0]);
    }

    public boolean colonise() {
        if(((Colonize) this.unit.Abilities.get(0)).prepareFoundCiti()) {
            ((Colonize) this.unit.Abilities.get(0)).foundNewCiti();
            return true;
        }
        return false;
    }

    public void buildFarmland() {
        if(this.tileComponent.getTile().getTypeOfBuilding() == TypeOfBuilding.Farmland) {
            return;
        }
        if(this.tileComponent.getTile().buildingInProcess != null && this.tileComponent.getTile().buildingInProcess.object == TypeOfBuilding.Farmland) {
            ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).workOnTile();
            return;
        }
        ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).designateStructureOnTile(TypeOfBuilding.Farmland);
        ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).workOnTile();
    }

    public void buildMine() {
        if(this.tileComponent.getTile().getTypeOfBuilding() == TypeOfBuilding.Mine) {
            return;
        }
        if(this.tileComponent.getTile().buildingInProcess != null && this.tileComponent.getTile().buildingInProcess.object == TypeOfBuilding.Mine) {
            ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).workOnTile();
            return;
        }
        ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).designateStructureOnTile(TypeOfBuilding.Mine);
        ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).workOnTile();
    }

    public void buildSawmill() {
        if(this.tileComponent.getTile().getTypeOfBuilding() == TypeOfBuilding.Sawmill) {
            return;
        }
        if(this.tileComponent.getTile().buildingInProcess != null && this.tileComponent.getTile().buildingInProcess.object == TypeOfBuilding.Sawmill) {
            ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).workOnTile();
            return;
        }
        ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).designateStructureOnTile(TypeOfBuilding.Sawmill);
        ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).workOnTile();
    }

    public void buildNone() {
        ((ConstructSomethingOnTile) this.unit.Abilities.get(0)).designateStructureOnTile(TypeOfBuilding.none);

        //this.tileComponent.getTile().setTypeOfBuilding(TypeOfBuilding.none);
    }

    public boolean isInMovingArea(Tile tile) {
        for(Tile t : this.movingArea) {
            if(t == tile) return true;
        }
        return false;
    }

    public boolean isInCargoArea(Tile tile) {
        for(Tile t : this.cargoArea) {
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

    private LinkedList<Tile> movingPath;
    private GameTileComponent tileToAttack;

    public void attack(GameTileComponent tile) {
        if(!isInAttackingArea(tile.getTile())) {
            this.state = UnitState.IDLE;
            return;
        }
        this.tileToAttack = tile;
        setState(UnitState.ATTACKING);
    }

    public void attack(Tile tile) {
        if(!isInAttackingArea(tile)) {
            this.state = UnitState.IDLE;
            return;
        }
        this.tileToAttack = tileComponent.getMapComponent().getTileComponent(tile.coordinates.x, tile.coordinates.y);
        setState(UnitState.ATTACKING);
    }

    public void move(GameTileComponent tile) {
        if(!isInMovingArea(tile.getTile())){
            this.state = UnitState.IDLE;
            return;
        }
        this.movingPath = this.tileComponent.getTile().getUnit().move(tile.getTile());
        setState(UnitState.MOVING);
        this.tileComponent.setUnitComponent(null);
        this.tileComponent = tile;
        tile.setUnitComponent(this);
    }

    public void move(Tile tile) {
        if(!isInMovingArea(tile)){
            this.state = UnitState.IDLE;
            return;
        }
        this.movingPath = this.tileComponent.getTile().getUnit().move(tile);
        setState(UnitState.MOVING);
        this.tileComponent.setUnitComponent(null);
        this.tileComponent = tileComponent.getMapComponent().getTileComponent(tile.coordinates.x, tile.coordinates.y);;
        this.tileComponent.setUnitComponent(this);
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
                else if (right) {
                    this.idleRightAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                break;
            case MOVING:
                if(left) {
                    this.movingLeftAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                else if (right) {
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
            case ATTACKING:
                getCurrentAnimation().draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                break;
            case PREPARE_CARGO:
                if(left) {
                    this.idleLeftAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                else if (right){
                    this.idleRightAnimation.draw(this.x, this.y, this.tileComponent.getWidth(), this.tileComponent.getHeight());
                }
                graphics.setColor(new Color(0, 1, 0, 0.2f));
                for(Tile tile : cargoArea) {
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
        if(projectileComponent != null) {
            projectileComponent.render(guiContext, graphics);
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
            case ATTACKING:
                updateAttacking(gameContainer, delta);
                break;
            default:
                break;
        }
    }

    public void updateAttacking(GameContainer gameContainer, int delta) throws SlickException {
        if(this.unit.typeOfUnit.isRanged) {
            //if (this.currentAttackingTime <= this.movingTime) {

            //}
            if(this.currentAttackingTime > this.movingTime && this.currentAttackingTime <= this.attackingTime - this.movingTime && projectileComponent == null) {
                this.projectileComponent = new ProjectileComponent(this, tileToAttack);
            }
            else if(projectileComponent != null) {
                this.projectileComponent.update(gameContainer ,delta);
            }
            //else if(this.currentAttackingTime > this.attackingTime - this.movingTime) {

            //}
        }
        else {
            if (this.currentAttackingTime <= this.movingTime) {
                moveToTile(this.tileToAttack.getTile(), gameContainer, delta);
            }
            //else if(this.currentAttackingTime > this.movingTime && this.currentAttackingTime <= this.attackingTime - this.movingTime) {

            //}
            else if(this.currentAttackingTime > this.attackingTime - this.movingTime) {
                moveToTile(this.tileComponent.getTile(), gameContainer, delta);
            }
        }
        this.currentAttackingTime += delta;
        if(this.currentAttackingTime > this.attackingTime) {
            this.currentAttackingTime = 0;
            this.unit.attack(this.tileToAttack.getTile(), this.unit.typeOfUnit.isRanged);
            System.out.println("attack");
            this.state = UnitState.IDLE;
            this.projectileComponent = null;
        }//
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
            else if(this.state == UnitState.ATTACKING) {
                if(this.unit.typeOfUnit.isRanged) {
                    if (this.currentAttackingTime <= this.movingTime){
                        return idleLeftAnimation;
                    }
                    else if (this.currentAttackingTime > this.movingTime && this.currentAttackingTime <= this.attackingTime - this.movingTime) {
                        return idleLeftAnimation;
                    } else {
                        return idleLeftAnimation;
                    }
                }
                else {
                    if (this.currentAttackingTime <= this.movingTime) return movingLeftAnimation;
                    else if (this.currentAttackingTime > this.movingTime && this.currentAttackingTime <= this.attackingTime - this.movingTime) {
                        return idleLeftAnimation;
                    } else {
                        return movingLeftAnimation;
                    }
                }
            }
            else return idleLeftAnimation;
        }
        else if(right) {
            if(this.state == UnitState.MOVING) return movingRightAnimation;
            else if(this.state == UnitState.ATTACKING) {
                if(this.unit.typeOfUnit.isRanged) {
                    if (this.currentAttackingTime <= this.movingTime){
                        return idleRightAnimation;
                    }
                    else if (this.currentAttackingTime > this.movingTime && this.currentAttackingTime <= this.attackingTime - this.movingTime) {
                        return idleRightAnimation;
                    } else {
                        return idleRightAnimation;
                    }
                }
                else {
                    if (this.currentAttackingTime <= this.movingTime) return movingRightAnimation;
                    else if (this.currentAttackingTime > this.movingTime && this.currentAttackingTime <= this.attackingTime - this.movingTime) {
                        return idleRightAnimation;
                    } else {
                        return movingRightAnimation;
                    }
                }
            }
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
        if(this.state == UnitState.MOVING) return;
        if(this.state == UnitState.ATTACKING) return;
        this.state = state;
    }

    public GameTileComponent getTileComponent() {
        return tileComponent;
    }
}
