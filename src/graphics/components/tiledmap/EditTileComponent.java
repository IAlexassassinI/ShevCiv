package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

public class EditTileComponent extends TileComponent {

    private EditMode editMode;

    private boolean riverUp;
    private boolean riverDown;
    private boolean riverRight;
    private boolean riverLeft;


    public EditTileComponent(GUIContext container, Tile tile, float x, float y) {
        super(container, tile, x, y);
        this.editMode = EditMode.NONE;
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        super.render(guiContext, graphics);
        renderFilter(guiContext, graphics);
    }

    public void renderFilter(GUIContext guiContext, Graphics graphics) throws SlickException {
        if(this.mouseClicked && this.editMode != EditMode.NONE) {
            graphics.setColor(this.mouseDownColor);
            switch(this.editMode) {
                case EDIT_TYPE_OF_LAND:
                    graphics.fillRect(this.x, this.y, this.width, this.height);
                    break;
                case EDIT_RESOURCE:
                    graphics.fillRect(this.x, this.y, this.width, this.height);
                    break;
                case EDIT_TYPE_OF_FLORA:
                    graphics.fillRect(this.x, this.y, this.width, this.height);
                    break;
                case EDIT_RIVER:
                    graphics.fillRect(this.x, this.y, this.width, this.height);
                    break;
                default :
                    break;
            }
        }
        else if(this.mouseOver && this.editMode != EditMode.NONE) {
            graphics.setColor(this.mouseOverColor);
            switch(this.editMode) {
                case EDIT_TYPE_OF_LAND:
                    graphics.fillRect(this.x, this.y, this.width, this.height);
                    break;
                case EDIT_RESOURCE:
                    graphics.fillRect(this.x, this.y, this.width, this.height);
                    break;
                case EDIT_TYPE_OF_FLORA:
                    graphics.fillRect(this.x, this.y, this.width, this.height);
                    break;
                case EDIT_RIVER:
                    graphics.fillRect(this.x, this.y, this.width, this.height);
                    break;
                default :
                    break;
            }
        }
    }

    /*@Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if(button == Input.MOUSE_LEFT_BUTTON && contains(x, y) && this.editMode != EditMode.NONE) {
            switch(this.editMode) {
                case EDIT_TYPE_OF_LAND:
                    this.mouseClicked = true;
                    break;
                case EDIT_RESOURCE:
                    this.mouseClicked = true;
                    break;
                case EDIT_TYPE_OF_FLORA:
                    this.mouseClicked = true;
                    break;
                case EDIT_RIVER:
                    this.mouseClicked = true;
                    break;
                default :
                    break;
            }
            notifyListeners();
        }
        else {
            this.mouseClicked = false;
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        this.mouseClicked = false;
        if(this.contains(newx, newy) && this.editMode != EditMode.NONE) {
            switch(this.editMode) {
                case EDIT_TYPE_OF_LAND:
                    this.mouseOver = true;
                    break;
                case EDIT_RESOURCE:
                    this.mouseOver = true;
                    break;
                case EDIT_TYPE_OF_FLORA:
                    this.mouseOver = true;
                    break;
                case EDIT_RIVER:
                    this.mouseOver = true;
                    break;
                default :
                    break;
            }
        }
        else {
            this.mouseOver = false;
        }
    }*/

    public void mouseClickedSignalise(int button, int x, int y, int clickCount) {
        if(button == Input.MOUSE_LEFT_BUTTON && contains(x, y) && this.editMode != EditMode.NONE) {
            switch(this.editMode) {
                case EDIT_TYPE_OF_LAND:
                    this.mouseClicked = true;
                    break;
                case EDIT_RESOURCE:
                    this.mouseClicked = true;
                    break;
                case EDIT_TYPE_OF_FLORA:
                    this.mouseClicked = true;
                    break;
                case EDIT_RIVER:
                    this.mouseClicked = true;
                    break;
                default :
                    break;
            }
            notifyListeners();
        }
        else {
            this.mouseClicked = false;
        }
    }

    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        this.mouseClicked = false;
        if(this.contains(newx, newy) && this.editMode != EditMode.NONE) {
            switch(this.editMode) {
                case EDIT_TYPE_OF_LAND:
                    this.mouseOver = true;
                    break;
                case EDIT_RESOURCE:
                    this.mouseOver = true;
                    break;
                case EDIT_TYPE_OF_FLORA:
                    this.mouseOver = true;
                    break;
                case EDIT_RIVER:
                    this.mouseOver = true;
                    break;
                default :
                    break;
            }
        }
        else {
            this.mouseOver = false;
        }
    }

    public void setEditMode(EditMode editMode) {
        this.editMode = editMode;
    }

}
