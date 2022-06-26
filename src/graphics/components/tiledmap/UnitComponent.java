package graphics.components.tiledmap;

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

    public void prepareToMove() {

    }



    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        switch(this.state) {
            case IDLE:
                this.idleAnimation.draw(this.tileComponent.getX(), this.tileComponent.getY(), this.tileComponent.getWidth(), this.tileComponent.getHeight());
                break;
            case MOVING:
                break;
            case PREPARE_TO_MOVE:
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
