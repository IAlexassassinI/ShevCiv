package Processing.Player;

import Processing.TileMap.GameMap;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Utilits.Wrapers.TwoTTT;

import java.io.Serializable;
import java.util.LinkedList;

public class Player implements Serializable {
    static final long serialVersionUID = 12L;

    public Player(GameMap Map){
        this.VisionMap = new int[Map.getHeight()][Map.getWidth()];
        this.OpenFOWMap = new boolean[Map.getHeight()][Map.getWidth()];
    }

    public double totalMoney;

    public int VisionMap[][];
    public boolean OpenFOWMap[][];

    public LinkedList<TwoTTT<TypeOfBuilding, Boolean>> availableUpgradesOfTile = new LinkedList<>();

    public int colonizationSquare = 4;



}
