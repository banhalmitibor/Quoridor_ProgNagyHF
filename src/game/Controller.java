package game;

import grid.GameData;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import player.Piece;

public class Controller {
    
    private static GameData data;

    public static void setData(GameData gd){
        data = gd;
    }

    public static void reset(int n){
        data.reset(n);
    }

    public static void addPropertyChangeListener(PropertyChangeListener l){
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

}
