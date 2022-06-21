package graphics.panels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImagePanel extends Panel {

    private Image image;

    public ImagePanel(Image image, float x, float y, float width, float height, int parts) {
        super(x, y, width, height, parts);
        this.image = image;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        image.draw(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
