package game;

import grid.GameData;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import player.Piece;

/**
 * Controller class that acts as an intermediary between the game data and the UI.
 * Provides static methods to access and modify game state, manage property change listeners,
 * and handle game saving/loading functionality.
 * 
 * @author banhalmitibor
 * @version 1.0
 */
public class Controller {
    
    /** The game data instance holding all game state. */
    private static GameData data;

    /** List of property change listeners for UI updates. */
    private static final ArrayList<PropertyChangeListener> listeners = new ArrayList<>();

    /**
     * Sets the game data instance to be used by the controller.
     * 
     * @param gd the GameData instance to set
     */
    public static void setData(GameData gd){
        data = gd;
    }

    /**
     * Resets the game with the specified number of players.
     * 
     * @param n the number of players (2 or 4)
     */
    public static void reset(int n){
        data.reset(n);
    }

    /**
     * Resets the game with two players, with the second player being an ai player
     */
    public static void resetAi(){
        data.resetAi();
    }

    /**
     * Adds a property change listener to receive game state updates.
     * The listener is stored locally and also registered with the game data.
     * 
     * @param l the PropertyChangeListener to add
     */
    public static void addPropertyChangeListener(PropertyChangeListener l){
        listeners.add(l);
        data.addPropertyChangeListener(l);
    }

    /**
     * Gets the list of all players in the game.
     * 
     * @return ArrayList of Piece objects representing all players
     */
    public static ArrayList<Piece> getPlayers(){
        return data.players;
    }

    /**
     * Gets the index of the current player whose turn it is.
     * 
     * @return the index of the current player (0-based)
     */
    public static int getCurPlayer(){
        return data.curPlayer;
    }

    /**
     * Gets the 2D array representing vertical wall placements.
     * 
     * @return 2D ArrayList of Boolean values indicating vertical wall positions
     */
    public static ArrayList<ArrayList<Boolean>> getVertcialWalls(){
        return data.verticalWalls;
    }

    /**
     * Gets the 2D array representing horizontal wall placements.
     * 
     * @return 2D ArrayList of Boolean values indicating horizontal wall positions
     */
    public static ArrayList<ArrayList<Boolean>> getHorizontalWalls(){
        return data.horizontalWalls;
    }

    /**
     * Attempts to move the current player to the specified position.
     * 
     * @param x the target x-coordinate
     * @param y the target y-coordinate
     */
    public static void movePlayer(int x, int y){
        data.movePlayer(x, y);
    }

    /**
     * Checks if a player is on the specified tile.
     * 
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the index of the player on the tile, or -1 if no player is present
     */
    public static int playerIsOnTile(int x, int y){
        return data.playerIsOnTile(x, y);
    }

    /**
     * Attempts to place a wall at the specified position.
     * 
     * @param posx the x-coordinate for the wall
     * @param posy the y-coordinate for the wall
     * @param vertical true for vertical wall, false for horizontal wall
     */
    public static void placeWall(int posx, int posy, boolean vertical){
        data.placeWall(posx, posy, vertical);
    }

    /**
     * Saves the current game state to a file.
     * 
     * @param slot the save slot number (used to create filename "save" + slot)
     * @throws Exception if an I/O error occurs during saving
     */
    public static void saveGame(int slot) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save" + slot))) {
            out.writeObject(data); 
        }
    }

    /**
     * Loads a game state from a file.
     * After loading, re-registers all listeners and refreshes the game state.
     * 
     * @param slot the save slot number (used to read from filename "save" + slot)
     * @throws Exception if an I/O error occurs or the file cannot be read
     */
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
