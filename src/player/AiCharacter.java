package player;

import java.util.ArrayList;
import java.util.Random;

import game.Controller;

/**
 * Represents an ai player character in the Quoridor game.
 * Extends the Piece class to provide computer player functionality.
 * 
 * @author banhalmitibor
 * @version 1.0
 */
public class AiCharacter extends Piece{

    /** A random object to be able to make random decisions. */
    private static final Random ran = new Random();

    /**
     * Constructs a new AiCharacter at the specified position.
     * 
     * @param x the initial x-coordinate on the board
     * @param y the initial y-coordinate on the board
     */
    public AiCharacter(int x, int y) {
        super(x, y);
    }

    /**
     * Makes a move for this character.
     * With a 40% chance the function is going to try and place a simple blocking wall.
     * In the other case it will try to make a move towards the goal.
     */
    @Override
    public void makeMove() {
      
        if (wallnum < 10 && ran.nextDouble() < 0.4) {
            if (tryPlaceSimpleBlockingWall()) {
                return;
            }
        }

        tryMoveTowardGoal();

    }

    /**
     * Tries to move the AI character toward its goal line.
     * 
     * The method attempts moves in a fixed priority order (forward,
     * diagonally forward, sideways, then backward) and stops after the
     * first successful move.
     */
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

    /**
     * Attempts to move the AI character to the given target coordinates.
     * 
     * The move is only attempted if the target position is within the board
     * boundaries.
     * 
     * @param tx the target x-coordinate to move to
     * @param ty the target y-coordinate to move to
     * @return true if the AI's position changed as a result of the move,
     *         false if the target was out of bounds or the move failed
     */
    private boolean tryMove(int tx, int ty) {
        if (tx < 0 || tx > 8 || ty < 0 || ty > 8) {
            return false;
        }

        int oldX = getX();
        int oldY = getY();

        Controller.movePlayer(tx, ty);

        return (getX() == tx && getY() == ty) || (getX() != oldX || getY() != oldY);
    }

    /**
     * Tries to place a simple blocking wall in front of the opponent player.
     * 
     * The method:
     *   Determines the opponent based on the current player index.
     *   Generates candidate wall positions around and in front of the opponent.
     *   Shuffles these candidates and tries them in random order.
     *   Places the first wall that is accepted by the controller.
     * @return true if a wall was successfully placed, flase otherwise
     */
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

        // Randomize the order of candidate wall positions
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

    /**
     * Shuffles the given array of candidate wall positions in place.
     * <p>
     * Uses the Fisher–Yates algorithm to produce a uniform random permutation.
     * 
     * @param arr the array of candidate positions to shuffle
     */
    private void shuffleArray(int[][] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = ran.nextInt(i + 1);
            int[] tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}