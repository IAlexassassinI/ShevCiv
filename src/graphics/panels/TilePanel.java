package graphics.panels;

import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class TilePanel extends Panel {

    private TileComponent tileComponent;

    public TilePanel(TileComponent tileComponent, float x, float y, float width, float height, int parts) {
        super(x, y, width, height, parts);
        this.tileComponent = tileComponent;
        this.tileComponent.setX(x);
        this.tileComponent.setY(y);
        this.tileComponent.setWidth(width);
        this.tileComponent.setHeight(height);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        this.tileComponent.render(container, g);
    }

    @Override
    protected void setX(float x) {
        super.setX(x);
        this.tileComponent.setX(x);
    }

    @Override
    protected void setY(float y) {
        super.setY(y);
        this.tileComponent.setY(y);
    }

    @Override
    protected void setWidth(float width) {
        super.setWidth(width);
        this.tileComponent.setWidth(width);
    }

    @Override
    protected void setHeight(float height) {
        super.setHeight(height);
        this.tileComponent.setHeight(height);
    }
}
