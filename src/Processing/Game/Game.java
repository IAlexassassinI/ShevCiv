package Processing.Game;

import Processing.Player.Player;
import Processing.Player.ResearchCell;
import Processing.TileMap.GameMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public class Game implements Serializable {
    static final long serialVersionUID = 4L;

    public static final Random RandomGen = new Random(System.currentTimeMillis());

    public int numberOfPlayers;
    public Player players[];
    public int currentPlayer;
    public int year = 0;

    public GameMap Map;

    HashMap<Player, Barbarian_AI> AI_ID = new HashMap<>();

    public void giveTurn(){
        currentPlayer++;
        if(currentPlayer == numberOfPlayers){
            year++;
            currentPlayer = (currentPlayer)%numberOfPlayers;
        }
        players[currentPlayer%numberOfPlayers].myTurn = true;
        if(players[currentPlayer].isBarbarianAI){
            AI_ID.get(players[currentPlayer]).doTurn();
            giveTurn();
        }
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }

    public Game(GameMap Map, int totalNumberOfPlayers, int numberOfAI, int spawnRateOfAI, int levelOfAI){
        this.Map = Map;
        this.numberOfPlayers = totalNumberOfPlayers;
        ResearchCell.initResearchTree();
        this.players = new Player[this.numberOfPlayers];
        for(int i = 0; i < this.numberOfPlayers; i++){
            this.players[i] = new Player(this, "none");
        }
        for(int i = 0; i < numberOfAI; i++){
            Player toProc = this.players[numberOfPlayers-numberOfAI+i];
            toProc.isBarbarianAI = true;
            toProc.race = "Orc";
            AI_ID.put(toProc, new Barbarian_AI(toProc, spawnRateOfAI, levelOfAI));
        }
        this.currentPlayer = 0;
        this.Map.GenerateSettlers(this);
    }

}
