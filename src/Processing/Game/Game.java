package Processing.Game;

import Processing.Player.Player;
import Processing.Player.ResearchCell;
import Processing.TileMap.GameMap;

import java.io.Serializable;
import java.util.Random;

public class Game implements Serializable {
    static final long serialVersionUID = 4L;

    public static final Random RandomGen = new Random(System.currentTimeMillis());

    public int numberOfPlayers;
    public Player players[];
    public int currentPlayer;

    public GameMap Map;

    public void giveTurn(){
        currentPlayer = (currentPlayer+1)%numberOfPlayers;
        players[currentPlayer%numberOfPlayers].myTurn = true;
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }

    public Game(GameMap Map, int numberOfPlayers){
        this.Map = Map;
        this.numberOfPlayers = numberOfPlayers;
        ResearchCell.initResearchTree();
        this.players = new Player[this.numberOfPlayers];
        for(int i = 0; i < this.numberOfPlayers; i++){
            this.players[i] = new Player(this, "none");
        }
        this.currentPlayer = 0;
        this.Map.GenerateSettlers(this);
    }

}
