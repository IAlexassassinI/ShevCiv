package Processing.FileHandler;

import Processing.Game.Game;
import Processing.TileMap.GameMap;
import Processing.Utilits.GeneralUtility;

import java.io.*;

public class SaveLoadInterface {
    static final long serialVersionUID = 3L;

    public static void serializeObjectToFile(Object object, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(object);
            outputStream.close();
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public static Object deserializeFromFile(String filePath) {
        Object savedObject = null;
        //GeneralUtility.initAllProcessingNeeds();

        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            savedObject = inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }

        return savedObject;
    }

    public static void SaveGameMapToFile(GameMap gameMap, String filePath){
        gameMap.GU.saveStaticParams();
        serializeObjectToFile(gameMap, filePath);
    }

    public static void SaveGameToFile(Game game, String filePath){
        game.Map.GU.saveStaticParams();
        serializeObjectToFile(game, filePath);
    }

    public static GameMap LoadGameMapFromFile(String filePath){
       GameMap GM = ((GameMap) deserializeFromFile(filePath));
        GeneralUtility.initAllProcessingNeeds();
        //GM.GU.loadStaticParams();
       return GM;
    }

    public static Game LoadGameFromFile(String filePath){
        Game GG = ((Game) deserializeFromFile(filePath));
        GeneralUtility.initAllProcessingNeeds();
        //GG.Map.GU.loadStaticParams();
        return GG;
    }

}
