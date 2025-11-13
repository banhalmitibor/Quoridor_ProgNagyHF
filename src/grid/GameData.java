package grid;

import java.util.ArrayList;

public class GameData {
    public static final ArrayList<ArrayList<Boolean>> verticalWalls;
    public static final ArrayList<ArrayList<Boolean>> horizontalWalls;

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

    }

}
