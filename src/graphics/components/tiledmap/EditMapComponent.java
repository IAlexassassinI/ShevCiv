package graphics.components.tiledmap;

import Processing.TileMap.GameMap;
import org.newdawn.slick.gui.GUIContext;

public class EditMapComponent extends MapComponent {

    private EditMode editMode;

    public EditMapComponent(GUIContext container, GameMap map, float x, float y) {
        super(container, map, x, y);
    }

    public void setEditMode(EditMode editMode) {
        this.editMode = editMode;
    }
}
