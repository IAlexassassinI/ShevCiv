package Processing.Game;

import Processing.Player.Player;
import Processing.Player.ResearchCell;
import Processing.TileMap.GameMap;
import Processing.Units.UnitPattern;
import graphics.states.GameState;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Game implements Serializable {

    private GameState gameState;

    static final long serialVersionUID = 4L;

    public static final Random RandomGen = new Random(System.currentTimeMillis());

    public int numberOfPlayers;
    public int numberOfDefeatedPlayers;
    public Player players[];
    public int currentPlayer = 0;
    public int year = 0;

    public boolean win = false;

    public GameMap Map;

    HashMap<Player, Barbarian_AI> AI_ID = new HashMap<>();

    public void giveTurn(){
        if(win){
            return;
        }
        currentPlayer++;
        if(currentPlayer == numberOfPlayers){
            year++;
            currentPlayer = 0;
        }
        players[currentPlayer].myTurn = true;
        if(numberOfPlayers - numberOfDefeatedPlayers == 1){
            for(int i = 0; i < players.length; i++){
                if(!players[i].isDefeated){
                    doWin();
                    break;
                }
            }
        }
        if(players[currentPlayer].isDefeated){
            if(numberOfPlayers != numberOfDefeatedPlayers){
                giveTurn();
            }
            else{
                doWin();
            }
        }
        else if(players[currentPlayer].isBarbarianAI){
            if(!win){
                AI_ID.get(players[currentPlayer]).doTurn();
            }
        }


    }

    public void doWin(){
        win = true;
        System.out.println("YouWin");
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }

    public Game(GameMap Map, int numberOfHumans, int numberOfAI, int spawnRateOfAI, int levelOfAI){
        this.Map = Map;
        this.numberOfPlayers = numberOfHumans + numberOfAI;
        ResearchCell.initResearchTree();
        this.players = new Player[this.numberOfPlayers];
        LinkedList<String> AvailableRace = new LinkedList<>();
        AvailableRace.add("Human");
        AvailableRace.add("Elven");
        AvailableRace.add("Demon");
        for(int i = 0; i < this.numberOfPlayers; i++){
            //this.players[i] = new Player(this, "none");
            if(AvailableRace.size() != 0){
                int randomIndex = RandomGen.nextInt(AvailableRace.size());
                this.players[i] = new Player(this, AvailableRace.get(randomIndex));
                AvailableRace.remove(randomIndex);
            }
            else {
                this.players[i] = new Player(this, "none");
            }
        }
        for(int i = 0; i < numberOfAI; i++){
            Player toProc = this.players[numberOfHumans+i];
            toProc.isBarbarianAI = true;
            toProc.race = "Ork";
            toProc.mySettlerType = UnitPattern.AllUnitPattern.get(UnitPattern.OrkSettler.NameOfUnit);
            AI_ID.put(toProc, new Barbarian_AI(toProc, spawnRateOfAI, levelOfAI));
        }
        this.currentPlayer = 0;
        this.Map.GenerateSettlers(this);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
