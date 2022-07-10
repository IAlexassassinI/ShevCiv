package graphics.components.camera;

import graphics.components.tiledmap.MapComponent;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class Camera extends AbstractComponent {

    private float x;
    private float y;

    private float width;
    private float height;

    private MapComponent map;

    private float maxTileSize = 600;

    private int mouseX;
    private int mouseY;

    private boolean locked;

    private boolean scrollLocked = false;

    public Camera(GUIContext container, float x, float y, float width, float height, MapComponent map) {
        super(container);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.map.setCamera(this);
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        graphics.setColor(Color.red);
        graphics.fillRect(this.x-1,this.y-1,this.width+2,this.height+2);
        graphics.setColor(Color.black);
        graphics.fillRect(this.x,this.y,this.width,this.height);
        graphics.setClip((int) this.x, (int) this.y, (int) this.width, (int) this.height);
        map.render(guiContext, graphics);
        graphics.clearClip();
        graphics.resetTransform();
    }

    public void mouseDraggedSignalise(int oldx, int oldy, int newx, int newy) {
        if(!contains(oldx, oldy)) return;
        translateMap(newx - oldx, newy - oldy);
    }

    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        this.mouseX = newx;
        this.mouseY = newy;
    }

    public void mouseWheelMovedSignalise(int change) {
        if(scrollLocked)return;
        if(!contains(this.mouseX, this.mouseY)) return;
        scaleMap((1000f + change) / 1000f, this.mouseX, this.mouseY);
    }

    public void translateMap(float dx, float dy) {
        if(this.map.getX() + dx <= this.x && this.map.getX() + this.map.getWidth() + dx >= this.x + this.width) this.map.translate(dx, 0);
        if(this.map.getY() + dy <= this.y && this.map.getY() + this.map.getHeight() + dy >= this.y + this.height) this.map.translate(0, dy);
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        this.map.translate(dx, dy);
    }

    public void scaleMap(float k, float px, float py) {
        if(k * this.map.getTileSize() > this.maxTileSize) return;
        if(this.map.getWidth() * k < this.width) {
            k = this.width / this.map.getWidth();
        }
        if(this.map.getHeight() * k < this.height) {
            k = this.height / this.map.getHeight();
        }
        this.map.scale(k, px, py);
        if(this.map.getX() > this.x) {
            this.map.translate(this.x - this.map.getX(), 0);
        }
        else if(this.map.getX() + this.map.getWidth() < this.x + this.width) {
            this.map.translate((this.x + this.width) - (this.map.getX() + this.map.getWidth()), 0);
        }
        if(this.map.getY() > this.y) {
            this.map.translate(0, this.y - this.map.getY());
        }
        else if(this.map.getY() + this.map.getHeight() < this.y + this.height) {
            this.map.translate(0, (this.y + this.height) - (this.map.getY() + this.map.getHeight()));
        }
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }

    public boolean containsArea(float x, float y, float width, float height) {
        return contains(x, y) || contains(x, y + height - 1) || contains(x + width - 1, y) || contains(x + width - 1, y + height - 1);
    }

    public boolean containsTileComponent(TileComponent tileComponent) {
        return containsArea(tileComponent.getX(), tileComponent.getY(), tileComponent.getWidth(), tileComponent.getHeight());
    }

    @Override
    public void setLocation(int i, int i1) {

    }

    @Override
    public int getX() {
        return (int) this.x;
    }

    @Override
    public int getY() {
        return (int) this.y;
    }

    @Override
    public int getWidth() {
        return (int) this.width;
    }

    @Override
    public int getHeight() {
        return (int) this.height;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isScrollLocked() {
        return scrollLocked;
    }

    public void setScrollLocked(boolean scrollLocked) {
        this.scrollLocked = scrollLocked;
    }

}
