package game;

import grid.GameData;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import player.Piece;

public class Controller {
    
    private static GameData data;

    private static final ArrayList<PropertyChangeListener> listeners = new ArrayList<>();

    public static void setData(GameData gd){
        data = gd;
    }

    public static void reset(int n){
        data.reset(n);
    }

    public static void resetAi(){
        data.resetAi();
    }

    public static void addPropertyChangeListener(PropertyChangeListener l){
        listeners.add(l);
        data.addPropertyChangeListener(l);
    }

    public static ArrayList<Piece> getPlayers(){
        return data.players;
    }

    public static int getCurPlayer(){
        return data.curPlayer;
    }

    public static ArrayList<ArrayList<Boolean>> getVertcialWalls(){
        return data.verticalWalls;
    }

    public static ArrayList<ArrayList<Boolean>> getHorizontalWalls(){
        return data.horizontalWalls;
    }

    public static void movePlayer(int x, int y){
        data.movePlayer(x, y);
    }

    public static int playerIsOnTile(int x, int y){
        return data.playerIsOnTile(x, y);
    }

    public static void placeWall(int posx, int posy, boolean vertical){
        data.placeWall(posx, posy, vertical);
    }

    public static void saveGame(int slot) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save" + slot))) {
            out.writeObject(data); 
        }
    }

    public static void loadGame(int slot) throws Exception {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("save" + slot))) {
            Object obj = in.readObject();
            data = (GameData) obj;
        }
        for(PropertyChangeListener l : listeners){
            data.addPropertyChangeListener(l);
            data.refresh();
        }
    }

}
