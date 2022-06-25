package Processing.Game;

import Processing.Player.Player;
import Processing.TileMap.GameMap;

import java.util.Random;

public class Game {
    static final long serialVersionUID = 4L;

    public static final Random RandomGen = new Random(System.currentTimeMillis());

    public int numberOfPlayers;
    public Player players[];
    public int currentPlayer;

    public GameMap Map;

    public void giveTurn(){
        currentPlayer++;
        players[currentPlayer%numberOfPlayers].myTurn = true;
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }

    Game(GameMap Map, int numberOfPlayers){
        this.Map = Map;
        this.numberOfPlayers = numberOfPlayers;
        this.players = new Player[this.numberOfPlayers];
        this.currentPlayer = 0;
        this.Map.GenerateSettlers(this);
    }

}
