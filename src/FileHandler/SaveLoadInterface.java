package FileHandler;

import java.io.*;

public class SaveLoadInterface {

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

}
