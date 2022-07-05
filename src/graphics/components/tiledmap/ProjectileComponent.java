package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;

public class ProjectileComponent {

    private float x;
    private float y;

    private static int movingTime = 1000;
    private static int explosionTime = 1000;
    private int currentTime = 0;

    private boolean left = true;
    private boolean right = false;

    private Image projectileRight;
    private Image projectileLeft;
    private Animation explosionAnimation;

    private UnitComponent unitComponent;
    private GameTileComponent destination;

    public ProjectileComponent(UnitComponent unitComponent, GameTileComponent destination) {
        this.x = unitComponent.getX();
        this.y = unitComponent.getY();
        this.destination = destination;
    }

    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        if(this.currentTime <= this.movingTime) {
            if(left) {
                this.projectileLeft.draw(this.x, this.y, this.unitComponent.getTileComponent().getWidth(), this.unitComponent.getTileComponent().getHeight());
            }
            else if(right) {
                this.projectileRight.draw(this.x, this.y, this.unitComponent.getTileComponent().getWidth(), this.unitComponent.getTileComponent().getHeight());
            }
        }
        else if(this.currentTime > this.movingTime && this.currentTime <= this.explosionTime) {
            this.explosionAnimation.draw(this.x, this.y, this.unitComponent.getTileComponent().getWidth(), this.unitComponent.getTileComponent().getHeight());
        }
    }

    public void update(GameContainer gameContainer, int delta) throws SlickException{
        if(this.currentTime <= this.movingTime) {
            moveToDestination(gameContainer, delta);
            this.currentTime += delta;
        }
        else {
            this.currentTime += delta;
        }
    }

    private boolean moveToDestination(GameContainer gameContainer, int delta) {
        //GameTileComponent destination = this.unitComponent.getTileComponent().getMapComponent().getTileComponent(tile.coordinates.x, tile.coordinates.y);
        if(destination.getX() == this.x && destination.getY() == this.y)return false;
        float dwidth = this.unitComponent.getTileComponent().getWidth() / (float)this.movingTime * (float)delta;
        float dheight = this.unitComponent.getTileComponent().getHeight() / (float)this.movingTime * (float)delta;
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

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
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
}