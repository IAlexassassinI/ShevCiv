package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import graphics.loads.Sounds;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.openal.SoundStore;

public class ProjectileComponent {

    private float x;
    private float y;

    private static int movingTime = 1000;
    private static int explosionTime = 1000;
    private int currentTime = 0;

    private boolean left = true;
    private boolean right = false;
    private boolean isSound = false;

    private Image projectileRight;
    private Image projectileLeft;
    private Animation explosionAnimation;

    private UnitComponent unitComponent;
    private GameTileComponent destination;

    public ProjectileComponent(UnitComponent unitComponent, GameTileComponent destination) {
        this.x = unitComponent.getX();
        this.y = unitComponent.getY();
        this.destination = destination;
        this.unitComponent = unitComponent;
        try {
            this.projectileRight = new Image("assets/graphics/units/projectiles/" + unitComponent.getUnit().typeOfUnit.projectile.name + "Right" + ".png");
            this.projectileLeft = new Image("assets/graphics/units/projectiles/" + unitComponent.getUnit().typeOfUnit.projectile.name + "Left" + ".png");

            if(unitComponent.getUnit().typeOfUnit.projectile.name.equals("BigFireball")) {
                SpriteSheet animationSpriteSheet = new SpriteSheet("assets/graphics/units/projectiles/COTTON.png", TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
                explosionAnimation = new Animation(animationSpriteSheet, 0, 0, animationSpriteSheet.getHorizontalCount()-1, 0, true, 100, true);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
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
        else if(this.currentTime > this.movingTime && this.currentTime <= this.explosionTime + this.movingTime && this.explosionAnimation != null) {
            if(!isSound) {
                //SoundStore.get().stopSoundEffect(0);
                Sounds.attackSound.stop();
                Sounds.explosionSound.play();
                isSound = true;
            }
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
        float dwidth = (this.unitComponent.getTileComponent().getWidth() * Math.abs(this.unitComponent.getTileComponent().getTile().coordinates.x -this.destination.getTile().coordinates.x)) / (float)this.movingTime * (float)delta;
        float dheight = (this.unitComponent.getTileComponent().getHeight() * Math.abs(this.unitComponent.getTileComponent().getTile().coordinates.y -this.destination.getTile().coordinates.y)) / (float)this.movingTime * (float)delta;
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