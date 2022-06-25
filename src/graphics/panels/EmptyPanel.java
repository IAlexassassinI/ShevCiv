package graphics.panels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EmptyPanel extends Panel {

    public EmptyPanel(float x, float y, float width, float height, int parts) {
        super(x, y, width, height, parts);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

    }
}
