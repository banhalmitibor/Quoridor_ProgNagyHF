package grid;

import java.util.ArrayList;

import player.Piece;
import player.PlayerCharecter;

public class GameData {
    public static final ArrayList<ArrayList<Boolean>> verticalWalls;
    public static final ArrayList<ArrayList<Boolean>> horizontalWalls;

    public static Piece player1;
    public static Piece player2;

    private static boolean oneMoved = false;

    static {
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

        player1 = new PlayerCharecter(4, 8);
        player2 = new PlayerCharecter(4, 0);

    }

    public static int getPlayerPos(int x, int y){
        if(player1.getX() == x && player1.getY() == y) return 1;
        else if(player2.getX() == x && player2.getY() == y) return 2;
        else return 0;
    }

    public static void placeWall(){
        if(!oneMoved){
            oneMoved = true;
        }
        else{
            oneMoved = false;
        }
    }

    public static void movePlayer(int x, int y){
        if(!oneMoved){
            player1.setPos(x, y);
            oneMoved = true;
        }
        else{
            player2.setPos(x, y);
            oneMoved = false;
        }

    }

}
