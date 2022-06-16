package Processing.Player;

import Processing.TileMap.GameMap;

import java.io.Serializable;

public class Player implements Serializable {
    static final long serialVersionUID = 12L;

    public Player(GameMap Map){
        this.VisionMap = new int[Map.getHeight()][Map.getWidth()];
        this.OpenFOWMap = new boolean[Map.getHeight()][Map.getWidth()];
    }

    public double totalMoney;

    public int VisionMap[][];
    public boolean OpenFOWMap[][];



}
