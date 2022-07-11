package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import graphics.components.camera.Camera;
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

    private Camera camera;

    protected Color backgroundColor;
    protected Color normalColor;
    protected Color mouseDownColor;
    protected Color mouseOverColor;
    protected Color currentColor;

    private float imageX;
    private float imageY;

    private float imageWidth;
    private float imageHeight;

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    private SelectStyle selectStyle = SelectStyle.NONE;

    private boolean selected;
    private boolean locked;

    protected boolean mouseOver;
    protected boolean mouseClicked;
    protected boolean mousePressed;
    protected boolean mouseReleased;

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
        this.mouseOverColor = new Color(0,0,0,0.2f);
        this.mouseDownColor = new Color(1,1,1,0.2f);
        this.normalColor = new Color(0,0,0,0);
        this.currentColor = normalColor;
        this.backgroundColor = Color.white;
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        Images.typesOfLandSpriteSheet.getSprite(this.tile.typeOfLand.Type,0).draw(this.x, this.y, this.width, this.height);
        Images.typesOfFloraSpriteSheet.getSprite(this.tile.typeOfFlora.Type,0).draw(this.x, this.y, this.width, this.height);
        Images.resourcesSpriteSheet.getSprite(this.tile.resource.Type,0).draw(this.x, this.y, this.width, this.height);
    }

    public void renderEmbeddedTypeOfLand(GUIContext guiContext, Graphics graphics) throws SlickException {
        Images.typesOfLandSpriteSheet.getSprite(this.tile.typeOfLand.Type,0).drawEmbedded(this.x, this.y, this.width, this.height);
    }

    public void renderEmbeddedTypeOfFlora(GUIContext guiContext, Graphics graphics) throws SlickException {
        Images.typesOfFloraSpriteSheet.getSprite(this.tile.typeOfFlora.Type,0).drawEmbedded(this.x, this.y, this.width, this.height);
    }

    public void renderEmbeddedResource(GUIContext guiContext, Graphics graphics) throws SlickException {
        Images.resourcesSpriteSheet.getSprite(this.tile.resource.Type,0).drawEmbedded(this.x, this.y, this.width, this.height);
    }

    public void scale(float sx, float sy) {
        this.width = this.width * sx;
        this.height = this.height * sy;
    }

    public void scale(float sx, float sy, float px, float py) {
        this.scale(sx, sy);
        this.x = px + (this.x - px) * sx;
        this.y = py + (this.y - py) * sy;
    }

    public void scaleImage(float sx, float sy) {
        this.imageWidth = this.imageWidth * sx;
        this.imageHeight = this.imageHeight * sy;
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

    public void mouseClickedSignalise(int button, int x, int y, int clickCount) {
        if(clickCount != 1) return;
        if(x < this.x || x > this.x + this.width) return;
        if(y < this.y || y > this.y + this.height) return;
        setSelected(!this.selected);
        if(!this.selected) {
            this.currentColor = this.mouseOverColor;
        }
        this.notifyListeners();
    }

    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {

    }

    public void setSelected(boolean selected) {

    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public boolean cameraContains(float x, float y) {
        if(this.camera != null) {
            return this.camera.contains(x, y);
        }
        return true;
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height && cameraContains(x, y);
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
