package com.nhom_4.arkanoid.util;

import com.nhom_4.arkanoid.core.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveLoadManager {
    private static final String SaveFileName = "arkanoidSave.dat";

    public static void saveGame(Game game) {
        try (FileOutputStream fileOut = new FileOutputStream(SaveFileName); 
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(game);

            System.out.println("Game đã lưu vào" + SaveFileName);
        } catch (IOException e) {
            System.err.println("Lỗi lưu game");
            e.printStackTrace();
        }
    }

    public static Game loadGame() {
        File saveFile =  new File(SaveFileName);

        if (!saveFile.exists()) {
            System.out.println("Không tìm thấy file");
            return null;
        }

        try (FileInputStream fileIn = new FileInputStream(saveFile); 
        ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            Game loadedGame = (Game) objectIn.readObject();
            return loadedGame;
        } catch (FileNotFoundException e) {
            System.err.println("File không tồn tại");
            return null;
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi I/O khi load game hoặc lỗi ClassNotFOund");
            e.printStackTrace();
            return null;
        }
    }
}
