package graphics.tests;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class SpriteSheetTest extends BasicGame {

    private SpriteSheet spriteSheet;
    private Image subImage;
    private Image anotherImage;
    private Rectangle rectangle;
    private Point point;

    public SpriteSheetTest(String title) {
        super(title);
    }


    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        //spriteSheet = new SpriteSheet("assets/graphics/typeOfLand/Void.png", 100, 100);
        //this.subImage = spriteSheet.getSubImage(0, 0);
        //anotherImage = new Image("assets/graphics/typeOfLand/FlatLand.png");
        //rectangle = new Rectangle(0, 0, 100, 100);
        //point = new Point(0,0);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        //graphics.setAntiAlias(true);
        //graphics.setColor(Color.red);
        //this.spriteSheet.startUse();
        //for(int i = 0; i < 100; i++) {
            //for(int j = 0; j < 100; j++) {
                //this.spriteSheet.renderInUse(0, 0, 0, 0);
                //this.spriteSheet.renderInUse(100, 0, 0, 0);
                //this.spriteSheet.renderInUse(200, 0, 0, 0);
                //this.spriteSheet.getSprite(0,0).drawEmbedded(i*2, j*2, 2, 2);

                //this.spriteSheet.getSprite(100,0).drawEmbedded(0, 0, 100, 100);
                //if(i*100 < 1280 && j *100 < 720)
                //this.subImage.drawEmbedded(i*4, j*4, 4, 4);
                //graphics.draw(rectangle);
                //graphics.fillRect(i * 1, j *1, 1, 1);
                //graphics.fillRect(i*100,j*100,100,100);
                //graphics.draw(this.point);
                //graphics.fill(new Point(i,j));
                //graphics.drawLine(i, j, i, j);
            //}
        //}
        //this.spriteSheet.endUse();
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new SpriteSheetTest("SpriteSheetTest"));
        app.setDisplayMode(1280, 720, false);
        app.start();
    }
}
