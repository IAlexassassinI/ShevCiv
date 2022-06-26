package graphics.tests;

import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.*;

public class AnimationTest extends BasicGame {

    Animation idleAnimation;

    public AnimationTest(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        SpriteSheet idleSpriteSheet = new SpriteSheet("assets/graphics/units/Settler.png", TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
        this.idleAnimation = new Animation(idleSpriteSheet, 0, 0, idleSpriteSheet.getHorizontalCount()-1, 0, true, 250, true);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.idleAnimation.draw(0,0,500,500);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new AnimationTest(""));
        app.setDisplayMode(1280, 720, false);
        app.start();
    }
}
