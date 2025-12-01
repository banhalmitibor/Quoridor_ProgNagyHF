package grid;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import player.AiCharacter;

import player.Piece;
import player.PlayerCharecter;

/**
 * Core game data class that holds all game state and implements game logic for Quoridor.
 * Manages player positions, wall placements, move validation, win conditions, and game serialization.
 * Implements PropertyChangeSupport for observer pattern to notify UI components of state changes.
 * 
 * @author Quoridor Team
 * @version 1.0
 */
public class GameData implements Serializable {
    /** 2D array tracking vertical wall placements. */
    public ArrayList<ArrayList<Boolean>> verticalWalls;
    
    /** 2D array tracking horizontal wall placements. */
    public ArrayList<ArrayList<Boolean>> horizontalWalls;

    /** List of all players in the game. */
    public ArrayList<Piece> players;

    /** Index of the current player whose turn it is (0-based). */
    public int curPlayer;

    /** Flag indicating if the game has been won. */
    private boolean gameWon = false;

    /**
     * Custom serialization method to write the object state.
     * 
     * @param out the ObjectOutputStream to write to
     * @throws IOException if an I/O error occurs
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * Custom deserialization method to restore the object state.
     * Reinitializes the PropertyChangeSupport after deserialization.
     * 
     * @param in the ObjectInputStream to read from
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        pcs = new PropertyChangeSupport(this);
        
    }


    /**
     * Refreshes all property change listeners to update the UI.
     * Called after loading a saved game to synchronize the display.
     */
    public void refresh(){
        pcs.firePropertyChange("Player", -1, curPlayer);
        pcs.firePropertyChange("Wall", -1, 0);
        pcs.firePropertyChange("Tile", -1, 0);
        pcs.firePropertyChange("Reset", -1, 0);
        for(Piece p : players){
            p.refresh();
        }
    }

    /** Property change support for notifying listeners about game state changes. */
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Adds a property change listener to receive game state updates.
     * 
     * @param l the PropertyChangeListener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    /**
     * Removes a property change listener.
     * 
     * @param l the PropertyChangeListener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }


    /**
     * Constructs a new GameData with default initial state.
     * Initializes an 8x8 grid of walls (all unplaced) and creates two players
     * at their starting positions.
     */
    public GameData() {
        verticalWalls = new ArrayList<>(8);
        horizontalWalls = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            verticalWalls.add(new ArrayList<>(8));
            horizontalWalls.add(new ArrayList<>(8));
            for(int j = 0; j < 8; j++){
                verticalWalls.get(i).add(false);
                horizontalWalls.get(i).add(false);
            }
        }

        players = new ArrayList<>();
        players.add(new PlayerCharecter(4, 8));
        players.add(new PlayerCharecter(4, 0));
        curPlayer = 0;

    }

    /**
     * Resets the game to its initial state with the specified number of players.
     * Clears all walls and repositions players to their starting positions.
     * 
     * @param pnum the number of players (2 or 4)
     */
    public void reset(int pnum){
        gameWon = false;
        for (int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                verticalWalls.get(i).set(j, false);
                horizontalWalls.get(i).set(j, false);
            }
        }

    
        players = new ArrayList<>();
        players.add(new PlayerCharecter(4, 8));
        players.add(new PlayerCharecter(4, 0));
        if(pnum == 4){
            players.add(new PlayerCharecter(0, 4));
            players.add(new PlayerCharecter(8, 4));
        }
        curPlayer = 0;

        pcs.firePropertyChange("Player", -1, 0);
        pcs.firePropertyChange("Wall", -1, 0);
        pcs.firePropertyChange("Tile", -1, 0);
        pcs.firePropertyChange("Reset", -1, 0);
    }


    /**
     * Resets the game to its initial state with two players, the second one being ai.
     * Clears all walls and repositions players to their starting positions.
     */
    public void resetAi(){
        gameWon = false;
        for (int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                verticalWalls.get(i).set(j, false);
                horizontalWalls.get(i).set(j, false);
            }
        }

    
        players = new ArrayList<>();
        players.add(new PlayerCharecter(4, 8));
        players.add(new AiCharacter(4, 0));
        curPlayer = 0;

        pcs.firePropertyChange("Player", -1, 0);
        pcs.firePropertyChange("Wall", -1, 0);
        pcs.firePropertyChange("Tile", -1, 0);
        pcs.firePropertyChange("Reset", -1, 0);
    }

    /**
     * Checks if a player has a valid path to their goal using BFS algorithm.
     * Used to validate wall placements don't completely block a player.
     * 
     * @param startX the starting x-coordinate
     * @param startY the starting y-coordinate
     * @param playerIndex the index of the player to check
     * @return true if a path exists to the player's goal, false otherwise
     */
    private boolean hasPathToGoal(int startX, int startY, int playerIndex) {
        int size = 9;
        boolean[][] visited = new boolean[size][size];

        ArrayList<int[]> queue = new ArrayList<>();
        int head = 0;

        queue.add(new int[] { startX, startY });
        visited[startY][startX] = true;

        while (head < queue.size()) {
            int[] cur = queue.get(head++);
            int x = cur[0];
            int y = cur[1];

            if (checkWin(x, y, playerIndex)) {
                return true;
            }

            int px = x;
            int py = y;

            int[][] dirs = {
                    { 0, -1 }, 
                    { 1, 0 },  
                    { 0, 1 },  
                    { -1, 0 }  
            };

            for (int[] d : dirs) {
                int nx = x + d[0];
                int ny = y + d[1];

                if (nx < 0 || nx >= size || ny < 0 || ny >= size) continue;
                if (visited[ny][nx]) continue;

                Pair<Integer> data = getStepData(nx, ny, px, py);
                int dir = data.getX();
                int dist = data.getY();

                if (dist != 1) continue;

                if (isThereWallBetweenCoordinates(px, py, dir)) continue;

                visited[ny][nx] = true;
                queue.add(new int[] { nx, ny });
            }
        }

        // No winning tile reachable
        return false;
    }

    /**
     * Validates that all players still have a path to their respective goals.
     * Used to ensure a wall placement doesn't create an illegal game state.
     * 
     * @return true if all players have valid paths, false otherwise
     */
    private boolean wallValid(){
        for (int i = 0; i < players.size(); i++) {
            Piece p = players.get(i);
            if (!hasPathToGoal(p.getX(), p.getY(), i)) {
                return false; 
            }
        }
        

        return true;
    }

    /**
     * Attempts to place a wall at the specified position.
     * Validates the placement doesn't overlap existing walls, cross other walls,
     * or block all paths for any player.
     * 
     * @param posx the x-coordinate for the wall
     * @param posy the y-coordinate for the wall
     * @param vertical true for vertical wall, false for horizontal wall
     */
    public void placeWall(int posx, int posy, boolean vertical){
        if(gameWon) return;

        if(posx<8 && posy < 8){
            if(vertical){

                if(verticalWalls.get(posx).get(posy+1 < 8 ? posy+1 : posy) || verticalWalls.get(posx).get(posy-1 >= 0 ? posy-1 : posy) || verticalWalls.get(posx).get(posy)) {
                    System.out.println("VERTICAL NOT PLACED ---- ABOVE OR BELOW");
                    return;
                }
                if(horizontalWalls.get(posx).get(posy)) {
                    System.out.println("VERTICAL NOT PLACED ---- HORIZONTAL WALL");
                    return;
                }

                if(players.get(curPlayer).canPlaceWall()) {
                    verticalWalls.get(posx).set(posy, true);
                    if(!wallValid()){
                        verticalWalls.get(posx).set(posy, false);
                        players.get(curPlayer).revertWall();
                        return;
                    }
                    int old = curPlayer;
                    curPlayer = curPlayer+1 < players.size() ? curPlayer+1 : 0;
                    pcs.firePropertyChange("Player", old, curPlayer);
                }
            }
            else{

                if(verticalWalls.get(posx).get(posy)) {
                    System.out.println("HORIZONTAL NOT PLACED ----  VERTICAL WALL");
                    return;
                }
                if(horizontalWalls.get(posx-1 >= 0 ? posx - 1 : posx).get(posy) || horizontalWalls.get(posx+1 < 8 ? posx+1 : posx).get(posy) || horizontalWalls.get(posx).get(posy)) {
                    System.out.println("HORIZONTAL NOT PLACED ---- LEFT OR RIGHT");
                    return;
                }

                if(players.get(curPlayer).canPlaceWall()) {
                    horizontalWalls.get(posx).set(posy, true);
                    if(!wallValid()){
                        horizontalWalls.get(posx).set(posy, false);
                        players.get(curPlayer).revertWall();
                        return;
                    }
                    int old = curPlayer;
                    curPlayer = curPlayer+1 < players.size() ? curPlayer+1 : 0;
                    pcs.firePropertyChange("Player", old, curPlayer);
                }

                
            }
            pcs.firePropertyChange("Wall", 0, 1);
        }
        
    }

    /**
     * Calculates the direction and distance between two positions.
     * Direction encoding: 1=up, 2=right, 3=down, 4=left, 0=invalid (diagonal).
     * 
     * @param x the target x-coordinate
     * @param y the target y-coordinate
     * @param px the current x-coordinate
     * @param py the current y-coordinate
     * @return a Pair containing direction and distance
     */
    private Pair<Integer> getStepData(int x, int y, int px, int py){
        int irany;
        int dist = 0;
        if(px == x && py > y) { irany = 1; dist = py-y; }
        else if(py == y && x > px) { irany = 2; dist = x-px;}
        else if(px == x && py < y) { irany = 3; dist = y-py;}
        else if(py == y && x < px) {irany = 4; dist = px-x; }
        else irany = 0;
        
        return new Pair<>(irany, dist);
    }


    /**
     * Checks if there is a wall blocking movement between two coordinates.
     * 
     * @param px the current x-coordinate
     * @param py the current y-coordinate
     * @param irany the direction of movement (1=up, 2=right, 3=down, 4=left)
     * @return true if a wall blocks the path, false otherwise
     */
    private boolean isThereWallBetweenCoordinates(int px, int py, int irany){
        

        if(irany == 0) return true;

        switch(irany){
            case 1 -> {
                if(horizontalWalls.get(px < 8 ? px : px-1).get(py-1 >= 0 ? py-1 : py) || horizontalWalls.get(px-1 >= 0 ? px-1 : px).get(py - 1)) return true;
                System.out.println("FELFELE TESZT " + px + " " + py);
            }
            case 2 -> {
                if(verticalWalls.get(px).get(py < 8 ? py : py - 1) || verticalWalls.get(px < 8 ? px : px-1).get(py-1 >= 0 ? py-1 : py)) return true;
                System.out.println("JOBBRA TESZT " + px + " " + py);
            }
            case 3 -> {
                if(horizontalWalls.get(px < 8 ? px : px-1).get(py) || horizontalWalls.get(px-1 >= 0 ? px-1 : px).get(py)) return true;
                System.out.println("LE TESZT " + px + " " + py);
            }
            case 4 -> {
                if(verticalWalls.get(px-1 >= 0 ? px-1 : px).get(py < 8 ? py : py - 1) || verticalWalls.get(px-1 >= 0 ? px-1 : px).get(py-1 >= 0 ? py-1 : py)) return true;  
                System.out.println("BALRA TESZT " + px + " " + py);                 
            }
        }

        return false;
            
    }

    /**
     * Checks if a diagonal move is valid when jumping over an opponent.
     * A diagonal move is allowed when jumping straight over an opponent is blocked.
     * 
     * @param ix the intermediate x-coordinate (opponent position)
     * @param iy the intermediate y-coordinate (opponent position)
     * @param px the current player x-coordinate
     * @param py the current player y-coordinate
     * @return true if the diagonal move is valid, false otherwise
     */
    private boolean checkDiagnalMove(int ix, int iy, int px, int py){
        if(playerIsOnTile(ix, iy) == -1) return false;

        Pair<Integer> dataToMid = getStepData(ix, iy, px, py);
        int dirToMid = dataToMid.getX();
        if (dirToMid == 0) return false;

        if (isThereWallBetweenCoordinates(px, py, dirToMid)) return false;

        int bx = ix, by = iy;
        switch (dirToMid) {
            case 1 -> by = iy - 1;
            case 2 -> bx = ix + 1;
            case 3 -> by = iy + 1;
            case 4 -> bx = ix - 1;
        }
        boolean behindBlocked =
                bx < 0 || bx > 8 || by < 0 || by > 8
            || isThereWallBetweenCoordinates(ix, iy, dirToMid);

        if (!behindBlocked) return false;

        return true;
    }


    /**
     * Validates if a move from the current position to the target position is legal.
     * Handles standard moves, jumps over opponents, and diagonal moves.
     * 
     * @param x the target x-coordinate
     * @param y the target y-coordinate
     * @param px the current x-coordinate
     * @param py the current y-coordinate
     * @return true if the move is valid, false otherwise
     */
    private boolean moveIsValid(int x, int y, int px, int py){
        Pair<Integer> data = getStepData(x, y, px, py); 
        int irany = data.getX();
        int dist = data.getY();

        if(dist == 2){
            
            switch(irany){
                case 1 -> {
                    if(isThereWallBetweenCoordinates(px, py, irany) || (playerIsOnTile(px, py-1) == -1)) return false;
                    py = py-1;
                    dist--;
                }
                case 2 -> {
                    if(isThereWallBetweenCoordinates(px, py, irany) || (playerIsOnTile(px+1, py) == -1)) return false;
                    px = px+1;
                    dist--;
                }
                case 3 -> {
                    if(isThereWallBetweenCoordinates(px, py, irany) || (playerIsOnTile(px, py+1) == -1)) return false;
                    py = py+1;
                    dist--;
                }
                case 4 -> {
                    if(isThereWallBetweenCoordinates(px, py, irany) || (playerIsOnTile(px-1, py) == -1)) return false;
                    px = px-1;
                    dist--;
                }
            }
        }

        if(dist > 2) return false;

        if(irany == 0){

            int dx = x - px;
            int dy = y - py;

            if (Math.abs(dx) != 1 || Math.abs(dy) != 1) {
                return false;
            }

        
            int ix1 = px + dx;
            int iy1 = py;
            int ix2 = px;
            int iy2 = py + dy;

            int ix;
            int iy;

            if (checkDiagnalMove(ix1, iy1, px, py)) {
                ix = ix1;
                iy = iy1;
            }
            else if (checkDiagnalMove(ix2, iy2, px, py)) {
                ix = ix2;
                iy = iy2;
            }
            else {
                return false;
            }
        
            px = ix;
            py = iy;

            data = getStepData(x, y, px, py);
            irany = data.getX();
            dist = data.getY();
        }
        
        if(dist != 1) return false;

        return !isThereWallBetweenCoordinates(px, py, irany);

    }


    /**
     * Checks if any player is on the specified tile.
     * 
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the index of the player on the tile, or -1 if empty
     */
    public int playerIsOnTile(int x, int y){
        int eredmeny = -1;
        for(Piece p : players){
            if(p.getX() == x && p.getY() == y) eredmeny =  players.indexOf(p);
        }
        return eredmeny;
    }

    /**
     * Checks if a player has reached their winning position.
     * Player 0 wins at y=0, player 1 wins at y=8, player 2 wins at x=8, player 3 wins at x=0.
     * 
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @param cur the current player index
     * @return true if the position is a winning position for the player, false otherwise
     */
    public boolean checkWin(int x, int y, int cur){
        switch(cur){
            case 0 -> {if(y == 0) return true;}
            case 1 -> {if(y == 8) return true;}
            case 2 -> {if(x == 8) return true;}
            case 3 -> {if(x == 0) return true;}
        }


        return false;
    }

    /**
     * Attempts to move the current player to the specified position.
     * Validates the move, updates player position, checks for win condition,
     * and advances to the next player's turn.
     * 
     * @param x the target x-coordinate
     * @param y the target y-coordinate
     */
    public void movePlayer(int x, int y){

        if(gameWon) return;

        int px = players.get(curPlayer).getX();
        int py = players.get(curPlayer).getY();

        if(!moveIsValid(x, y, px, py)) return;

        if(playerIsOnTile(x, y) != -1) return;

        players.get(curPlayer).setPos(x, y);

        if(checkWin(x, y, curPlayer)){
            System.out.println("WIIIIIIIINNNN");
            gameWon = true;
            pcs.firePropertyChange("Win", 0, curPlayer+1);
            pcs.firePropertyChange("Tile", 0, 1);
            return;
        }
        int old = curPlayer;
        curPlayer = curPlayer+1 < players.size() ? curPlayer+1 : 0;
        pcs.firePropertyChange("Player", old, curPlayer);
        pcs.firePropertyChange("Tile", 0, 1);
        players.get(curPlayer).makeMove();

    }

}
