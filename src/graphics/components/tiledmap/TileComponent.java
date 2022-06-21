package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import graphics.loads.Images;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class TileComponent extends AbstractComponent {

    public static final int STANDARD_WIDTH = 100;
    public static final int STANDARD_HEIGHT = 100;

    private Tile tile;

    protected Color color;
    protected Color backgroundColor;
    protected Color mouseOverColor;
    protected Color currentColor;

    private float imageX;
    private float imageY;

    private float imageWidth;
    private float imageHeight;

    private float x;
    private float y;

    private float width;
    private float height;

    private SelectStyle selectStyle = SelectStyle.BRIGHTER;

    private boolean selected;
    private boolean locked;

    private boolean mouseOver;
    private boolean mousePressed;
    private boolean mouseReleased;

    public TileComponent(GUIContext container, Tile tile, float x, float y) {
        super(container);
        this.x = x;
        this.y = y;
        this.tile = tile;
        init();
    }

    private void init() {
        this.imageX = this.x;
        this.imageY = this.y;
        this.imageWidth = STANDARD_WIDTH;
        this.imageHeight = STANDARD_HEIGHT;
        this.width = STANDARD_WIDTH;
        this.height = STANDARD_HEIGHT;
        this.selected = false;
        this.color = Color.white;
        this.backgroundColor = Color.black;
        this.currentColor = color;
        this.mouseOverColor = new Color(1,1,1,0.8f);
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        graphics.setColor(this.backgroundColor);
        //if(this.selectStyle == SelectStyle.BRIGHTER) graphics.fillRect(this.imageX, this.imageY, this.imageWidth, this.imageHeight);
        if(this.selectStyle == SelectStyle.BRIGHTER) graphics.fillRect(this.x, this.y, this.width, this.height);
        else if(selectStyle == SelectStyle.WHITE_BORDER) graphics.fillRoundRect(this.x - this.width * 0.05f, this.y - this.height * 0.05f, this.width * 1.1f, this.height * 1.1f, (int) (this.width * 0.05f));
        //Image typeOfLand = new Image("assets/graphics/typeOfLand/" + this.tile.typeOfLand.elementName + ".png");
        //typeOfLand.draw(this.x, this.y, this.width, this.height, this.currentColor);
        //Renderer.get().glBegin(SGL.GL_QUADS);
        //Images.getTypeOfLandImage(this.tile.typeOfLand.elementName).drawEmbedded(this.x, this.y, this.width, this.height);
        //Renderer.get().glEnd();
        Images.getTypeOfLandImage(this.tile.typeOfLand.elementName).draw(this.imageX, this.imageY, this.currentColor);
        //this.tile.getTypeOfLand().image.draw(this.x, this.y, this.width, this.height, this.currentColor);
    }

    public void scale(float sx, float sy) {
        this.width = this.width * sx;
        this.height = this.height * sy;
        //System.out.println(this.x + " " + this.y + " " + this.width + " " + this.height);
    }

    public void scale(float sx, float sy, float px, float py) {
        this.scale(sx, sy);
        this.x = px + (this.x - px) * sx;
        this.y = py + (this.y - py) * sy;
    }

    public void scaleImage(float sx, float sy) {
        this.imageWidth = this.imageWidth * sx;
        this.imageHeight = this.imageHeight * sy;
        //System.out.println(this.x + " " + this.y + " " + this.width + " " + this.height);
    }

    public void scaleImage(float sx, float sy, float px, float py) {
        this.scaleImage(sx, sy);
        this.imageX = px + (this.imageX - px) * sx;
        this.imageY = py + (this.imageY - py) * sy;
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public void translateImage(float dx, float dy) {
        this.imageX += dx;
        this.imageY += dy;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if(button != Input.MOUSE_LEFT_BUTTON) return;
        if(clickCount != 1) return;
        if(x < this.x || x > this.x + this.width) return;
        if(y < this.y || y > this.y + this.height) return;
        setSelected(!this.selected);
        if(!this.selected) {
            this.currentColor = this.mouseOverColor;
        }
        this.notifyListeners();
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if(this.selected)return;
        if(this.contains(newx, newy)) {
            this.currentColor = this.mouseOverColor;
        }
        else this.currentColor = this.color;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(this.selected) {
            if(this.selectStyle == SelectStyle.BRIGHTER) {
                this.backgroundColor = Color.white;
                this.currentColor = this.mouseOverColor;
            }
            else if(this.selectStyle == SelectStyle.WHITE_BORDER) {
                this.backgroundColor = Color.white;
                this.currentColor = this.color;
            }
        }
        else {
            if(this.selectStyle == SelectStyle.BRIGHTER) {
                this.backgroundColor = Color.black;
                this.currentColor = this.color;
            }
            else if(this.selectStyle == SelectStyle.WHITE_BORDER) {
                this.backgroundColor = Color.black;
                this.currentColor = this.color;
            }
        }
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }

    public SelectStyle getSelectStyle() {
        return selectStyle;
    }

    public void setSelectStyle(SelectStyle selectStyle) {
        this.selectStyle = selectStyle;
    }

    public Tile getTile() {
        return tile;
    }

    @Override
    public void setLocation(int i, int i1) {

    }

    public float getImageX() {
        return imageX;
    }

    public void setImageX(float imageX) {
        this.imageX = imageX;
    }

    public float getImageY() {
        return imageY;
    }

    public void setImageY(float imageY) {
        this.imageY = imageY;
    }

    public float getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(float imageWidth) {
        this.imageWidth = imageWidth;
    }

    public float getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(float imageHeight) {
        this.imageHeight = imageHeight;
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
