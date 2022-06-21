package graphics.components.tiledmap;

import Processing.TileMap.Tile;
import org.newdawn.slick.Graphics;
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

    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if(this.contains(newx, newy)) {
            switch(this.editMode) {
                case EDIT_TYPE_OF_LAND:
                    break;
                case EDIT_RESOURCE:
                    break;
                case EDIT_TYPE_OF_FLORA:
                    break;
                case EDIT_RIVER:
                    //if()
                    break;
                default :
                    break;
            }
        }
    }

    public void setEditMode(EditMode editMode) {
        this.editMode = editMode;
    }

}
