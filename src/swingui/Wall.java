package swingui;

import java.awt.*;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.Controller;

/**
 * Button representing a wall slot on the game board.
 * Players can click wall slots to place walls. Walls can be either
 * vertical or horizontal depending on their position in the grid.
 * 
 * @author banhalmitibor
 * @version 1.0
 */
public class Wall extends JButton {

    /** The x-coordinate of this wall slot. */
    private final int posx;
    
    /** The y-coordinate of this wall slot. */
    private final int posy;
    
    /** Whether this is a vertical wall slot. */
    private final boolean vertical;

    /** Color for empty wall slots (matches board background). */
    private static final Color NOT_PLACED = new Color(240, 225, 200);
    
    /** Color for placed walls. */
    private static final Color PLACED = new Color(140, 105,80);

    /**
     * Mouse adapter that handles hover effects on wall slots.
     * Shows a preview color when the mouse hovers over an empty wall slot.
     */
    public class MouseHoverListener extends MouseAdapter{
        /**
         * Changes the wall color to indicate hover state.
         * 
         * @param e the mouse event
         */
        @Override 
        public void mouseEntered(MouseEvent e){
            Wall w = (Wall)e.getSource();
            w.setBackground(new Color(128, 128, 128, 64));
        }
        
        /**
         * Restores the wall color when mouse exits.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseExited(MouseEvent e){
            Wall w = (Wall)e.getSource();
            w.refreshColor();
        }
    }

    /**
     * Action listener that handles wall placement when clicked.
     */
    public class ClickWall implements ActionListener {
        /**
         * Attempts to place a wall at this location when clicked.
         * 
         * @param e the action event
         */
        @Override
        public void actionPerformed(ActionEvent e){
            
            Controller.placeWall(posx, posy, vertical);
        }
    }

    /**
     * Constructs a new Wall at the specified grid position.
     * Determines if the wall is vertical or horizontal based on grid position.
     * 
     * @param row the visual grid row
     * @param col the visual grid column
     */
    public Wall(int row, int col){
        
        Color c;
        if(row % 2 == 0){
            vertical = true;
            posx = (col / 2);
            posy = (row / 2);
            if(posy < 8) c = Controller.getVertcialWalls().get(posx).get(posy) || Controller.getVertcialWalls().get(posx).get(posy-1>0?posy-1:0) ? PLACED : NOT_PLACED;
            else c = Controller.getVertcialWalls().get(posx).get(posy-1) ? PLACED : NOT_PLACED;
        }
        else{
            vertical = false;
            posx = (col / 2);
            posy = (row / 2);
            System.out.println(posx + " " + posy);
            if(posx < 8) c = Controller.getHorizontalWalls().get(posx).get(posy) || Controller.getHorizontalWalls().get(posx-1>0?posx-1:0).get(posy) ? PLACED : NOT_PLACED;
            else c = Controller.getHorizontalWalls().get(posx-1).get(posy) ? PLACED : NOT_PLACED;
        }

        this.addActionListener(new ClickWall());
        this.addMouseListener(new MouseHoverListener());
        Controller.addPropertyChangeListener(evt ->{
            String n = evt.getPropertyName();
            if(n.equals("Wall")){
                refreshColor();
            }
        });

        this.setOpaque(true);
        this.setBackground(c);
        this.setFocusable(false);
        this.setBorder(null);
    }

    /**
     * Updates the wall color based on current wall placement state.
     * Shows PLACED color if a wall is placed, NOT_PLACED otherwise.
     */
    public void refreshColor(){
        Color c;
        if(vertical){
            if(posy < 8) c = Controller.getVertcialWalls().get(posx).get(posy) || Controller.getVertcialWalls().get(posx).get(posy-1>0?posy-1:0) ? PLACED : NOT_PLACED;
            else c = Controller.getVertcialWalls().get(posx).get(posy-1) ? PLACED : NOT_PLACED;
        }
        else{
            if(posx < 8) c = Controller.getHorizontalWalls().get(posx).get(posy) || Controller.getHorizontalWalls().get(posx-1>0?posx-1:0).get(posy) ? PLACED : NOT_PLACED;
            else c = Controller.getHorizontalWalls().get(posx-1).get(posy) ? PLACED : NOT_PLACED;
        }
        this.setBackground(c);
    }

}
