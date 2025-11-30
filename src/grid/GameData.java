package grid;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import player.Piece;
import player.PlayerCharecter;

public class GameData implements Serializable {
    public ArrayList<ArrayList<Boolean>> verticalWalls;
    public ArrayList<ArrayList<Boolean>> horizontalWalls;

    public ArrayList<Piece> players;

    public int curPlayer;

    private boolean gameWon = false;

    //KIMENTÉS (SZERIALIZÁCIÓ)
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        pcs = new PropertyChangeSupport(this);
        
    }


    //A listenerek aktualitása miatt
    public void refresh(){
        pcs.firePropertyChange("Player", -1, curPlayer);
        pcs.firePropertyChange("Wall", -1, 0);
        pcs.firePropertyChange("Tile", -1, 0);
        pcs.firePropertyChange("Reset", -1, 0);
        for(Piece p : players){
            p.refresh();
        }
    }

    //public static Piece player1;
    //public static Piece player2;

    //private static final GameData INSTANCE = new GameData();


    //LISTENEREKNEK
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }


    //private static boolean oneMoved = false;

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

        //player1 = new PlayerCharecter(4, 8);
        //player2 = new PlayerCharecter(4, 0);
        players = new ArrayList<>();
        players.add(new PlayerCharecter(4, 8));
        players.add(new PlayerCharecter(4, 0));
        curPlayer = 0;

    }

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

    /*public static int getPlayerPos(int x, int y){
        if(player1.getX() == x && player1.getY() == y) return 1;
        else if(player2.getX() == x && player2.getY() == y) return 2;
        else return 0;
    }*/

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

    private boolean wallValid(){
        for (int i = 0; i < players.size(); i++) {
            Piece p = players.get(i);
            if (!hasPathToGoal(p.getX(), p.getY(), i)) {
                return false; 
            }
        }
        

        return true;
    }

    public void placeWall(int posx, int posy, boolean vertical){
        if(gameWon) return;

        if(posx<8 && posy < 8){
            if(vertical){

                if(verticalWalls.get(posx).get(posy+1 < 8 ? posy+1 : posy) || verticalWalls.get(posx).get(posy-1 >= 0 ? posy-1 : posy) || verticalWalls.get(posx).get(posy)) { //Annak ellenőrzése hogy van e fal az a hely felett vagy alatt ahova rakni szeretnénk, ha van akkor nem valid a fal
                    System.out.println("VERTICAL NOT PLACED ---- ABOVE OR BELOW");
                    return;
                }
                if(horizontalWalls.get(posx).get(posy)) { //Annak ellenőrzése hogy egy vízszintes fal keresztezné e a lerakandó falat
                    System.out.println("VERTICAL NOT PLACED ---- HORIZONTAL WALL");
                    return;
                }

                //FAL LERAKÁSA HA MINDEN CHECK PASSELT
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

                if(verticalWalls.get(posx).get(posy)) { //Annak ellenőrzése hogy van e függőleges fal ami keresztezi a lerakandót
                    System.out.println("HORIZONTAL NOT PLACED ----  VERTICAL WALL");
                    return;
                }
                if(horizontalWalls.get(posx-1 >= 0 ? posx - 1 : posx).get(posy) || horizontalWalls.get(posx+1 < 8 ? posx+1 : posx).get(posy) || horizontalWalls.get(posx).get(posy)) { //Annak ellenőrzése, hogy van e a lerakandó fal mellett balra vagy jobbra amár fal ami ütné
                    System.out.println("HORIZONTAL NOT PLACED ---- LEFT OR RIGHT");
                    return;
                }

                //FAL LERAKÁSA HA MINDEN CHECK PASSELT
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
        
        //oneMoved = !oneMoved;
    }

    private Pair<Integer> getStepData(int x, int y, int px, int py){
        int irany;
        int dist = 0;
        if(px == x && py > y) { irany = 1; dist = py-y; } //Fel
        else if(py == y && x > px) { irany = 2; dist = x-px;}//Jobbra
        else if(px == x && py < y) { irany = 3; dist = y-py;}//Le
        else if(py == y && x < px) {irany = 4; dist = px-x; }//Balra
        else irany = 0; //INVALID lépés
        
        return new Pair<>(irany, dist);
    }


    private boolean isThereWallBetweenCoordinates(int px, int py, int irany){
        
        //int dist = data.getY();


        if(irany == 0) return true; //Ugyanaz a négyzet lett kiválasztva amin jelenleg áll a játékos

        //px--;
        //py--;

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

    private boolean diagnalJumpAllowed(){
        return false;
    }

    private boolean moveIsValid(int x, int y, int px, int py){
        Pair<Integer> data = getStepData(x, y, px, py); 
        int irany = data.getX();
        int dist = data.getY();

        if(dist == 2){
            
            switch(irany){
                case 1 -> {
                    if(isThereWallBetweenCoordinates(px, py, irany) || (playerIsOnTile(px, py-1) == -1)) return false;
                    py = py-1;
                }
                case 2 -> {
                    if(isThereWallBetweenCoordinates(px, py, irany) || (playerIsOnTile(px+1, py) == -1)) return false;
                    px = px+1;
                }
                case 3 -> {
                    if(isThereWallBetweenCoordinates(px, py, irany) || (playerIsOnTile(px, py+1) == -1)) return false;
                    py = py+1;
                }
                case 4 -> {
                    if(isThereWallBetweenCoordinates(px, py, irany) || (playerIsOnTile(px-1, py) == -1)) return false;
                    px = px-1;
                }
            }
        }

        if(dist > 2) return false;

        //Itt kell ellenőrizni a blokkolt mellé ugrást
        int dx = x - px;
        int dy = y - py;

        return !isThereWallBetweenCoordinates(px, py, irany);

    }


    public int playerIsOnTile(int x, int y){
        int eredmeny = -1;
        for(Piece p : players){
            if(p.getX() == x && p.getY() == y) eredmeny =  players.indexOf(p);
        }
        return eredmeny;
    }

    public boolean checkWin(int x, int y, int cur){
        switch(cur){
            case 0 -> {if(y == 0) return true;}
            case 1 -> {if(y == 8) return true;}
            case 2 -> {if(x == 8) return true;}
            case 3 -> {if(x == 0) return true;}
        }


        return false;
    }

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

    }

}
