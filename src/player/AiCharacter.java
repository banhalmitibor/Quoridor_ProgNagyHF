package player;

import java.util.ArrayList;
import java.util.Random;

import game.Controller;

public class AiCharacter extends Piece{

    private static final Random ran = new Random();

    public AiCharacter(int x, int y) {
        super(x, y);
    }

    @Override
    public void makeMove() {
      
        if (wallnum < 10 && ran.nextDouble() < 0.4) {
            if (tryPlaceSimpleBlockingWall()) {
                return;
            }
        }

        tryMoveTowardGoal();

    }


    private void tryMoveTowardGoal() {
        int x = getX();
        int y = getY();

        if (tryMove(x, y + 1)) return;

        if (tryMove(x - 1, y + 1)) return;  
        if (tryMove(x + 1, y + 1)) return; 

        if (tryMove(x - 1, y)) return; 
        if (tryMove(x + 1, y)) return;

        tryMove(x, y - 1); 
    }

    
    private boolean tryMove(int tx, int ty) {
        if (tx < 0 || tx > 8 || ty < 0 || ty > 8) {
            return false;
        }

        int oldX = getX();
        int oldY = getY();

        Controller.movePlayer(tx, ty);

        return (getX() == tx && getY() == ty) || (getX() != oldX || getY() != oldY);
    }


    private boolean tryPlaceSimpleBlockingWall() {
        ArrayList<Piece> players = Controller.getPlayers();
        int curIndex = Controller.getCurPlayer();

        if (players == null || players.size() <= 1) {
            return false;
        }

        int opponentIndex = (curIndex == 0 ? 1 : 0);
        Piece opponent = players.get(opponentIndex);

        int ox = opponent.getX();
        int oy = opponent.getY();

        int[][] candidates = new int[][] {
                { ox, oy - 1, 0 },
                { ox, oy - 1, 1 }, 

                { ox - 1, oy - 1, 0 },
                { ox + 1, oy - 1, 0 },
                { ox - 1, oy - 1, 1 },
                { ox + 1, oy - 1, 1 },

                { ox - 1, oy, 0 },
                { ox + 1, oy, 0 },
                { ox - 1, oy, 1 },
                { ox + 1, oy, 1 },
        };


        shuffleArray(candidates);

        for (int[] c : candidates) {
            int wx = c[0];
            int wy = c[1];
            boolean vertical = (c[2] == 1);

            if (wx < 0 || wx >= 8 || wy < 0 || wy >= 8) {
                continue;
            }

            int oldWalls = this.wallnum;

            Controller.placeWall(wx, wy, vertical);

            if (this.wallnum > oldWalls) {
                return true;
            }
        }

        return false;
    }

    private void shuffleArray(int[][] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = ran.nextInt(i + 1);
            int[] tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
