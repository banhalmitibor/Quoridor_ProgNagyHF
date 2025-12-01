package player;

/**
 * Represents a human player character in the Quoridor game.
 * Extends the Piece class to provide human player functionality.
 * 
 * @author Quoridor Team
 * @version 1.0
 */
public class PlayerCharecter extends Piece {
    
    /**
     * Constructs a new PlayerCharecter at the specified position.
     * 
     * @param x the initial x-coordinate on the board
     * @param y the initial y-coordinate on the board
     */
    public PlayerCharecter(int x, int y){
        super(x, y);
    }   

    /**
     * Makes a move for this player character.
     * Currently empty as human players make moves through UI interaction.
     */
    @Override
    public void makeMove(){
        
    }

    
}
