package graphics.panels;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.MouseOverArea;

public class MouseOverAreaPanel extends Panel {

    private MouseOverArea mouseOverArea;
    private Rectangle rectangle;

    public MouseOverAreaPanel(MouseOverArea mouseOverArea, Image image, Rectangle rectangle, float x, float y, float width, float height, int parts) {
        super(x, y, width, height, parts);
        this.mouseOverArea = mouseOverArea;
        this.rectangle = rectangle;
        rectangle.setSize(width, height);
        rectangle.setLocation(x, y);
        mouseOverArea.setNormalImage(image.getScaledCopy((int) width, (int) height));
        mouseOverArea.setMouseDownImage(image.getScaledCopy(0.9f));
        mouseOverArea.setMouseOverImage(image.getScaledCopy((int) width, (int) height));
        mouseOverArea.setMouseOverColor(new Color(1,1,1,0.75f));
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

    }
}
