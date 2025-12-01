package swingui;

import java.awt.Color;
import javax.swing.JPanel;

import game.Controller;

/**
 * Panel representing an intersection point on the game board.
 * Intersections are located where walls cross and change color
 * when adjacent walls are placed.
 * 
 * @author banhalmitibor
 * @version 1.0
 */
public class Intersection extends JPanel {

    /** The x-coordinate of this intersection (1-based). */
    private final int posx;
    
    /** The y-coordinate of this intersection (1-based). */
    private final int posy;

    /**
     * Constructs a new Intersection at the specified grid position.
     * Converts from visual grid coordinates to game coordinates.
     * 
     * @param row the visual grid row (odd numbers only)
     * @param col the visual grid column (odd numbers only)
     */
    public Intersection(int row, int col){
        posx = (col/2) + 1;
        posy = (row/2) + 1;

        Controller.addPropertyChangeListener(evt ->{
            String n = evt.getPropertyName();
            if(n.equals("Wall")){
                refreshColor();
            }
        });

        System.out.println("INTERSECTION POS: " + posx + " " + posy);
        this.setBackground(new Color(0, true));
    }

    /**
     * Updates the background color based on adjacent wall placements.
     * Shows a wall color if any wall passes through this intersection,
     * otherwise shows the board background color.
     */
    public void refreshColor(){
        Color c;
        
        c = Controller.getHorizontalWalls().get(posx-1).get(posy-1) || Controller.getVertcialWalls().get(posx-1).get(posy-1) ? new Color(140, 105,80) : new Color(240, 225, 200);
        
        this.setBackground(c);
    }
}
